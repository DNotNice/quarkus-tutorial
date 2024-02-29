package repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.devansh.app.model.FilmEntity;
import org.devansh.app.model.FilmEntity$;

import java.io.StreamCorruptedException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class filmRepository {
    @Inject
    JPAStreamer jpaStreamer;
    private  static  final int PAGE_SIZE = 20;
    public Optional<FilmEntity> getFilm(short filmId){
        return jpaStreamer.stream(FilmEntity.class)
                .filter(FilmEntity$.filmId.equal(filmId))
                .findFirst();
    }
    public Stream<FilmEntity> paged(long page, short minLength){
     return jpaStreamer.stream(Projection.select(FilmEntity$.filmId , FilmEntity$.title , FilmEntity$.length))
             .filter(FilmEntity$.length.greaterThan(minLength))
             .sorted(FilmEntity$.length)
             .skip(page * PAGE_SIZE)
             .limit(PAGE_SIZE);
    }
    public Stream<FilmEntity> getFilms(short minLength){
        return jpaStreamer.stream(FilmEntity.class)
                .filter(FilmEntity$.length.greaterThan(minLength))
                .sorted(FilmEntity$.length);
    }

    public Stream<FilmEntity> actors(String startsWith , short minLength){
        final StreamConfiguration<FilmEntity> sc = StreamConfiguration.of(FilmEntity.class).joining(FilmEntity$.actors);
        return jpaStreamer.stream(sc)
                .filter(FilmEntity$.title.startsWith(startsWith).and(FilmEntity$.length.greaterThan(minLength)))
                .sorted(FilmEntity$.length.reversed());
    }

    @Transactional
    public void updateRentalRate(short minLength , Float rentalRate ){
         jpaStreamer.stream(FilmEntity.class)
                 .filter(FilmEntity$.length.greaterThan(minLength))
                 .forEach(f->{
                     f.setRentalRate(new BigDecimal(rentalRate));
                 });
    }
}
