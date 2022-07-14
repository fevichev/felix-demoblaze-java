package com.test.pageObject;

import com.test.base.BaseTest;
import org.openqa.selenium.By;

import java.util.List;

import static com.test.utils.Helper.element;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class PlaceOrderModalPage extends BaseTest {

    public final By nameOrderTextBox = By.xpath("//input[@id='name']");
    public final By countryOrderTextBox = By.xpath("//input[@id='country']");
    public final By cityOrderTextBox = By.xpath("//input[@id='city']");
    public final By creditCardOrderTextBox = By.xpath("//input[@id='card']");
    public final By monthOrderTextBox = By.xpath("//input[@id='month']");
    public final By yearOrderTextBox = By.xpath("//input[@id='year']");
    public final By purchaseButton = By.xpath("//button[contains(text(),'Purchase')]");
    public final By thankYouText = By.xpath("//h2[contains(text(),'Thank you for your purchase!')]");
    public final By okOrderSubmitButton = By.xpath("//button[contains(text(),'OK')]");
    public final By orderDescriptionText = By.xpath("//body/div[10]/p[1]");

    public void populateOrderFields() {
        String name = faker.funnyName().name();
        String country = faker.country().countryCode2();
        String city = faker.address().city();
        String creditCard = faker.finance().creditCard();
        String month = faker.rickAndMorty().character();
        String year = faker.number().digits(4);

        element(nameOrderTextBox).sendKeys(name);
        element(countryOrderTextBox).sendKeys(country);
        element(cityOrderTextBox).sendKeys(city);
        element(creditCardOrderTextBox).sendKeys(creditCard);
        element(monthOrderTextBox).sendKeys(month);
        element(yearOrderTextBox).sendKeys(year);
    }

    public void clickPurchaseButton() {
        element(purchaseButton).click();
    }

    public void verifyOrderIsSubmitted() {
        assertTrue(element(thankYouText).isDisplayed());
        assertTrue(element(okOrderSubmitButton).isDisplayed());
    }

    public void verifySubmittedFinalPrice() {
        int summaryTotalPrice = Integer.parseInt(element(orderDescriptionText).getText().split("\n")[1].
                split(" ")[1]);
        assertThat(getExpectedTotalPrice(priceValues), equalTo(summaryTotalPrice));
    }

    public void clickOkOnSubmissionModal() {
        element(okOrderSubmitButton).click();
    }

    private int getExpectedTotalPrice(List<Integer> prices) {
        return prices.stream().mapToInt(Integer::intValue).sum();
    }
}
