package com.test.base;

import com.github.javafaker.Faker;
import com.test.UI.GeneralPageUi;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.with;

public class BaseTest {

    public WebDriver driver;
    public Faker faker;
    String baseUrl;
    public Properties prop;
    public GeneralPageUi generalPageUi;
    public WebDriverWait wait;

    public BaseTest() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialization() {
        String driverName = prop.getProperty("browserName");
        switch (driverName.toLowerCase()) {
            case ("chrome"):
                driver = WebDriverManager.chromedriver().create();
                break;
            case ("safari"):
                driver = WebDriverManager.safaridriver().create();
                break;
            case ("firefox"):
                driver = WebDriverManager.firefoxdriver().create();
                break;
            default:
                throw new RuntimeException("Ooopsy! you probably didn't specify the browser type or made a typo. \n" +
                        "Please use the following types of browsers to save the world: Chrome, Safari or Firefox");
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(4));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        baseUrl = prop.getProperty("url");
        driver.get(baseUrl);
        initObjects();
    }

    public void initObjects() {
        generalPageUi = new GeneralPageUi();
        faker = new Faker();
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void closeBrowser() {
        if (driver != null) {
            try {
                driver.close();
                driver.quit();
            } catch (NoSuchMethodError nsme) {
            } catch (NoSuchSessionException nsse) {
            } catch (SessionNotCreatedException snce) {
                throw new RuntimeException(snce);
            }
        }
    }

    public WebElement element(By ByElement) {
        WebElement e = driver.findElement(ByElement);
        with().pollDelay(100, MILLISECONDS).await().atMost
                (5, SECONDS).until(e::isDisplayed);
        return driver.findElement(ByElement);
    }

    public List<WebElement> elements(By ByElement) {
        WebElement e = driver.findElement(ByElement);
        with().pollDelay(100, MILLISECONDS).await().atMost
                (5, SECONDS).until(e::isDisplayed);
        return driver.findElements(ByElement);
    }

    public static void waitVisibleElement(WebElement element, int seconds) {
        with().pollDelay(100, MILLISECONDS).await().atMost
                (seconds, SECONDS).until(element::isDisplayed);
    }

    public String getTextOfAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }

    public void acceptAlertMessage() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }
}
