package com.gokul.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("application.properties file not found");
            } else {
                properties.load(input);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static double getDoubleProperty(String key) {
        return Double.parseDouble(getProperty(key));
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
}
