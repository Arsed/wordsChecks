package de.test.automatedTests.homePagePackage;

import com.sdl.selenium.web.utils.Utils;
import de.test.automatedTests.managers.ApplicationManager;
import de.test.automatedTests.managers.CartPageManager;
import de.test.automatedTests.managers.HomePageManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class AddElementsToCartAcceptanceTests extends AbstractHomePage {

    private static final Logger logger = LoggerFactory.getLogger(AddElementsToCartAcceptanceTests.class);

    /**
     * check cheapest items in cart
     */

    @Test
    public void verifyTheSearchInput() {
        int numberOfElements = random.nextInt(15)+1;

        //search
        homePageManager.search(HomePageManager.itemsName[random.nextInt(HomePageManager.itemsName.length)]);

        // search on page and find the cheap  items
        List<HomePageManager.Product> cheapElements = homePageManager.lowPrices(numberOfElements);

        int itemsNumber=homePageManager.addToCart(cheapElements);

        //verify the total number of the products from cart
        WebElement numberOfItemsFromCartElement = getWebDriver().findElement(By.cssSelector(HomePageManager.numberOfItemsFromCart));
        Assert.assertEquals(Integer.parseInt(numberOfItemsFromCartElement.getText()), itemsNumber);

        //verify the prices and the title of the items from cart
        homePageManager.verifyCartItems(cheapElements);
    }



}
