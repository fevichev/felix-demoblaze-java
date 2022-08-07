package com.test.pageObject;

import com.test.utils.GetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginModalPage extends GetPage {

    public final By loginUsernameTextBox = By.xpath("//input[@id='loginusername']");
    public final By loginPasswordTextBox = By.xpath("//input[@id='loginpassword']");
    public final By loginButton = By.xpath("//button[contains(text(),'Log in')]");

    public LoginModalPage(WebDriver driver) {
        super(driver);
    }

    public void loginWithCredentials(String username, String password) {
        element(loginUsernameTextBox).sendKeys(username);
        element(loginPasswordTextBox).sendKeys(password);
        element(loginButton).click();
    }
}
