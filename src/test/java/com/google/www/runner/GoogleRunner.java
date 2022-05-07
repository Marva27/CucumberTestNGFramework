package com.google.www.runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.www.utility.ReportGenerator;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(features = "src/test/resources/", glue = {
		"com.google.www.steps" }, tags = "@Google", plugin = { "pretty",
				// "json:target/cucumber-reports/CucumberTestReport.json",
				"rerun:target/cucumber-reports/rerun.txt", "json:target/jsonReports/cucumber.json" })

public class GoogleRunner {

	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		// FileUtils.cleanDirectory(new File(System.getProperty("user.dir") +
		// "\\screenshots\\"));
	}

//	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
//	public void feature(CucumberFeatureWrapper cucumberFeature) {
//		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
//	}

	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runScenario(pickle.getPickle());
	}

	@DataProvider
	public Object[][] scenarios() {
		return testNGCucumberRunner.provideScenarios();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
		System.out.println("Generating report....");
		ReportGenerator report = new ReportGenerator();
		report.generateReport();
		System.out.println("Done!!!!");
	}

}
