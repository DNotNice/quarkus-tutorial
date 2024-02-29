import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.devansh.app.model.FilmEntity;
import org.junit.jupiter.api.Test;
import repository.filmRepository;

import java.util.Optional;

import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class FimRepositoryTest {
    @Inject
    filmRepository FilmRepository;

    @Test
    public  void test(){
        Optional<FilmEntity> film = FilmRepository.getFilm((short) 5);
        assertTrue(film.isPresent());
        assertEquals("AFRICAN EGG" , film.get().getTitle());
    }
}
