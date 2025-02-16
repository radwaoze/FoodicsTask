import com.github.javafaker.Faker;
import data_driven.ReadPropertiesFile;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static baseSetup.SetUpRequests.SetUpRequest;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class UpdateUserTest extends BaseTest {
    static ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    @Test(dependsOnMethods = "CreateUserTest.testCreateUser")
    public void testUpdateUser() throws IOException {
        Faker faker = new Faker();
        Map<String, Object> updatedPayload = new HashMap<>();

        try {
            String updatedName = faker.name().fullName();
            String updatedJob = faker.job().position();

            updatedPayload.put("name", updatedName);
            updatedPayload.put("job", updatedJob);

            System.out.println("Updating user ID " + userId + " at URL: " +
                    getPropValue.readPropertiesFile("api.users") + "/" + userId);

            Response response = given()
                    .spec(SetUpRequest())
                    .contentType("application/json")
                    .body(updatedPayload)
                    .put(getPropValue.readPropertiesFile("api.users") + "/" + userId)
                    .then().log().all().extract().response();

            assertEquals(response.getStatusCode(), 200,
                    "Update failed. Response: " + response.getBody().asString());
            System.out.println("Successfully updated user details");

            if (!response.toString().contains("name") || !response.toString().contains("job")) {
                System.out.println("Update not persisted for user ID: " + userId);
            }


        } catch (Exception e) {
            System.err.println("User update failed: " + e.getMessage());
            throw e;
        }
    }
}