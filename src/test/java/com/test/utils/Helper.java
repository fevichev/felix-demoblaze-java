package com.test.utils;

import com.test.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.with;

public class Helper extends BaseTest {

    public static WebElement element(By ByElement) {
        WebElement e = driver.findElement(ByElement);
        with().pollDelay(100, MILLISECONDS).await().atMost
                (5, SECONDS).until(e::isDisplayed);
        return driver.findElement(ByElement);
    }

    public static List<WebElement> elements(By ByElement) {
        WebElement e = driver.findElement(ByElement);
        with().pollDelay(100, MILLISECONDS).await().atMost
                (5, SECONDS).until(e::isDisplayed);
        return driver.findElements(ByElement);
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
