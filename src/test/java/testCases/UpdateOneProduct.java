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

public class UpdateOneProduct {

	String baseURI = "https://techfios.com/api-prod/api/product";
	String filePath = "src\\main\\java\\data\\testData.json";
	HashMap<String,String> updatePayLoad;
	String updateProductID;
	String readOneProductId;
	
	
	
	public Map<String,String> updatepayloadMap()
	{
	updatePayLoad = new HashMap<String, String>	();
	
	updatePayLoad.put("id", "6154");
	updatePayLoad.put("name", "Amazing Pillow 2.0 By MD");
	updatePayLoad.put("price", "199");
	updatePayLoad.put("description", "The best pillow for amazing programmers.");
	updatePayLoad.put("category_id", "2");
	updatePayLoad.put("category_name", "Electronics");
		
	return updatePayLoad;	
		
	}
	
	
	@Test(priority=1)
	public void createOneproduct()
	{
		
		Response response = 
				
				given()
				  .baseUri(baseURI)
				  .header("Content-Type","application/json")
				  .auth().preemptive().basic("demo@techfios.com", "abc123")
				  .body(updatepayloadMap()).
				when() 
				  .put("/update.php").
		       then()
		           .extract().response();
		
		int statuscode = response.getStatusCode();
		System.out.println("StatusCode :" + statuscode);
		Assert.assertEquals(statuscode, 200);
		
		
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
		String updateproductmessage  = js.getString("message");
		System.out.println("Update Product Message :" +updateproductmessage);
		Assert.assertEquals(updateproductmessage, "Product was updated.");
		
	}
	
	

		@Test(priority=2)
		public void readOneProduct()
		{
			updateProductID = updatepayloadMap().get("id");
			readOneProductId = updateProductID;
			
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
			
			String expectproductName = updatepayloadMap().get("name");
			System.out.println("Expected Product Name :" + expectproductName);
			
			String expectproductPrice = updatepayloadMap().get("price");
			System.out.println("Expected Product Price :" + expectproductPrice);
			
			String expectproductDescription = updatepayloadMap().get("description");
			System.out.println("Expected Product Description :" + expectproductDescription);
			
			String expectproductCategoryID = updatepayloadMap().get("category_id");
			System.out.println("Expeceted Product Category ID :" + expectproductCategoryID);
			
			
			Assert.assertEquals(actualproductName,  expectproductName);
			Assert.assertEquals(actualproductPrice, expectproductPrice);
			Assert.assertEquals(actualproductDescription, expectproductDescription);
			Assert.assertEquals(actualproductCategoryID, expectproductCategoryID);
			
			
			
			
			
		}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
