package com.google.www.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;
	
public class ReportGenerator {
	
    @Test
    public void generateReport() throws IOException {
        File reportOutputDirectory = new File("target/advnced-cucumber-report");  
        
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add(".\\target\\jsonReports\\cucumber.json");

        String buildNumber = "1";																			
        String projectName = "Transistion FI";															
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);											
        configuration.addClassifications("OS", "Windows");
        if(System.getProperty("environment").equals("Stage") || System.getProperty("environment").equals("stage"))
        	configuration.addClassifications("Environment", "Stage");
        else if(System.getProperty("environment").equals("Test") || System.getProperty("environment").equals("test"))
        	configuration.addClassifications("Environment", "Test");
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);        	
        reportBuilder.generateReports();
    }
}
