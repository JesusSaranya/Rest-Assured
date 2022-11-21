package testCases;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DeleteOpeProduct {

	
	String baseURI = "https://techfios.com/api-prod/api/product";
	String filePath = "src\\main\\java\\data\\testData.json";
	HashMap<String,String> deletePayLoad;
	String deleteProductID;
	String readOneProductId;
	
	
	
	public Map<String,String> deletepayloadMap()
	{
	deletePayLoad = new HashMap<String, String>();
	
	deletePayLoad.put("id", "6046");
	
	return deletePayLoad;	
		
	}
	
	
	@Test(priority=1)
	public void deleteOneproduct()
	{
		
		Response response = 
				
				given()
				  .baseUri(baseURI)
				  .header("Content-Type","application/json")
				  .auth().preemptive().basic("demo@techfios.com", "abc123")
				  .body(deletepayloadMap()).
				when() 
				  .delete("/delete.php").
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
		String deleteproductmessage  = js.getString("message");
		System.out.println("Delete Product Message :" +deleteproductmessage);
		Assert.assertEquals(deleteproductmessage, "Product was deleted.");
		
	}
	
	

		@Test(priority=2)
		public void readOneProduct()
		{
			deleteProductID = deletepayloadMap().get("id");
			readOneProductId = deleteProductID;
			
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
			Assert.assertEquals(statusCode, 404);
			
			String responseBody = response.getBody().asString();
			System.out.println("Read One Product Response Body :" + responseBody);
			
			JsonPath js = new JsonPath(responseBody);
			String actualproductMesssage = js.getString("message");
			System.out.println("Actual Product message :" + actualproductMesssage);
			Assert.assertEquals(actualproductMesssage,  "Product does not exist.");
			
			
			
			
		}

	
	
	
	
	
}
