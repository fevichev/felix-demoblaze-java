package com.test.utils;

import com.github.javafaker.Faker;
import com.test.base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GetPage extends BaseTest {

    public WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    public Faker faker = new Faker();

    public GetPage(WebDriver driver) {
        super(driver);
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void acceptAlertMessage() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public String getTextOfAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }
}
