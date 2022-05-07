package com.google.www.steps;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.google.www.cucumber.TestContext;
import com.google.www.managers.FileReaderManager;
import com.google.www.pages.GooglePage;
import com.google.www.utility.LaunchBrowser;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GoogleSteps {

	WebDriver browser;

	Scenario currentScenario;
	TestContext testContext;

	GooglePage googlePage;
	Map<String, String> record = new HashMap<>();
	
	private static Logger logger;

	public GoogleSteps(TestContext context) {
		testContext = context;
		googlePage = testContext.getPageObjectManager().getGooglePage();
		logger = LogManager.getLogger(GoogleSteps.class);
		record = new HashMap<String, String>();
		currentScenario = (Scenario) context.getScenarioContext().getContext("currentScenario");
	}

	@Given("I connect to https:\\/\\/www.google.com")
	public void i_connect_to_https_www_google_com() {
		logger.info("Connecting " + FileReaderManager.getInstance().getConfigReader().getURL());
		browser = testContext.getWebDriverManager().getDriver();
		browser.get(FileReaderManager.getInstance().getConfigReader().getURL());
	}

	@Then("I should see Google Home page")
	public void i_should_see_google_home_page() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

}
