package com.test.base;

import com.github.javafaker.Faker;
import com.test.pageObject.CartPage;
import com.test.pageObject.DevicePage;
import com.test.pageObject.HomePage;
import com.test.pageObject.LoginModalPage;
import com.test.pageObject.PlaceOrderModalPage;
import com.test.pageObject.SignUpModalPage;
import com.test.utils.Helper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BaseTest {

    public static WebDriver driver;
    public static Faker faker;
    public Properties prop;
    public static SignUpModalPage signUpModalPage;
    public static LoginModalPage loginModalPage;
    public static HomePage homePage;
    public static DevicePage devicePage;
    public static CartPage cartPage;
    public static PlaceOrderModalPage placeOrderModalPage;
    public static WebDriverWait wait;
    public String baseUrl;
    public static Helper helper;

    public static Map<String, Object> session = new HashMap<>();
    public static List<Integer> priceValues = new ArrayList<>();

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
        faker = new Faker();
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        signUpModalPage = new SignUpModalPage();
        loginModalPage = new LoginModalPage();
        homePage = new HomePage();
        devicePage = new DevicePage();
        cartPage = new CartPage();
        placeOrderModalPage = new PlaceOrderModalPage();
        helper = new Helper();
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

}
