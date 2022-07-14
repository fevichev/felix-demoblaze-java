package com.test.tests;

import com.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UiDemoBlazeTest extends BaseTest {

    @BeforeClass
    public void setup() {
        initialization();
    }

    @Test(priority = 1, testName = "Create new user verification", groups = "UI")
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

    @Test(priority = 2, testName = "Login to the system verification", groups = "UI")
    public void loginTest() {
        homePage.clickLoginTopMenuButton();
        loginModalPage.loginWithCredentials(String.valueOf(session.get("username")),
                String.valueOf(session.get("password")));
        homePage.verifyUserIsLogedIn();
    }

    @Test(priority = 3, testName = "Add to cart 2 devices verification", groups = "UI")
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

    @Test(priority = 4, testName = "Move to Cart verification", groups = "UI")
    public void cartTest() {
        homePage.clickCartTopMenu();
        cartPage.verifyCartHasSelectedDevices();
        cartPage.verifyTotalPrice();
    }

    @Test(priority = 5, testName = "Place an order verification", groups = "UI")
    public void placeOrderTest() {
        cartPage.clickPlaceOrderButton();
        placeOrderModalPage.populateOrderFields();
        placeOrderModalPage.clickPurchaseButton();
        placeOrderModalPage.verifyOrderIsSubmitted();
        placeOrderModalPage.verifySubmittedFinalPrice();
        placeOrderModalPage.clickOkOnSubmissionModal();
    }

    @Test(priority = 6, testName = "Login API test", groups = "API")
    public void loginApiTest() {
        homePage.clickTopLogo();
        homePage.clickLoginTopMenuButton();
        loginModalPage.loginWithCredentials(String.valueOf(session.get("username")),
                String.valueOf(session.get("password")));
        homePage.verifyUserIsLogedIn();
    }

    @Test(priority = 7, testName = "Add device for API verification", groups = "API")
    public void addDevice() {
        homePage.selectDeviceFromList("Nexus 6");
        devicePage.moveDeviceToCart();
        devicePage.verifyDevicewasAddedtoCart();
        helper.acceptAlertMessage();
        homePage.clickTopLogo();
    }

    @Test(priority = 8, testName = "Validate cart with API", groups = "API")
    public void validateCartWithApi() {
        homePage.clickCartTopMenu();
        cartPage.verifyNumberOfItemsInTheCard(1);
        cartPage.verifyPriceOfTheSelectedPhone(650);
        cartPage.verifyTitleOfTheSelectedPhone("Nexus 6");
        cartPage.verifyItemId(3);
    }

    @AfterClass
    public void teardown() {
        closeBrowser();
    }

    @AfterGroups(groups = {"UI"})
    public void cleanUp() {
        cartPage.deleteCartApi();
        homePage.clickLogOut();
    }
}