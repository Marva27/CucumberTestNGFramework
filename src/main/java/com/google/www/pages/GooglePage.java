package com.google.www.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class GooglePage extends BasePage {
	
	private static Logger logger = LogManager.getLogger(GooglePage.class);

	public GooglePage(WebDriver browser) {
		super(browser);
		PageFactory.initElements(browser, this);
	}
	
	

}
