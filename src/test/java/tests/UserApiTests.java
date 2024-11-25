package tests;
import com.expandtesting.api.UserApi;
import com.expandtesting.utils.AuthenticationTests;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static org.testng.TestRunner.PriorityWeight.priority;

@Slf4j
class UserApiTests extends AuthenticationTests {

    UserApi userApi;
    protected  static   String token;
    SoftAssert softAssert;


    @BeforeAll
    public static void registerUser() throws IOException{
        setup();

        Response response=registerNewUser();
        SoftAssert softAssert1=new SoftAssert();

        softAssert1.assertEquals(response.statusCode(),201,"status code must be 201");
        String message=response.jsonPath().getString("message");
        softAssert1.assertEquals(message,"User account created successfully","Message should match the expected value");

        Response response1=login();
        softAssert1.assertEquals(response1.statusCode(),200,"status code must be 200");
        String LoginMessage=response1.jsonPath().getString("message");
        softAssert1.assertEquals(LoginMessage,"Login successful","Message should match the expected value");
        token = response1.jsonPath().getString("data.token");

        softAssert1.assertAll();
    }

    @Test
   public void retrieveUserInformation() {
        userApi=new UserApi();
        softAssert=new SoftAssert();

       Response response= userApi.retrieveUserInformation(token);
       response.prettyPrint();
       softAssert.assertEquals(response.statusCode(),200,"status code must be 200");
       String message=response.jsonPath().getString("message");
       softAssert.assertEquals(message,"Profile successful","Message should match the expected value");

       softAssert.assertAll();
    }

    @Test
    public void Logout_User() throws IOException {
        userApi = new UserApi();
        softAssert = new SoftAssert();
        registerUser();
        Response response = userApi.logoutUser(token);

        softAssert.assertEquals(response.statusCode(), 200, "status code must be 200");
        String message = response.jsonPath().getString("message");
        softAssert.assertEquals(message, "User has been successfully logged out", "Message should match the expected value");

        softAssert.assertAll();
    }

    @Test
    public void delete_User() {
        userApi = new UserApi();
        softAssert = new SoftAssert();

        Response response = userApi.deleteUser(token);
        softAssert.assertEquals(response.statusCode(), 200, "status code must be 200");
        String message = response.jsonPath().getString("message");
        softAssert.assertEquals(message, "Account successfully deleted", "Message should match the expected value");

        softAssert.assertAll();
    }

    @AfterAll
    public static void endUserEndpoint() {
        System.out.println("some Users endpoint Implemented and executed");
    }


}