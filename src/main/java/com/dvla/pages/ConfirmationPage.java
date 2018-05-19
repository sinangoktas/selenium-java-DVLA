package com.dvla.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class represents the Confirmation Page
 *
 */

public class ConfirmationPage {

	private WebDriverWait wait;

	@FindBy(css = ".heading-large")
	public WebElement CONFIRMATION_QUESTION;

	@FindBy(css = ".list-summary-item:nth-child(2)")
	public WebElement VEHICLE_MAKE;

	@FindBy(css = ".list-summary-item:nth-child(3)")
	public WebElement VEHICLE_COLOUR;


	public ConfirmationPage(WebDriver driver, int timeoutInSecs) {
		this.wait = new WebDriverWait(driver, timeoutInSecs);
		PageFactory.initElements(driver, this);
	}


}
