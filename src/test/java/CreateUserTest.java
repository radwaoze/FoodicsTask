import baseSetup.SetUpRequests;
import com.github.javafaker.Faker;
import data_driven.ReadPropertiesFile;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreateUserTest extends SetUpRequests {
    static ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    @Test
    public void testCreateUser() throws IOException {
        Faker faker = new Faker();
        Map<String, Object> payload = new HashMap<>();

        try {
            String userName = faker.name().fullName();
            String userJob = faker.job().position();
            int userAge = faker.number().numberBetween(18, 65);

            payload.put("name", userName);
            payload.put("job", userJob);
            payload.put("age", userAge);

            System.out.println("Creating user with payload: " + payload);

            Response response = given().spec(SetUpRequest())
                    .contentType("application/json")
                    .body(payload)
                    .post(getPropValue.readPropertiesFile("api.users"))
                    .then()
                    .log().all()
                    .extract().response();
            System.out.println("Status Code: " + response.getStatusCode());

            assertEquals(response.getStatusCode(), 201,
                    "Create user failed - unexpected status code");
            assertEquals(response.jsonPath().getString("name"), userName);
            assertEquals(response.jsonPath().getString("job"), userJob);

            BaseTest.userId = response.jsonPath().getString("id");
            BaseTest.userName = response.jsonPath().getString("name");
            BaseTest.userJob = response.jsonPath().getString("job");

            System.out.println("Successfully created user ID: " + BaseTest.userId);

        } catch (Exception e) {
            System.err.println("User creation failed: " + e.getMessage());
            throw e;
        }
    }
}