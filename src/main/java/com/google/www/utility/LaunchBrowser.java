package com.google.www.utility;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class LaunchBrowser {

	@SuppressWarnings("deprecation")
	public static WebDriver initBrowser(String browserType) {
		if(browserType.equalsIgnoreCase("CHROME")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
			options.addArguments("--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
			options.setExperimentalOption("useAutomationExtension", false);
			return new ChromeDriver(options);
		} else if(browserType.equalsIgnoreCase("MOZILLA")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//drivers//geckodriver.exe");
	        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
	        capabilities.setCapability("marionette", true);
	        return new FirefoxDriver(capabilities);
		} else if(browserType.equalsIgnoreCase("IE")) {
			InternetExplorerOptions IE32options = new InternetExplorerOptions();
			IE32options.setCapability("ignoreProtectedModeSettings", true);
			IE32options.setCapability("acceptSslCerts", true);
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//drivers//IEDriverServer.exe");
			return new InternetExplorerDriver(IE32options);
		} else if(browserType.equals("EDGE")) {
			DesiredCapabilities capabilities = DesiredCapabilities.edge();
			capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "//drivers//MicrosoftWebDriver.exe");
			return new EdgeDriver(capabilities);
		} else if(browserType.equals("SAFARI")) {
			Assert.fail("No Safari browser available on local machine. Update the execution variable as remote and try again.");
		}
		return null;
	}
	
	public static WebDriver initRemoteBrowser(String browserType) {
		String ipAddress = "10.130.50.35"; //IP Address of TD9723868-WIN10
		RemoteWebDriver driver = null;
		DesiredCapabilities caps;
		String urlFormat = "http://" + ipAddress + ":" + 4444 + "/wd/hub";
		try {
			if(browserType.equalsIgnoreCase("CHROME")) {
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("useAutomationExtension", false);
		        caps = DesiredCapabilities.chrome();
		        caps.setCapability(ChromeOptions.CAPABILITY, options);
		        driver = new RemoteWebDriver(convertToURLFormat(urlFormat), caps);
		        return driver;
			} else if(browserType.equalsIgnoreCase("EDGE")) {
				caps = DesiredCapabilities.edge();
				driver = new RemoteWebDriver(convertToURLFormat(urlFormat), caps);
				return driver;
			} else if(browserType.equals("IE")) {
				DesiredCapabilities capabilities2 = DesiredCapabilities.internetExplorer();
	            capabilities2.setBrowserName("internet explorer");
	            capabilities2.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	            capabilities2.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
	            capabilities2.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	            capabilities2.setCapability("ignoreProtectedModeSettings", true);
	            capabilities2.setCapability("nativeEvents", false);
	            capabilities2.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
	            capabilities2.setCapability(InternetExplorerDriver.LOG_LEVEL, "DEBUG");
	            capabilities2.setPlatform(Platform.WINDOWS);
	            driver = new RemoteWebDriver(convertToURLFormat(urlFormat),capabilities2);
				return driver;
			} else if(browserType.equals("MOZILLA")) {
				caps = DesiredCapabilities.firefox();
				driver = new RemoteWebDriver(convertToURLFormat(urlFormat), caps);
				return driver;
			} else if(browserType.equals("SAFARI")) {
		        ipAddress = "10.130.50.19"; //IP from MAC
		        caps = DesiredCapabilities.safari();
		        urlFormat = "http://" + ipAddress + ":" + 8055 + "/wd/hub";
		        driver = new RemoteWebDriver(convertToURLFormat(urlFormat), caps);
		        return driver;
			}
		}catch(Exception e) {
			Assert.fail(e.getMessage());
		}
		return null;
	}
	
	private static URL convertToURLFormat(String x){
        URL url = null;
        try {
            url = new URL(x);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }
        return url; 
	}
}
