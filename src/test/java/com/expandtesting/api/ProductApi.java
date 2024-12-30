package com.expandtesting.api;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static com.expandtesting.utils.AuthenticationTests.requestSpec;
import static io.restassured.RestAssured.given;
@Slf4j
public class ProductApi {

    //API 1: Get All Products List
    public Response getAllProductsList()
    {
        return given(requestSpec).log().uri() // Log the full URI
            .log().headers() // Log headers for debugging
            .when().get("/productsList")
            .then().extract().response();
    }

    //API 2: POST To All Products List
    public Response postAllProductsList()
    {
        return given(requestSpec).log().uri().
               log().headers().when().post("/productsList")
                .then().extract().response();
    }

    //API 3: Get All Brands List
    public Response getAllBrandsList()
    {
        return given(requestSpec).log().uri()
                .log().headers().when().get("/brandsList")
                .then().extract().response();
    }
}
