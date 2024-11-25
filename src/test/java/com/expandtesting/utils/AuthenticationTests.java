package com.expandtesting.utils;
import com.expandtesting.notes.models.CreateNote;
import com.expandtesting.user.models.Login;
import com.expandtesting.user.models.UserRegister;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.time.Instant;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
@Slf4j

public class AuthenticationTests {

    public static RequestSpecification requestSpec;
    public static UserRegister userRegister;
    public static CreateNote createNote;


    public static void setup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://practice.expandtesting.com/notes/api")
                .setContentType("application/json").setAccept("application/json")
                .build();
    }

    //create new user
    public  static Response registerNewUser() throws IOException {

        userRegister = TestUtils.readJsonFromFile("src/test/resources/testdata.user/user_create.json", UserRegister.class);

        // user Randomize Email
        userRegister.setEmail("test" + Instant.now().toEpochMilli() + "@gmail.com");
        log.info(String.format("User Data {}:%s", userRegister));

        return given(requestSpec)
                .body(userRegister)
                .when()
                .post("/users/register")
                .then().extract().response();
    }

    //login user
    public static Response login() throws IOException {

        Login login = new Login();
        login.setPassword(userRegister.getPassword());
        login.setEmail(userRegister.getEmail());

        return given(requestSpec)
                .body(login)
                .when().post("/users/login")
                .then().log().body().extract().response();
    }


    //creates a new note with the given title, description, category and user id.
    public  static String create_Note(String token) throws IOException {

        createNote = TestUtils.readJsonFromFile("src/test/resources/testdata.notes/note_create.json", CreateNote.class);
        log.info(String.format("User notes {}:%s", createNote));

        Response response = given(requestSpec)
                .header("x-auth-token", token)
                .log().uri() // Log the full URI
                .log().headers() // Log headers for debugging
                .body(createNote)
                .when()
                .post("/notes")
                .then()
                .log().all().statusCode(200)
                .body("message", equalTo("Note successfully created"))
                .extract().response();
        return response.jsonPath().getString("data.id");
    }

}
