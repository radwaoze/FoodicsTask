import data_driven.ReadPropertiesFile;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import java.io.IOException;

public class BaseTest {
    protected static String userId;
    protected static String userName;
    protected static String userJob;

    @BeforeSuite
        public void setup() throws IOException {

        }

}