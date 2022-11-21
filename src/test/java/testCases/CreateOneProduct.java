package testCases;

import org.testng.Assert;


import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class CreateOneProduct {

	String baseURI = "https://techfios.com/api-prod/api/product";
	String filePath = "src\\main\\java\\data\\testData.json";
	
	
	@Test
	public void createOneproduct()
	{
		
		Response response = 
				
				given()
				  .baseUri(baseURI)
				  .header("Content-Type","application/json; charset=UTF-8")
				  .auth().preemptive().basic("demo@techfios.com", "abc123")
				  .body(new File(filePath)).
				when() 
				  .post("/create.php").
		       then()
		           .extract().response();
		
		int statuscode = response.getStatusCode();
		System.out.println("StatusCode :" + statuscode);
		Assert.assertEquals(statuscode, 201);
		
		
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response time :" + responseTime);
		if(responseTime<=2000)
		{
			System.out.println("Response time within range.");
		}
		else
		{
			System.out.println("Response time out of range!!!!");
		}
		
		
		String responseHeader = response.getHeader("Content-Type");
		System.out.println("Response Header :" + responseHeader);
		Assert.assertEquals(responseHeader, "application/json; charset=UTF-8");
		
		
		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody :" + responseBody);
		
		JsonPath js= new JsonPath(responseBody);
		String productmessage  = js.getString("message");
		System.out.println("Product Message :" +productmessage);
		Assert.assertEquals(productmessage, "Product was created.");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
