package com.test.pageObject;

import com.test.base.BaseTest;
import org.openqa.selenium.By;

import static com.test.utils.Helper.element;

public class LoginModalPage extends BaseTest {

    public final By loginUsernameTextBox = By.xpath("//input[@id='loginusername']");
    public final By loginPasswordTextBox = By.xpath("//input[@id='loginpassword']");
    public final By loginButton = By.xpath("//button[contains(text(),'Log in')]");

    public void loginWithCredentials(String username, String password) {
        element(loginUsernameTextBox).sendKeys(username);
        element(loginPasswordTextBox).sendKeys(password);
        element(loginButton).click();
    }
}
