package com.expandtesting.api;

import com.expandtesting.notes.models.UpdateNote;
import com.expandtesting.utils.TestUtils;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.expandtesting.utils.AuthenticationTests.requestSpec;
import static io.restassured.RestAssured.given;

@Slf4j

public class NoteApi {
    public static UpdateNote updateExistNote;


    //Retrieve a list of notes for the authenticated user
    public  Response getAllNotes(String token) {
        return given(requestSpec)
                .header("x-auth-token", token)
                .log().uri() // Log the full URI
                .log().headers() // Log headers for debugging
                .when().get("/notes")
                .then().extract().response();
    }


    //Retrieve a note by its ID
    public Response getNoteById(String token, String myid) {
        return given(requestSpec)
                .header("x-auth-token", token)
                .pathParam("id", myid.trim())
                .when()
                .get("/notes/{id}").then()
                .extract().response();

    }

    //Creates a new note with the given title, description, category and user id.
    public Response updateExistingNote(String token, String mid) throws IOException {
        updateExistNote = TestUtils.readJsonFromFile("src/test/resources/testdata.notes/note_update.json", UpdateNote.class);
        log.info("update note file :{}", updateExistNote);

        return given(requestSpec)
                .header("x-auth-token", token)
                .pathParam("id", mid.trim())
                .body(updateExistNote)
                .when().put("/notes/{id}")
                .then()
                .extract().response();
    }

    //Update the completed attribute of the note with the specified id
    public Response updateCompletedNote(String token, String mid) {
        return given(requestSpec)
                .header("x-auth-token",token)
                .header("content-type","text/html")
                .pathParam("id",mid.trim())
//                .formParam("data.completed",true)
//                 .when().patch("/notes/{id}").then()
//                .extract().response();
                .formParam("{\"data\": {\"completed\": false}}") // Send a JSON payload
                .when().patch("/notes/{id}")
                .then().extract().response();
    }

    //Deletes a note with the specified ID.
    public Response deleteNoteById(String token, String mid) {
        return given(requestSpec)
                .header("x-auth-token", token)
                .pathParam("id", mid.trim())
                .when().delete("/notes/{id}")
                .then().extract().response();
    }

}
