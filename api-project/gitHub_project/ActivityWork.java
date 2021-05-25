package gitHub_project;
import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
public class ActivityWork {
	
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	
	String sshKey;
	int sshKeyId;
	
	@BeforeClass
	public void BeforeClass() {
	
		requestSpec = new RequestSpecBuilder()
				
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "token xxxx")
				.setBaseUri("https://api.github.com")
				.build();
		sshKey = "ssh-rsa xxxxx";
	}
	@Test(priority = 1)

	public void addKeys() {
		String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
		Response response = given().spec(requestSpec) // Use requestSpec
				.body(reqBody) 
				.when().post("/user/keys"); 
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		sshKeyId = response.then().extract().path("id");
	    response.then().statusCode(201);
	}
	
	@Test(priority = 2)

	public void getKeys() {
		Response response = given().spec(requestSpec) 
				.when().get("/user/keys"); 
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		response.then().statusCode(200);
	}
	
	@Test(priority = 3)

	public void deleteKeys() {
		Response response = given().spec(requestSpec) 
				.pathParam("keyId", sshKeyId).when().delete("/user/keys/{keyId}"); 
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(204);
	}
}


















