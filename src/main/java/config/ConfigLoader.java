package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static Properties properties;

    public ConfigLoader() {
    	if(properties == null) {
    		properties = new Properties();
    		loadProperties();
    	}
    }

    private void loadProperties() {
        // Using ClassLoader to load the properties file
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }

            // Load properties from the input stream
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
    	return properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
