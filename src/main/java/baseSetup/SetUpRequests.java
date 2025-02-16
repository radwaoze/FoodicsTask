package baseSetup;

import data_driven.ReadPropertiesFile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

public class SetUpRequests {
    static ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    public static RequestSpecification SetUpRequest() throws IOException {
        String baseUrl = getPropValue.readPropertiesFile("base.url");
        System.out.println("Using Base URL: " + baseUrl); // Debug log

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .build();
    }
}
