package com.dvla.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class represents the Home Page
 *
 */

public class Homepage {

    private WebDriverWait wait;

    @FindBy(css = ".gem-c-button--start")
    public WebElement START_BUTTON;

    public Homepage(WebDriver driver, int timeoutInSecs) {
        this.wait = new WebDriverWait(driver, timeoutInSecs);
        PageFactory.initElements(driver, this);
    }


    public void clickOnStartButton() {
        wait.until(ExpectedConditions.visibilityOf(START_BUTTON));
        START_BUTTON.click();
    }
}
