package com.expandtesting.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static com.expandtesting.utils.AuthenticationTests.requestSpec;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Slf4j

public class UserApi {


    // Returns the profile information for the logged-in current user.
    public  Response retrieveUserInformation(String My_token) {
        return given(requestSpec)
                .header("x-auth-token", My_token)
                .when().get("/users/profile").then().extract().response();
    }

    //logout user
    public Response logoutUser(String My_token) {
        return given(requestSpec).header("x-auth-token", My_token)
                .contentType(ContentType.JSON)
                .when().delete("/users/logout")
                .then()
                .extract().response();

    }

    //delete User from database
    public  Response deleteUser(String My_token) {
        return given(requestSpec)
                .header("x-auth-token", My_token)
                .contentType(ContentType.JSON)
                .when().delete("/users/delete-account").then()
                .log().all().statusCode(200)
                .body("message", equalTo("Account successfully deleted"))
                .extract().response();
    }


}
