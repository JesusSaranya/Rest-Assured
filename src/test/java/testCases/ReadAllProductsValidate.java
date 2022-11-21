package testCases;

import org.testng.Assert;
import org.testng.annotations.Test; 
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.util.concurrent.TimeUnit;

public class ReadAllProductsValidate {

	String baseURI = "https://techfios.com/api-prod/api/product";
	
	
	@Test
	public void readAllProducts()
	{
		
		//given:all input details(baseURI,Headers,Authorization,Payload/Body,QueryParametes)
		//when: submit api requests(Http method , Endpoint/Resource)
		//then: validate response(status code, Headers, responseTime, Payload/Body)
		
		Response response =
		  given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123").
		  when()
		        .get("/read.php").
		  then()
		       .extract().response();
		
		///STATUS CODE=========
		int responseCode = response.getStatusCode();
		System.out.println("Response code : " + responseCode);
		Assert.assertEquals(responseCode, 200);
		
		///RESPONSEtIME======
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response Time :" +responseTime);
		if(responseTime<=2000)
		{
			System.out.println("Response Time within Range. ");
		}
		else
		{
			System.out.println("Outof Range!!!!!!");
		}
		
		///RESPONSE HEADER=========
		String responseHeader = response.getHeader("Content-Type");
		System.out.println("Response Header :"  + responseHeader);
		Assert.assertEquals(responseHeader, "application/json; charset=UTF-8");
		
		///RESPONSEBODY==============
		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody : " + responseBody);
		
		
		////CONVERT STRING TO JSON TO VALIDATE THE ONE DATA===========
		JsonPath js = new JsonPath(responseBody);
		String firstProductID = js.getString("records[0].id");
		System.out.println("First Product Id :" + firstProductID);
		
		if(firstProductID != null)
		{
			System.out.println("Response Body contains First Product ID");
		}
		
		else
		{
			System.out.println("Response Body does not contain first product ID");
		}
		
		
		
		
		
	/*String baseURI = "https://techfios.com/api-prod/api/product";	
		
	@Test
	public void readallProducts()
	{
		Response response =
				
		given()
			.baseUri(baseURI)
			.header("Content-Type", "application/json; charset=UTF-8")
			.auth().preemptive().basic("demo@techfios.com", "abc123").
		when()
		    .get("/read.php").
		then()
		  .extract().response();
		
		
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response Time :" + responseTime);
		if(responseTime<=3000)
		{
			System.out.println("Response Time is Within Range....");
		}
		else
		{
			System.out.println("ResponseTime not in range...");
		}
		
		
		int statuscode = response.getStatusCode();
		System.out.println("Status Code :" +statuscode);
		Assert.assertEquals(statuscode, 200);
		
		
		String ResponseHeader = response.getHeader("Content-Type");
		System.out.println("responseHeader :" + ResponseHeader);
		Assert.assertEquals(ResponseHeader, "application/json; charset=UTF-8");
		
		String responsebody = response.getBody().asString();
		System.out.println("Response body :" + responsebody);
		JsonPath js = new JsonPath(responsebody);
		String firstProductID = js.getString("records[0].id");
		System.out.println("First Product ID :" + firstProductID);
		
		if(firstProductID != null)
		{
			System.out.println("Response Body contains first product ID");
		}
		else
		{
			System.out.println("ResponseBody does not contain first product id...");
		}*/
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

