package com.test.pageObject;

import com.test.base.BaseTest;
import org.openqa.selenium.By;

import static com.test.utils.Helper.element;
import static com.test.utils.Helper.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SignUpModalPage extends BaseTest {

    public final By usernameTextBox = By.cssSelector("#sign-username");
    public final By passwordTextBox = By.cssSelector("#sign-password");
    public final By signUpButtonModal = By.xpath("//button[contains(text(),'Sign up')]");

    public static final String ALERT_MESSAGE = "Sign up successful.";

    public void populateUsernameAndPassword(String username, String password) {
        sleep(1);
        element(usernameTextBox).clear();
        element(usernameTextBox).sendKeys(username);
        element(passwordTextBox).sendKeys(password);
        element(signUpButtonModal).click();
    }

    public void verifyAlertMessage() {
        assertThat(helper.getTextOfAlert(), equalTo(ALERT_MESSAGE));
    }
}