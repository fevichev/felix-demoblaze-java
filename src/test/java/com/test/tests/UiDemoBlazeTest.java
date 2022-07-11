package com.test.tests;

import com.test.base.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UiDemoBlazeTest extends BaseTest {

//    private final By signUpButton = By.id("signin2");
    private final By usernameTextBox = By.cssSelector("#sign-username");
    private final By passwordTextBox = By.cssSelector("#sign-password");
    private final By signUpButtonModal = By.xpath("//button[contains(text(),'Sign up')]");
    private final By loginTopMenuButton = By.cssSelector("#login2");
    private final By cartTopMenuButoon = By.xpath("//a[contains(text(),'Cart')]");
    private final By loginUsernameTextBox = By.xpath("//input[@id='loginusername']");
    private final By loginPasswordTextBox = By.xpath("//input[@id='loginpassword']");
    private final By loginButoon = By.xpath("//button[contains(text(),'Log in')]");
    private final By nameOfUser = By.xpath("//a[@id='nameofuser']");
    private final By nexus6Link = By.xpath("//a[contains(text(),'Nexus 6')]");
    private final By addToCartButton = By.xpath("//a[contains(text(),'Add to cart')]");
    private final By nextPageButton = By.xpath("//button[@id='next2']");
    private final By previewPageButton = By.xpath("//button[@id='prev2']");
    private final By mainLogoButton = By.xpath("//a[@id='nava']");
    private final By devicePriceContainer = By.xpath("//*[@id='tbodyid']/h3");
    private final By totalPriceText = By.xpath("//h3[@id='totalp']");
    private final By placeOrderButton = By.xpath("//button[contains(text(),'Place Order')]");
    //order
    private final By nameOrderTextBox = By.xpath("//input[@id='name']");
    private final By countryOrderTextBox = By.xpath("//input[@id='country']");
    private final By cityOrderTextBox = By.xpath("//input[@id='city']");
    private final By creditCardOrderTextBox = By.xpath("//input[@id='card']");
    private final By yearOrderTextBox = By.xpath("//input[@id='year']");
    private final By thankYouText = By.xpath("//h2[contains(text(),'Thank you for your purchase!')]");
    private final By orderDescriptionText = By.xpath("//body/div[10]/p[1]");
    private final By okOrderSubmitButton = By.xpath("//button[contains(text(),'OK')]");


    private static final String ALERT_MESSAGE = "Sign up successful.";
    private static final String PRODUCT_ADDED_ALERT_MESSAGE = "Product added.";
    private By deviceWithName(String deviceName) {
        return By.xpath("//a[contains(text(),'" + deviceName + "')]");
    }

    @BeforeTest
    public void setup() {
        initialization();
    }

    @Test
    public void blazeTest() {
        String username = faker.internet().emailAddress();
        String password = faker.internet().password();

        element(generalPageUi.signUpButton).click();
        element(usernameTextBox).sendKeys(username);
        element(passwordTextBox).sendKeys(password);
        element(signUpButtonModal).click();
        assertThat(getTextOfAlert(), equalTo(ALERT_MESSAGE));
        acceptAlertMessage();

        element(loginTopMenuButton).click();
        element(loginUsernameTextBox).sendKeys(username);
        element(loginPasswordTextBox).sendKeys(password);
        element(loginButoon).click();
        assertThat(element(nameOfUser).getText(), equalTo(username));

        addDeviceToCart("Nexus 6");
        addDeviceToCart("MackBook Pro");
        element(cartTopMenuButoon).click();
        assertThat("", equalTo(element(totalPriceText).getText()));

        String name = faker.funnyName().name();
        String country = faker.country().countryCode2();
        String city = faker.address().city();
        String creditCard = faker.finance().creditCard();
        String month = "june";//??
        String year = "2023";//??

    }

    private void addDeviceToCart(String deviceName) {
        try {
            element(deviceWithName(deviceName)).click();

        } catch (Exception e) {
            element(nextPageButton).click();
            element(deviceWithName(deviceName)).click();
        }
        element(addToCartButton).click();
        assertThat(getTextOfAlert(), equalTo(PRODUCT_ADDED_ALERT_MESSAGE));
        acceptAlertMessage();
        element(mainLogoButton).click();
        element(devicePriceContainer).getText();//??
    }

    @AfterTest
    public void teardown() {
        closeBrowser();
    }
}
