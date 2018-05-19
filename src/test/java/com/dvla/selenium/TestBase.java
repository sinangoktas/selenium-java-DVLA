package com.dvla.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {

    private String OS = System.getProperty("os.name").toLowerCase();


    public WebDriver createDriver() {

        if (OS.indexOf("win") >= 0) {

            System.setProperty("webdriver.chrome.driver", "utilities/win/chromedriver");

        } else if (OS.indexOf("mac") >= 0) {

            System.setProperty("webdriver.chrome.driver", "utilities/mac/chromedriver");

        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {

            System.setProperty("webdriver.chrome.driver", "utilities/linux/chromedriver");

        }

        return new ChromeDriver();
    }

}
