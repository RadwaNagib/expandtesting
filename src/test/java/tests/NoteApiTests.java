package tests;

import com.expandtesting.api.NoteApi;
import com.expandtesting.utils.AuthenticationTests;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;


@Slf4j
class NoteApiTests extends AuthenticationTests {
    //instance declaration
    NoteApi noteApi;

    protected static String token;
    protected static String mid;

    @BeforeAll
    public static void  registerLoginUser() throws IOException{
        setup();
        AuthenticationTests.registerNewUser();
        Response response1=login();
        token = response1.jsonPath().getString("data.token");

        System.out.println("token:" + token);
        mid =create_Note(token);
    }

    @Test
   public void getAllNotes()  {
        //instance  Initialized
        noteApi=new NoteApi();

        Response response=noteApi.getAllNotes(token);
        SoftAssert softAssert=new SoftAssert();

        // Assert the HTTP status code
        softAssert.assertEquals(response.statusCode(), 200, "Status code should be 200");

        // Assert the response message
        String message = response.jsonPath().getString("message");
        softAssert.assertEquals(message, "Notes successfully retrieved", "Message should match the expected value");

        softAssert.assertAll();
    }

    @Test
    public void getNoteById() {
        noteApi=new NoteApi();
        SoftAssert softAssert=new SoftAssert();

        Response response=noteApi.getNoteById(token, mid);
        softAssert.assertEquals(response.statusCode(),200,"status code must be 200");
        String message=response.jsonPath().getString("message");
        softAssert.assertEquals(message,"Note successfully retrieved","Message should match the expected value");

        softAssert.assertAll();
    }

    @Test
   public void updateExistingNote() throws IOException {
        noteApi=new NoteApi();
        SoftAssert softAssert=new SoftAssert();

        Response response=noteApi.updateExistingNote(token, mid);
        softAssert.assertEquals(response.statusCode(),200,"status code must be 200");
        String message=response.jsonPath().getString("message");
        softAssert.assertEquals(message,"Note successfully Updated");

        softAssert.assertAll();
    }

    @Test
    public void updateCompletedStatusOfNote() {
        noteApi=new NoteApi();
        SoftAssert softAssert=new SoftAssert();

        Response response=noteApi.updateCompletedNote(token, mid);
        softAssert.assertEquals(response.statusCode(),200,"status code must be 200");
        response.then().log().all();
        String message=response.jsonPath().getString("message");
        softAssert.assertEquals(message,"Note successfully updated","Message should match the expected value");

        softAssert.assertAll();

    }

    @Test
    public void deleteNote()
    {
        noteApi=new NoteApi();
        SoftAssert softAssert=new SoftAssert();

        Response response=noteApi.deleteNoteById(token, mid);
        softAssert.assertEquals(response.statusCode(),200,"status code must be 200");
        String message=response.jsonPath().getString("message");
        softAssert.assertEquals(message,"Note successfully deleted","Message should match the expected value");

        softAssert.assertAll();
    }

    @AfterAll
    public static void endNoteEndpoint()
    {
        System.out.println("All Notes endpoints Implemented and executed");
    }

}
