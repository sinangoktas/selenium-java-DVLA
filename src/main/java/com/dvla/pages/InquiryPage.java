package com.dvla.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class represents the Inquiry Page
 *
 */

public class InquiryPage {

    private WebDriverWait wait;

    @FindBy(css = "input.form-control[id='Vrm']")
    public WebElement QUERY_TEXTAREA;

    @FindBy(css = "button[name='Continue']")
    public WebElement QUERY_SUBMIT_BUTTON;

    public InquiryPage(WebDriver driver, int timeoutInSecs) {
        this.wait = new WebDriverWait(driver, timeoutInSecs);
        PageFactory.initElements(driver, this);
    }

    public void enterRegistrationNumber(String registrationNumber) {
        wait.until(ExpectedConditions.visibilityOf(QUERY_TEXTAREA));
        QUERY_TEXTAREA.sendKeys(registrationNumber);
    }

    public void submitRegistrationNumber() {
        wait.until(ExpectedConditions.visibilityOf(QUERY_SUBMIT_BUTTON));
        QUERY_SUBMIT_BUTTON.click();
    }


}
