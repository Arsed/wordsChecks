package de.test.automatedTests.cartPagePackage;

import de.test.automatedTests.homePagePackage.AbstractHomePage;
import de.test.automatedTests.managers.ApplicationManager;
import de.test.automatedTests.managers.CartPageManager;
import de.test.automatedTests.managers.HomePageManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.List;

public class CartPageAcceptanceTest extends AbstractHomePage {

    @Test
    public void verifyTotalPriceForAnElement() {


        //search "tableta"
        homePageManager.search(HomePageManager.itemsName[random.nextInt(HomePageManager.itemsName.length)]);

        //add one element random
        while (!homePageManager.addToCartItem(random.nextInt(60))) ;

        homePageManager.clickOnCartIcon();
        //verify the maxim number of items

        cartPageManager.verifyTotalPrice(50, softAssert);

        //verify with random value
        for (int i = 0; i < random.nextInt(20) + 2; i++) {
            cartPageManager.verifyTotalPrice(random.nextInt(50) + 1, softAssert);

        }
    }


    @Test
    public void verifyTotalPrice() {

        //adding radon a number of products
        for (int i = 0; i < random.nextInt(10) + 1; i++) {
            homePageManager.search(HomePageManager.itemsName[random.nextInt(HomePageManager.itemsName.length)]);

            for (int j = 0; j < random.nextInt(10) + 1; j++)
                while (!homePageManager.addToCartItem(random.nextInt(60))) ;
        }

        //go to cart page
        homePageManager.clickOnCartIcon();
        new WebDriverWait(getWebDriver(), ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CartPageManager.titleOfCartPage)));

        List<WebElement> elementsList = getWebDriver().findElements(By.cssSelector(CartPageManager.itemContainerSelector));
        float totalPrice = Float.parseFloat(getWebDriver().findElement(By.cssSelector(CartPageManager.summarComandaSelector)).getText().replaceAll("[^\\d]", "")) / 100;
        float totalPriceSecond = 0;


        for (WebElement element : elementsList) {
            totalPriceSecond += Float.parseFloat(element.findElement(By.cssSelector(CartPageManager.priceOfItemSelector)).getText().replaceAll("[^\\d]", "")) / 100;
        }


        Float totalPriceWithDelivery = Float.parseFloat(getWebDriver().findElement(By.cssSelector(CartPageManager.totalPriceSelector)).getText().replaceAll("[^\\d]", "")) / 100;
        ;
        Float deliveryPrice = Float.parseFloat(getWebDriver().findElement(By.cssSelector(CartPageManager.deliveryCost)).getText().replaceAll("[^\\d]", "")) / 100;

        cartPageManager.assertPrice(totalPriceSecond, totalPrice, softAssert);
        cartPageManager.assertPrice(deliveryPrice + totalPrice, totalPriceWithDelivery, softAssert);

    }

}