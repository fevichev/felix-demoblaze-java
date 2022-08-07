package com.test.base;

import com.github.javafaker.Faker;
import com.test.pageObject.CartPage;
import com.test.pageObject.DevicePage;
import com.test.pageObject.HomePage;
import com.test.pageObject.LoginModalPage;
import com.test.pageObject.PlaceOrderModalPage;
import com.test.pageObject.SignUpModalPage;
import com.test.utils.GetPage;
import com.test.utils.ReportingHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BaseTest {

    public WebDriver driver;
    public Faker faker;
    public static Properties prop;
    public SignUpModalPage signUpModalPage;
    public LoginModalPage loginModalPage;
    public HomePage homePage;
    public DevicePage devicePage;
    public CartPage cartPage;
    public PlaceOrderModalPage placeOrderModalPage;
    public String baseUrl;
    public GetPage getPage;
    public ReportingHelper reportingHelper;

    public static Map<String, Object> session = new HashMap<>();
    public static List<Integer> priceValues = new ArrayList<>();

    public BaseTest(WebDriver driver) {
        this.driver = driver;
    }

    public BaseTest() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/config.properties");
            prop.load(ip);

            for (String propertyName : prop.stringPropertyNames()) {
                String systemPropertyValue = System.getProperty(propertyName);

                if (systemPropertyValue != null) {
                    prop.setProperty(propertyName, systemPropertyValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialization() {
        String driverName = prop.getProperty("browser.name");
        String isHeadless = prop.getProperty("browser.headless");
        switch (driverName.toLowerCase()) {
            case ("chrome"):
                if (isHeadless.matches("true")){
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("disable-gpu");
                    driver = new ChromeDriver(chromeOptions);
                }
                else {
                    driver = WebDriverManager.chromedriver().create();
                }
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

        baseUrl = prop.getProperty("base.url");
        driver.get(baseUrl);
        initObjects();
    }

    public void initObjects() {
        faker = new Faker();
        signUpModalPage = new SignUpModalPage(driver);
        loginModalPage = new LoginModalPage(driver);
        homePage = new HomePage(driver);
        devicePage = new DevicePage(driver);
        cartPage = new CartPage(driver);
        placeOrderModalPage = new PlaceOrderModalPage(driver);
        getPage = new GetPage(driver);
        reportingHelper = new ReportingHelper(driver);
    }

    public void closeBrowser() {
        if (driver != null) {
            try {
                driver.close();
                driver.quit();
            } catch (NoSuchMethodError | NoSuchSessionException | SessionNotCreatedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public WebElement element(By ByElement) {
        return driver.findElement(ByElement);
    }

    public List<WebElement> elements(By ByElement) {
        return driver.findElements(ByElement);
    }

    protected static void logMessage(String message) {
        Reporter.log(message, true);
    }
}
