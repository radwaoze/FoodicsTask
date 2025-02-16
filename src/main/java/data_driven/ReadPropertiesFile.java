package data_driven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ReadPropertiesFile {

    public String readPropertiesFile(String variable) throws IOException {
        Properties proObj = new Properties();

        try (FileInputStream fileObj = new FileInputStream("src/main/resources/config.properties")) {
            proObj.load(fileObj);
        }

        String choiceValue = proObj.getProperty(variable);
        System.out.println(choiceValue);
        return choiceValue;
    }
}
