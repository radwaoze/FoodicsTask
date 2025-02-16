import data_driven.ReadPropertiesFile;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.IOException;
import static baseSetup.SetUpRequests.SetUpRequest;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class RetrieveUserTest extends BaseTest {
    static ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    @Test(dependsOnMethods = "CreateUserTest.testCreateUser")
    public void testRetrieveUser() throws IOException {
        try {
            System.out.println("Attempting to retrieve user ID: " + userId);

            Response response = given().spec(SetUpRequest())
                    .get(getPropValue.readPropertiesFile("api.users") + "/" + userId).then()
                    .log().all()
                    .extract().response();

            System.out.println("Status Code: " + response.getStatusCode());

            int statusCode = response.statusCode();
            String responseBody = response.getBody().asString().trim();

            switch(statusCode) {
                case 200: {
                    assertEquals(response.jsonPath().getString("data.id"), userId, "ID mismatch");
                    assertEquals(response.jsonPath().getString("data.name"), userName, "Name mismatch");
                    assertEquals(response.jsonPath().getString("data.job"), userJob, "Job mismatch");
                    System.out.println("Successfully retrieved user details");
                    break;
                }
                case 404:
                {
                    boolean isValidEmptyResponse = responseBody.isEmpty() ||
                            responseBody.equals("{}");

                    assertTrue(isValidEmptyResponse,
                            "Invalid error response body: " + responseBody);

                    System.out.println("User not found ");
                    break;
                }
                default:
                    fail("Unexpected status code: " + statusCode);
            }
        } catch (Exception e) {
            System.err.println("User retrieval failed: " + e.getMessage());
            throw e;
        }
    }

}