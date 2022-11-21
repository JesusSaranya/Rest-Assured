package testCases;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MergeClasses {

	
	String baseURI = "https://techfios.com/api-prod/api/product";
	String filePath = "src\\main\\java\\data\\testData.json";
	HashMap<String,String> createPayLoad;
	String firstProductID;
	String readOneProductId;
	
	
	
	public Map<String,String> createpayloadMap()
	{
	createPayLoad = new HashMap<String, String>	();
	createPayLoad.put("name", "Amazing Pillow 2.0 By MD");
	createPayLoad.put("price", "199");
	createPayLoad.put("description", "The best pillow for amazing programmers.");
	createPayLoad.put("category_id", "2");
	createPayLoad.put("category_name", "Electronics");
		
	return createPayLoad;	
		
	}
	
	
	@Test(priority=1)
	public void createOneproduct()
	{
		
		Response response = 
				
				given()
				  .baseUri(baseURI)
				  .header("Content-Type","application/json; charset=UTF-8")
				  .auth().preemptive().basic("demo@techfios.com", "abc123")
				  .body(createpayloadMap()).
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
	
	@Test(priority=2)
	public void readAllProducts()
	{
		
		/*given:all input details(baseURI,Headers,Authorization,Payload/Body,QueryParametes)
		when: submit api requests(Http method , Endpoint/Resource)
		then: validate response(status code, Headers, responseTime, Payload/Body)*/
		
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
		
		
		///RESPONSEBODY==============
		String responseBody = response.getBody().asString();
		
		
		
		////CONVERT STRING TO JSON TO VALIDATE THE ONE DATA===========
		JsonPath js = new JsonPath(responseBody);
		firstProductID = js.getString("records[0].id");
		System.out.println("First Product Id :" + firstProductID);
		
		if(firstProductID != null)
		{
			System.out.println("Response Body contains First Product ID");
		}
		
		else
		{
			System.out.println("Response Body does not contain first product ID");
		}
	}	
	
	
		@Test(priority=3)
		public void readOneProduct()
		{
			readOneProductId = firstProductID;
			
			Response response =
			 given()		 
			    .baseUri(baseURI)
			    .header("Content-Type", "application/json")
			    .auth().preemptive().basic("demo@techfios.com", "abc123")
			    .queryParam("id",readOneProductId).
			  when()
			    .get("/read_one.php").
			  then()
			    .extract().response();
			  
			 
			int statusCode = response.getStatusCode();
			System.out.println("Response code :" + statusCode);
			Assert.assertEquals(statusCode, 200);
			
			String responseBody = response.getBody().asString();
			System.out.println("Read One Product Response Body :" + responseBody);
			
			
			JsonPath js = new JsonPath(responseBody);
			String actualproductName = js.getString("name");
			System.out.println("Actual Product Name :" + actualproductName);
			
			String actualproductPrice = js.getString("price");
			System.out.println("Actual Product Price :" + actualproductPrice);
			
			String actualproductDescription = js.getString("description");
			System.out.println("Actual Product Description :" + actualproductDescription);
			
			String actualproductCategoryID = js.getString("category_id");
			System.out.println("Actual Product Category ID :" + actualproductCategoryID);
	
	        JsonPath js2 = new JsonPath(new File (filePath));
	       String expectedproductName =  js2.getString("name");
	       System.out.println("Expected Product Name :" + expectedproductName);
			
			String expectproductName = createpayloadMap().get("name");
			System.out.println("Expected Product Name :" + expectproductName);
			
			String expectproductPrice = createpayloadMap().get("price");
			System.out.println("Expected Product Price :" + expectproductPrice);
			
			String expectproductDescription = createpayloadMap().get("description");
			System.out.println("Expected Product Description :" + expectproductDescription);
			
			String expectproductCategoryID = createpayloadMap().get("category_id");
			System.out.println("Expeceted Product Category ID :" + expectproductCategoryID);
			
			
			Assert.assertEquals(actualproductName,  expectproductName);
			Assert.assertEquals(actualproductPrice, expectproductPrice);
			Assert.assertEquals(actualproductDescription, expectproductDescription);
			Assert.assertEquals(actualproductCategoryID, expectproductCategoryID);
			
			
			
			
			
		}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}	

