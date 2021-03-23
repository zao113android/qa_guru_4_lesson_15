package tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utils.FileUtils.readStringFromFile;

public class ReqresTests extends TestBase {

    @Test
    void correctPaginationUsersListTest() {
        given()
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .log().body()
                .body("per_page",
                        is(6));
    }

    @Test
    void successUserByIdTest() {
        given()
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.id",
                        is(2));
    }

    @Test
    void userNotFoundTest() {
        given()
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404)
                .log().body()
                .body(is("{}"));
    }

    @Test
    void createUserTest() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"leader\" }")
                .post("/api/users")
                .then()
                .statusCode(201)
                .log().body()
                .body("id",
                        is(notNullValue()))
                .body("name",
                        is("morpheus"))
                .body("job",
                        is("leader"));
    }

    @Test
    void registerUserTest() {

        String data = readStringFromFile("./src/test/resources/register_data.txt");

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(data)
                .post("/api/register")
                .then()
                .statusCode(200)
                .log().body()
                .body("id",
                        is(notNullValue()))
                .body("token",
                        is(notNullValue()));
    }
}
