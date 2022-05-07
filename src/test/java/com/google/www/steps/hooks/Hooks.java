package com.google.www.steps.hooks;

import com.google.www.cucumber.TestContext;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	
	TestContext testContext;
	
	public Hooks(TestContext context) {
		testContext = context;
	}
	
	@Before
	public void testSetup(Scenario scenario) {
		testContext.scenarioContext.setContext("currentScenario", scenario);
	}
	
	@After
	public void tearDown() {
		testContext.getWebDriverManager().closeDriver();
	}

}
