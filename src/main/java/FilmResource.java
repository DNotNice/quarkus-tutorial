import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.devansh.app.model.FilmEntity;
import repository.filmRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Path("/")
public class FilmResource {
    @Inject
    filmRepository FilmRepository;
    @GET
    @Path("/helloWorld")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(){
        return "Hello World";
    }
    @GET
    @Path("/film/{filmId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello2(short filmId){
        Optional<FilmEntity> film = FilmRepository.getFilm(filmId);
        return film.isPresent() ? film.get().getTitle() : "No film was found !";

    }

    @GET
    @Path("/pagedFilms/{page}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String paged(long page, short minLength){
        return FilmRepository.paged(page , minLength)
                .map(f->String.format("%s (%d min)" ,f.getTitle() , f.getLength()))
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("/actors/{startsWith}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String actors(String  startsWith , short minLength){
        return FilmRepository.actors(startsWith , minLength)
                .map(i-> String.format("%s (%d min) : %s", i.getTitle() , i.getLength() ,
                        i.getActors().stream()
                                .map(a-> String.format("%s %s" , a.getFirstName(),a.getLastName()))
                                .collect(Collectors.joining(","))))
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("/updates/{rentalRate}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String actors(Float  rentalRate , short minLength){
        FilmRepository.updateRentalRate(minLength , rentalRate);
        return FilmRepository.getFilms(minLength)
                .map(f-> String.format("%s (%d min) - $%f" , f.getTitle() , f.getLength() , f.getRentalRate()))
                .collect(Collectors.joining("\n"));
    }
}
