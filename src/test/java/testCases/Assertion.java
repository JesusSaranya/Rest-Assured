package testCases;

import static io.restassured.RestAssured.given;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Assertion {

	SoftAssert softassert;
    String baseURi = "https://techfios.com/api-prod/api/product";
	
	@Test
	public void readOneProduct()
	{
		softassert = new SoftAssert();
		
		Response response =
				
		 given()		 
		    .baseUri(baseURi)
		    .header("Content-Type", "application/json")
		    .auth().preemptive().basic("demo@techfios.com", "abc123")
		    .queryParam("id","6154").
		  when()
		    .get("/read_one.php").
		  then()
		    .extract().response();
		  
		 
		int statusCode = response.getStatusCode();
		System.out.println("Response code :" + statusCode);
		//Assert.assertEquals(statusCode, 201);
		softassert.assertEquals(statusCode, 201);
		
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response Time :" + responseTime);
		if(responseTime<=2000)
		{
			System.out.println("ResponseTime within range");
		}
		else
		{
			System.out.println("ResponseTime not within range!!!!");
		}
		
		
		String responseHeader = response.getHeader("Content-Type");
		System.out.println("Response Header :" + responseHeader);
		//Assert.assertEquals(responseHeader, "application/json");
		softassert.assertEquals(responseHeader, "application/json");
		
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body :" + responseBody);
		
		
		JsonPath js = new JsonPath(responseBody);
		String productName = js.getString("name");
		System.out.println("Product Name :" + productName);
		//Assert.assertEquals(productName, "Amazing Pillow 2.0 By MD");
		softassert.assertEquals(productName, "Amazing Pillow 2.0 By MD");
		
		String productPrice = js.getString("price");
		System.out.println("Product Price :" + productPrice);
		//Assert.assertEquals(productPrice, "199");
		softassert.assertEquals(productPrice, "199");
		
		String productDescription = js.getString("description");
		System.out.println("Product Decsription :" + productDescription);
		//Assert.assertEquals(productDescription, "The best pillow for amazing programmers.");
		softassert.assertEquals(productDescription, "The best pillow for amazing programmers.");
		
        
		String productCateGoryID = js.getString("category_id");
		System.out.println("Product Category ID :" + productCateGoryID);
		//Assert.assertEquals(productCateGoryID, "2");
		softassert.assertEquals(productCateGoryID, "2");
		
		String productCateGoryName = js.getString("category_name");
		System.out.println("Product Category Name :" + productCateGoryName);
		//Assert.assertEquals(productCateGoryName, "Electronics");
		softassert.assertEquals(productCateGoryName, "Electronics");
	
		softassert.assertAll();
		
		
	}
}
