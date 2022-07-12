package com.test.tests;

import com.test.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertTrue;

public class UiDemoBlazeTest extends BaseTest {

    //    private final By signUpButton = By.id("signin2");
    private final By usernameTextBox = By.cssSelector("#sign-username");
    private final By passwordTextBox = By.cssSelector("#sign-password");
    private final By signUpButtonModal = By.xpath("//button[contains(text(),'Sign up')]");
    private final By loginTopMenuButton = By.cssSelector("#login2");
    private final By cartTopMenuButoon = By.xpath("//a[contains(text(),'Cart')]");
    private final By loginUsernameTextBox = By.xpath("//input[@id='loginusername']");
    private final By loginPasswordTextBox = By.xpath("//input[@id='loginpassword']");
    private final By loginButton = By.xpath("//button[contains(text(),'Log in')]");
    private final By nameOfUser = By.xpath("//a[@id='nameofuser']");
    private final By addToCartButton = By.xpath("//a[contains(text(),'Add to cart')]");
    private final By nextPageButton = By.xpath("//button[@id='next2']");
    private final By mainLogoButton = By.xpath("//a[@id='nava']");
    private final By devicePriceContainer = By.xpath("//*[@id='tbodyid']/h3");
    private final By totalPriceText = By.xpath("//h3[@id='totalp']");
    private final By placeOrderButton = By.xpath("//button[contains(text(),'Place Order')]");
    //order
    private final By nameOrderTextBox = By.xpath("//input[@id='name']");
    private final By countryOrderTextBox = By.xpath("//input[@id='country']");
    private final By cityOrderTextBox = By.xpath("//input[@id='city']");
    private final By creditCardOrderTextBox = By.xpath("//input[@id='card']");
    private final By monthOrderTextBox = By.xpath("//input[@id='month']");
    private final By yearOrderTextBox = By.xpath("//input[@id='year']");
    private final By purchaseButton = By.xpath("//button[contains(text(),'Purchase')]");
    private final By thankYouText = By.xpath("//h2[contains(text(),'Thank you for your purchase!')]");
    private final By orderDescriptionText = By.xpath("//body/div[10]/p[1]");
    private final By okOrderSubmitButton = By.xpath("//button[contains(text(),'OK')]");
    private final By allDevicesInCart = By.xpath("//tbody/tr");
    private static final String ALERT_MESSAGE = "Sign up successful.";
    private static final String PRODUCT_ADDED_ALERT_MESSAGE = "Product added.";

    private By deviceWithName(String deviceName) {
        return By.xpath("//a[contains(text(),'" + deviceName + "')]");
    }

    private List<Integer> priceValues = new ArrayList<>();
    String username = faker.internet().emailAddress();
    String password = faker.internet().password();

    @BeforeTest
    public void setup() {
        initialization();
    }

    @Test
    public void signUpTest() {
        element(generalPageUi.signUpButton).click();
        element(usernameTextBox).clear();
        element(usernameTextBox).sendKeys(username);
        element(passwordTextBox).sendKeys(password);
        element(signUpButtonModal).click();
        assertThat(getTextOfAlert(), equalTo(ALERT_MESSAGE));
        acceptAlertMessage();
    }

    @Test
    public void loginTest() {
        element(loginTopMenuButton).click();
        element(loginUsernameTextBox).sendKeys(username);
        element(loginPasswordTextBox).sendKeys(password);
        element(loginButton).click();

        given().ignoreExceptions().await().until(() -> element(nameOfUser).isDisplayed());
        assertThat(element(nameOfUser).getText(), endsWith(username));
    }

    @Test
    public void addToCartTest() {
        addDeviceToCart("Nexus 6");
        addDeviceToCart("MacBook Pro");
        element(cartTopMenuButoon).click();

        assertThat(getExpectedTotalPrice(priceValues), equalTo(Integer.parseInt(element(totalPriceText).getText())));
        assertThat(elements(allDevicesInCart).size(), comparesEqualTo(2));
    }

    @Test
    public void cartTest() {
        Map<String, Integer> expectedValueInCart = Map.of("Nexus 6", 650, "MacBook Pro", 1100);
        Map<String, Integer> actualValuesInCart = new HashMap<>();
        for (WebElement row : elements(allDevicesInCart)) {
            String deviceNameInCart = row.findElements(By.tagName("td")).get(1).getText();
            int devicePriceInCart = Integer.parseInt(row.findElements(By.tagName("td")).get(2).getText());
            actualValuesInCart.put(deviceNameInCart, devicePriceInCart);
        }
        assertThat(expectedValueInCart.entrySet(), everyItem(is(in(actualValuesInCart.entrySet()))));
    }

    @Test
    public void placeOrderTest() {
        String name = faker.funnyName().name();
        String country = faker.country().countryCode2();
        String city = faker.address().city();
        String creditCard = faker.finance().creditCard();
        String month = faker.rickAndMorty().character();
        String year = "2022";

        element(placeOrderButton).click();
        element(nameOrderTextBox).sendKeys(name);
        element(countryOrderTextBox).sendKeys(country);
        element(cityOrderTextBox).sendKeys(city);
        element(creditCardOrderTextBox).sendKeys(creditCard);
        element(monthOrderTextBox).sendKeys(month);
        element(yearOrderTextBox).sendKeys(year);
        element(purchaseButton).click();

        assertTrue(element(thankYouText).isDisplayed());
        assertTrue(element(okOrderSubmitButton).isDisplayed());

        int summaryTotalPrice = Integer.parseInt(element(orderDescriptionText).getText().split("\n")[1].
                split(" ")[1]);
        assertThat(getExpectedTotalPrice(priceValues), equalTo(summaryTotalPrice));
    }

    private void addDeviceToCart(String deviceName) {
        try {
            element(deviceWithName(deviceName)).click();

        } catch (Exception e) {
            element(nextPageButton).click();
            element(deviceWithName(deviceName)).click();
        }
        int price = Integer.parseInt(element(devicePriceContainer).getText().split(" ")[0].
                replace("$", ""));
        priceValues.add(price);
        element(addToCartButton).click();
        assertThat(getTextOfAlert(), equalTo(PRODUCT_ADDED_ALERT_MESSAGE));
        acceptAlertMessage();
        element(mainLogoButton).click();
    }

    private int getExpectedTotalPrice(List<Integer> prices) {
        return prices.stream().mapToInt(Integer::intValue).sum();
    }

    @AfterTest
    public void teardown() {
        closeBrowser();
    }
}
