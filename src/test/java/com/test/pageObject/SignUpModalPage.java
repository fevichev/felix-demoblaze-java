package com.test.pageObject;

import com.test.utils.GetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SignUpModalPage extends GetPage {

    public final By usernameTextBox = By.cssSelector("#sign-username");
    public final By passwordTextBox = By.cssSelector("#sign-password");
    public final By signUpButtonModal = By.xpath("//button[contains(text(),'Sign up')]");

    public static final String ALERT_MESSAGE = "Sign up successful.";

    public SignUpModalPage(WebDriver driver) {
        super(driver);
    }

    public void populateUsernameAndPassword(String username, String password) {
        sleep(1);
        element(usernameTextBox).clear();
        element(usernameTextBox).sendKeys(username);
        element(passwordTextBox).sendKeys(password);
        element(signUpButtonModal).click();
    }

    public void verifyAlertMessage() {
        assertThat(getTextOfAlert(), equalTo(ALERT_MESSAGE));
    }
}