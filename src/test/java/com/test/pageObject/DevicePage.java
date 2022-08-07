package com.test.pageObject;

import com.test.utils.GetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DevicePage extends GetPage {

    public final By addToCartButton = By.xpath("//a[contains(text(),'Add to cart')]");
    public final By devicePriceContainer = By.xpath("//*[@id='tbodyid']/h3");

    public static final String PRODUCT_ADDED_ALERT_MESSAGE = "Product added.";

    public DevicePage(WebDriver driver) {
        super(driver);
    }

    public void moveDeviceToCart() {
        int price = Integer.parseInt(element(devicePriceContainer).getText().split(" ")[0].
                replace("$", ""));
        priceValues.add(price);
        element(addToCartButton).click();
    }

    public void verifyDeviceWasAddedToCart() {
        assertThat(getTextOfAlert(), equalTo(PRODUCT_ADDED_ALERT_MESSAGE));
    }
}
