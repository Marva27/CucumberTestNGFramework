package com.google.www.dataprovider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.www.enums.DriverType;
import com.google.www.enums.ExecutionType;

public class ConfigFileReader {

	private Properties properties;
	private final String propertyFilePath= "configs//Configuration.properties";
	 
	 
	 public ConfigFileReader(){
		 BufferedReader reader;
		 try {
			 reader = new BufferedReader(new FileReader(propertyFilePath));
			 properties = new Properties();
			 try {
				 properties.load(reader);
				 reader.close();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
			 throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		 } 
	 }
	 
	 public String getStubURL() {
		 String url = properties.getProperty("stubURL");
		 if(url != null) return url;
		 else throw new RuntimeException("stubURL not specified in the Configuration.properties file.");
	 }
	 
	 public String getURL() {
		 String url = properties.getProperty("url");
		 if(url != null) return url;
		 else throw new RuntimeException("url not specified in the Configuration.properties file.");
	 }
	 
	public String getRouteOneUrl() {
		String url = properties.getProperty("routeOneURL");
		if(url != null) return url;
		else throw new RuntimeException("routeOneURL not specified in the Configuration.properties file.");
	}

	public String getRouteOneUsername() {
		String routeOneUserName = properties.getProperty("routeOneUserName");
		if(routeOneUserName != null) return routeOneUserName;
		else throw new RuntimeException("routeOneUserName not specified in the Configuration.properties file.");
	}

	public String getRouteOnePassword() {
		String routeOnePassword = properties.getProperty("routeOnePassword");
		if(routeOnePassword != null) return routeOnePassword;
		else throw new RuntimeException("routeOnePassword not specified in the Configuration.properties file.");
	}
 	 
	public DriverType getBrowser() {
		String browserName = properties.getProperty("browser");
		if(browserName == null || browserName.equals("chrome")) return DriverType.CHROME;
		else if(browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
		else if(browserName.equals("iexplorer")) return DriverType.INTERNETEXPLORER;
		else throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
	}
		 
	public ExecutionType getExecution() {
		String executionType = properties.getProperty("execution");
		if(executionType == null || executionType.equalsIgnoreCase("local")) return ExecutionType.LOCAL;
		else if(executionType.equals("remote")) return ExecutionType.REMOTE;
		else throw new RuntimeException("Execution Type Key value in Configuration.properties is not matched : " + executionType);
	}	
}
