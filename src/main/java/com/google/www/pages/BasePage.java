package com.google.www.pages;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.google.www.utility.Utility;

import io.cucumber.java.Scenario;

public class BasePage {

	public HashMap<String, WebDriver> browserMap = new HashMap<String, WebDriver>();
	
	private static Logger logger = LogManager.getLogger(BasePage.class);

	public BasePage(WebDriver browser) {
		browserMap.put(Thread.currentThread().getName(), browser);
	}
	
	public void unsetBrowser() {
		browserMap.put(Thread.currentThread().getName(), null);
	}
	
	public WebDriver getBrowser() {
		return browserMap.get(Thread.currentThread().getName());
	}
	
	/*
	 * Function Name: clickElementIfExists
	 * Objective: To click an element if it is visible
	 * Date Created: 05/17/2019
	 * Date Modified: 05/17/2019
	 * Changes Made: Initial version
	 */
	public void clickElementIfExists(String elementName, WebElement element) {
		try {
			element.click();
		}catch(Exception e) {
			Assert.fail("Error in locating element : " + elementName + " due to : " + e.getMessage());
		}
	}
	
	public void waitUntil(int time)
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/*
	 * Function Name: verifyLabelText
	 * Objective: To verify the text of a label element
	 * Date Created: 03/12/2020 Date Modified: 03/12/2020
	 * Changes Made: Initial version
	 */
	public void verifyLabelText(Scenario currentScenario, WebElement element, String expectedText, String message) throws InterruptedException {
		expectedText = expectedText.replaceAll("\\P{Print}", "");
		String actualText = element.getText().replaceAll("\\P{Print}", "");
		actualText = actualText.replaceAll("\\n", "").trim();
		logger.info("Actual text: " + actualText);
		logger.info("Expected text: " + expectedText);
		if(isElementDisplayed(currentScenario, element)) {
			if(!actualText.trim().equals(expectedText.trim())) {
				logger.info("Actual text length: " + actualText.length());
				logger.info("Expected text length: " + expectedText.length());
				JavascriptExecutor jse = (JavascriptExecutor) getBrowser();
				jse.executeScript("arguments[0].style.border='5px solid red'", element);
				Thread.sleep(1000);
				try {
					currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(getBrowser(), false).toPath()), "image/png", "");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Assert.fail(message + " expected value [" + expectedText + "] but actual value [" + element.getText() + "]");
			}
		}
	}

	public boolean isElementDisplayed(Scenario currentScenario, WebElement element) {
		try {
			if(element.isDisplayed()) return true;
			else return false;
		}catch(Exception e) {
//			try {
//				currentScenario.embed(Files.readAllBytes(Utility.captureScreenshot(getBrowser(), false).toPath()), "image/png");
//				Assert.fail("Error in locating webelement due to: " + e.getMessage());
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			return false;
		}
	}
	
	public void clickElement(Scenario currentScenario, WebElement element) {
		try {
			element.click();
		} catch(Exception e) {
			try {
				currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(getBrowser(), false).toPath()), "image/png", "");
				Assert.fail("Error in locating webelement due to: " + e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	}
	
	public void addScreenshot(Scenario scenario) {
		try {
			scenario.attach(Files.readAllBytes(Utility.captureScreenshot(getBrowser(), true).toPath()), "image/png", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectDropDownByVisibleText(Scenario currentScenario, WebElement element, String optionValue) throws IOException {
		Select select = new Select(element);
		try {
			select.selectByVisibleText(optionValue);
//			if(!select.getFirstSelectedOption().getText().equals(optionValue)){
//				currentScenario.embed(Files.readAllBytes(Utility.captureScreenshot(getBrowser(), false).toPath()), "image/png");
//				Assert.fail(optionValue + " was NOT selected");
//			}
		}catch(Exception e) {
			scrollToElement(currentScenario, element);
			element.click();
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(getBrowser(), false).toPath()), "image/png", "");
			Assert.fail(optionValue + " was NOT found in dropdown due to " + e.getMessage());
		}
	}

	private void scrollToElement(Scenario currentScenario, WebElement element) throws IOException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) getBrowser();
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(getBrowser(), false).toPath()), "image/png", "");
			Assert.fail("Error in locating webelement due to: " + e.getMessage());
		}	
	}
	
	/*
	 * Function Name: clickElementUsingJS
	 * Objective: To click a web element using Java Script Executor
	 * Date Created: 03/07/2019
	 * Date Modified: 03/07/2019
	 * Changes Made: Initial version
	 */
	public void clickElementUsingJS(WebDriver browser, WebElement element, Scenario currentScenario) throws IOException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) browser;
			js.executeScript("arguments[0].click()", element);
		}catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element. Please contact automation developer due to reason " + e.getMessage());
		}
	}
}
