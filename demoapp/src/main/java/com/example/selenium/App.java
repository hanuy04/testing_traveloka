package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class App {
    private WebDriver driver;

    public App() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    public void openURL(String url) {
        driver.manage().window().maximize();
        driver.get(url);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
