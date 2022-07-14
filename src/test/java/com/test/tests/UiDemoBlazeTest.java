package com.test.tests;

import com.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UiDemoBlazeTest extends BaseTest {

    @BeforeClass
    public void setup() {
        initialization();
    }

    @Test(priority = 1, testName = "Create new user verification")
    public void signUpTest() {
        String username = faker.internet().emailAddress();
        String password = faker.internet().password();
        session.put("username", username);
        session.put("password", password);

        homePage.clickSignUpButton();
        signUpModalPage.populateUsernameAndPassword(username, password);
        signUpModalPage.verifyAlertMessage();
        helper.acceptAlertMessage();
    }

    @Test(priority = 2, testName = "Login to the system verification")
    public void loginTest() {
        homePage.clickLoginTopMenuButton();
        loginModalPage.loginWithCredentials(String.valueOf(session.get("username")),
                String.valueOf(session.get("password")));
        homePage.verifyUserIsLogedIn();
    }

    @Test(priority = 3, testName = "Add to cart 2 devices verification")
    public void addToCartTest() {
        homePage.selectDeviceFromList("Nexus 6");
        devicePage.moveDeviceToCart();
        devicePage.verifyDevicewasAddedtoCart();
        helper.acceptAlertMessage();
        homePage.clickTopLogo();
        homePage.selectDeviceFromList("MacBook Pro");
        devicePage.moveDeviceToCart();
        devicePage.verifyDevicewasAddedtoCart();
        helper.acceptAlertMessage();
        homePage.clickTopLogo();
    }

    @Test(priority = 4, testName = "Move to Cart verification")
    public void cartTest() {
        homePage.clickCartTopMenu();
        cartPage.verifyCartHasSelectedDevices();
        cartPage.verifyTotalPrice();
    }

    @Test(priority = 5, testName = "Place an order verification")
    public void placeOrderTest() {
        cartPage.clickPlaceOrderButton();
        placeOrderModalPage.populateOrderFields();
        placeOrderModalPage.clickPurchaseButton();
        placeOrderModalPage.verifyOrderIsSubmitted();
        placeOrderModalPage.verifySubmittedFinalPrice();
    }

    @AfterClass
    public void teardown() {
        closeBrowser();
    }
}