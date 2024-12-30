package tests;

import com.expandtesting.api.ProductApi;
import com.expandtesting.utils.AuthenticationTests;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.asserts.SoftAssert;

import static com.expandtesting.utils.TestDataUtils.getJsonData;

public class ProductApiTests extends AuthenticationTests {

//instance declaration
    ProductApi productApi;
    private static final String baseurl1=getJsonData("browser_navigate","baseurl_automation_exercise");

    @BeforeAll
    public static void navigateBaseUrl()
    {
       setup(baseurl1);
    }

    @Test
    public void verifyListAllProducts() {
        //instance  Initialized
        productApi = new ProductApi();

        //declare soft assert
        SoftAssert softAssert = new SoftAssert();

        Response response = productApi.getAllProductsList();
        System.out.println("my response is :" + response.asString());

        //assert on status code
        softAssert.assertEquals(response.statusCode(), 200, "error on status code");

        //assert on product id
        String products_id = response.jsonPath().getString("products.id[0]");
        softAssert.assertEquals(products_id, "1", "products field not retrieved");

        //assert on product name
        String products_name = response.jsonPath().getString("products.name[0]");
        softAssert.assertEquals(products_name, "Blue Top", "products name not retrieved");

        softAssert.assertAll();
    }

    @Test
    public void verifyListAllProductsUsingPost()
    {
        //declare soft assert
        SoftAssert softAssert=new SoftAssert();

        //instance  Initialized
        productApi = new ProductApi();

        Response response=productApi.postAllProductsList();

        //verify on status code
        softAssert.assertEquals(response.statusCode(),200,"status code not equal 405");

        //assert on message
        String response_message=response.jsonPath().getString("message");
        softAssert.assertEquals(response_message,"This request method is not supported.","wrong on response message");

        softAssert.assertAll();
    }

    @Test
    public void verifyListAllBrands()
    {

        //instance  Initialized
        productApi = new ProductApi();

        //create soft assert instance
        SoftAssert softAssert=new SoftAssert();

        Response response=productApi.getAllBrandsList();

        //return response
        System.out.println(response.asString());

        //assert on status code
        softAssert.assertEquals(response.statusCode(),200,"list brands status code not equal 200");

        //assert on first brand name
        String first_brand_name=response.jsonPath().getString("brands.brand[0]");

        softAssert.assertEquals(first_brand_name,"Polo","first brand name not as expected");
        softAssert.assertAll();


    }

    @AfterAll
    public static void endNoteEndpoint()
    {
        System.out.println("All Notes endpoints Implemented and executed");
    }

}
