package com.test.pageObject;

import com.test.rest.DeleteCartAPI;
import com.test.rest.LoginApi;
import com.test.rest.ViewCartApi;
import com.test.rest.ViewDeviceDescriptionApi;
import com.test.utils.GetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;

public class CartPage extends GetPage {

    public final By totalPriceText = By.xpath("//h3[@id='totalp']");
    public final By allDevicesInCart = By.xpath("//tbody/tr");
    public final By placeOrderButton = By.xpath("//button[contains(text(),'Place Order')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCartHasSelectedDevices() {
        sleep(1);
        assertThat(getExpectedTotalPrice(priceValues), equalTo(Integer.parseInt(element(totalPriceText).getText())));
        assertThat(elements(allDevicesInCart).size(), comparesEqualTo(2));
    }

    public void verifyTotalPrice() {
        Map<String, Integer> expectedValueInCart = Map.of("Nexus 6", 650, "MacBook Pro", 1100);
        Map<String, Integer> actualValuesInCart = new HashMap<>();
        for (WebElement row : elements(allDevicesInCart)) {
            String deviceNameInCart = row.findElements(By.tagName("td")).get(1).getText();
            int devicePriceInCart = Integer.parseInt(row.findElements(By.tagName("td")).get(2).getText());
            actualValuesInCart.put(deviceNameInCart, devicePriceInCart);
        }
        assertThat(expectedValueInCart.entrySet(), everyItem(is(in(actualValuesInCart.entrySet()))));
    }

    public void clickPlaceOrderButton() {
        element(placeOrderButton).click();
    }

    public void verifyNumberOfItemsInTheCard(int expectedItemsNumber) {
        int numberOfItemsInCart = ViewCartApi.withToken(getUserTokenApi()).getSizeOfItemsInCart();
        assertThat(numberOfItemsInCart, equalTo(expectedItemsNumber));
    }

    public void verifyPriceOfTheSelectedPhone(int expectedItemPrice) {
        int price = ViewDeviceDescriptionApi.withDeviceId(getProductIdInCart()).getPrice();
        assertThat(price, equalTo(expectedItemPrice));
    }

    public void verifyTitleOfTheSelectedPhone(String expectedPhoneTitle) {
        String deviceName = ViewDeviceDescriptionApi.withDeviceId(getProductIdInCart()).getTitle();
        assertThat(deviceName, equalTo(expectedPhoneTitle));
    }

    public void verifyItemId(int expectedId) {
        assertThat(getProductIdInCart(), equalTo(expectedId));
    }

    public void deleteCartApi() {
        DeleteCartAPI.withCredentials(String.valueOf(session.get("username")));
    }

    private int getExpectedTotalPrice(List<Integer> prices) {
        return prices.stream().mapToInt(Integer::intValue).sum();
    }

    private String getUserTokenApi() {
        return LoginApi.withCredentials(String.valueOf(session.get("username")),
                String.valueOf(session.get("password"))).getAuth_token();
    }

    private int getProductIdInCart() {
        return ViewCartApi.withToken(getUserTokenApi()).getProductId();
    }
}
