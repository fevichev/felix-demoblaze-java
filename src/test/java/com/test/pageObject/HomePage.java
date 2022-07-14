package com.test.pageObject;

import com.test.base.BaseTest;
import org.openqa.selenium.By;

import static com.test.utils.Helper.element;
import static com.test.utils.Helper.sleep;
import static org.awaitility.Awaitility.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;

public class HomePage extends BaseTest {

    public final By signUpButton = By.id("signin2");
    public final By loginTopMenuButton = By.cssSelector("#login2");
    public final By nameOfUser = By.xpath("//a[@id='nameofuser']");
    public final By nextPageButton = By.xpath("//button[@id='next2']");
    public final By mainLogoButton = By.xpath("//a[@id='nava']");
    public final By cartTopMenuButton = By.xpath("//a[contains(text(),'Cart')]");

    public By deviceWithName(String deviceName) {
        return By.xpath("//a[contains(text(),'" + deviceName + "')]");
    }

    public void clickSignUpButton() {
        element(signUpButton).click();
    }

    public void clickLoginTopMenuButton() {
        element(loginTopMenuButton).click();
    }

    public void verifyUserIsLogedIn() {
        given().ignoreExceptions().await().until(() -> element(nameOfUser).isDisplayed());
        assertThat(element(nameOfUser).getText(), endsWith(String.valueOf(session.get("username"))));
    }

    public void selectDeviceFromList(String deviceName) {
        try {
            element(deviceWithName(deviceName)).click();

        } catch (Exception e) {
            element(nextPageButton).click();
            element(deviceWithName(deviceName)).click();
        }
    }

    public void clickTopLogo() {
        element(mainLogoButton).click();
    }

    public void clickCartTopMenu() {
        sleep(1);
        element(cartTopMenuButton).click();
    }
}