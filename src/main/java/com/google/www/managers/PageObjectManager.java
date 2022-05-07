package com.google.www.managers;

import org.openqa.selenium.WebDriver;

import com.google.www.pages.GooglePage;

public class PageObjectManager {

	private WebDriver driver;
		
	//Google Page Objects
	private GooglePage googlePage;
		
	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}
	
	public GooglePage getGooglePage() {
		return (googlePage == null) ? googlePage = new GooglePage(driver) : googlePage;
	}
}