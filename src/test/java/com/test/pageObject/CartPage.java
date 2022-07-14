package com.test.pageObject;

import com.test.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.test.utils.Helper.element;
import static com.test.utils.Helper.elements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;

public class CartPage extends BaseTest {

    public final By totalPriceText = By.xpath("//h3[@id='totalp']");
    public final By allDevicesInCart = By.xpath("//tbody/tr");
    public final By placeOrderButton = By.xpath("//button[contains(text(),'Place Order')]");

    public void verifyCartHasSelectedDevices() {
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

    private int getExpectedTotalPrice(List<Integer> prices) {
        return prices.stream().mapToInt(Integer::intValue).sum();
    }
}
