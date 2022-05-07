package com.google.www.managers;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.google.www.enums.DriverType;


public class WebDriverManager {
	 private WebDriver driver;
	 private static DriverType driverType;
	 
	 private static Logger logger = LogManager.getLogger(WebDriverManager.class);
	 
	 public WebDriverManager() {
		 driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
	 }
	 
	 public WebDriver getDriver() {
		 if(driver == null) driver = createDriver();
		 driver.manage().window().maximize();
		 return driver;
	 }
	 
	 private WebDriver createDriver() {
		 try {
			 switch (System.getProperty("execution")) {     
	         case "local" : driver = createLocalDriver();
	         	break;
	         case "remote" : driver = createRemoteDriver();
	         	break;
			 }
			 return driver;
		 }catch(Exception e) {
			 logger.error("Error opening browser due to " + e.getMessage());
			 Assert.fail("Error opening browser due to " + e.getMessage());
			 return null;
		 }
	 }
	 
	 private WebDriver createRemoteDriver() {
			String ipAddress = "10.130.50.35"; //IP Address of TD9723868-WIN10
			RemoteWebDriver driver = null;
			DesiredCapabilities caps;
			String urlFormat = "http://" + ipAddress + ":" + 4444 + "/wd/hub";
			switch(driverType) {
			case CHROME:
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("useAutomationExtension", false);
		        caps = DesiredCapabilities.chrome();
		        caps.setCapability(ChromeOptions.CAPABILITY, options);
		        driver = new RemoteWebDriver(convertToURLFormat(urlFormat), caps);
		        break;
			default:
				break;
			}
			return driver;
//			try {
//				if(driverType.equalsIgnoreCase("CHROME")) {
//					ChromeOptions options = new ChromeOptions();
//					options.setExperimentalOption("useAutomationExtension", false);
//			        caps = DesiredCapabilities.chrome();
//			        caps.setCapability(ChromeOptions.CAPABILITY, options);
//			        driver = new RemoteWebDriver(convertToURLFormat(urlFormat), caps);
//			        return driver;
//				} else if(browserType.equalsIgnoreCase("EDGE")) {
//					caps = DesiredCapabilities.edge();
//					driver = new RemoteWebDriver(convertToURLFormat(urlFormat), caps);
//					return driver;
//				} else if(browserType.equals("IE")) {
//					DesiredCapabilities capabilities2 = DesiredCapabilities.internetExplorer();
//		            capabilities2.setBrowserName("internet explorer");
//		            capabilities2.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//		            capabilities2.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
//		            capabilities2.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//		            capabilities2.setCapability("ignoreProtectedModeSettings", true);
//		            capabilities2.setCapability("nativeEvents", false);
//		            capabilities2.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
//		            capabilities2.setCapability(InternetExplorerDriver.LOG_LEVEL, "DEBUG");
//		            capabilities2.setPlatform(Platform.WINDOWS);
//		            driver = new RemoteWebDriver(convertToURLFormat(urlFormat),capabilities2);
//					return driver;
//				} else if(browserType.equals("MOZILLA")) {
//					caps = DesiredCapabilities.firefox();
//					driver = new RemoteWebDriver(convertToURLFormat(urlFormat), caps);
//					return driver;
//				} else if(browserType.equals("SAFARI")) {
//			        ipAddress = "10.130.50.19"; //IP from MAC
//			        caps = DesiredCapabilities.safari();
//			        urlFormat = "http://" + ipAddress + ":" + 8055 + "/wd/hub";
//			        driver = new RemoteWebDriver(convertToURLFormat(urlFormat), caps);
//			        return driver;
//				}
//			}catch(Exception e) {
//				Assert.fail(e.getMessage());
//			}
//			return null;
	 }
	 
	 private URL convertToURLFormat(String urlFormat) {
		 URL url = null;
		 try {
			 url = new URL(urlFormat);
		 }catch (MalformedURLException ex){
			 ex.printStackTrace();
		 }
		 return url; 
	}

	private WebDriver createLocalDriver() {
		 switch (driverType) {     
		 case FIREFOX : driver = new FirefoxDriver();
		 break;
		 case CHROME : 
			 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			 
			 ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-gpu","--ignore-certificate-errors");
			options.setExperimentalOption("useAutomationExtension", false);
			 //cOptions.addArguments("--headless");
			 options.addArguments("kiosk-printing");
			 driver = new ChromeDriver(options);
			 break;
		 case INTERNETEXPLORER : driver = new InternetExplorerDriver();
		 break;
		 }
		 driver.manage().window().maximize();
		 return driver;
	 } 
	 
	 public void closeDriver() {
		 driver.close();
		 driver.quit();
	 }
	 
}
