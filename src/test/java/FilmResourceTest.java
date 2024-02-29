import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class FilmResourceTest {
    @Test
    public void test(){
             given()
                     .when().get("/film/343")
                     .then()
                     .statusCode(500)
                     .body(containsString("FULL FLATLINERS"));
         }
}
