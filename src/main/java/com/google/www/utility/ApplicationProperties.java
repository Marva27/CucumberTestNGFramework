package com.google.www.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {
	
	private static FileInputStream fis;
	private static Properties properties;
	
	public synchronized static void initProperties() throws IOException {
		try {
			if(System.getProperty("environment").equals(""))
				throw new RuntimeException("Specify value for environment variable. Allowed values are test or stage.");
			if(System.getProperty("environment").equalsIgnoreCase("test")){
				fis = new FileInputStream(System.getProperty("user.dir")+"\\resources\\application-test.properties");
			}else if(System.getProperty("environment").equalsIgnoreCase("stage")) {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\resources\\application-stage.properties");
			}else if(System.getProperty("environment").equalsIgnoreCase("prod")) {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\resources\\application-prod.properties");
			}
		} catch(Exception e) {
			fis = new FileInputStream(System.getProperty("user.dir")+"\\resources\\application-stage.properties");
		}
		properties = new Properties();
		properties.load(fis);
	}
	
	public static String getProperty(String propertyKey) {
		return properties.getProperty(propertyKey);
	}

}
