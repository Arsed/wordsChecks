package de.test.automatedTests.homePagePackage;

import de.test.automatedTests.managers.ApplicationManager;
import de.test.automatedTests.managers.CartPageManager;
import de.test.automatedTests.managers.HomePageManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.List;

public class CompareOptionsAcceptanceTest extends AbstractHomePage {

    @Test
    public void verifyOneItemInCompareBox() {
        int index = random.nextInt(60);

        homePageManager.search("telefon");

        List<WebElement> pageItems = getWebDriver().findElements(By.cssSelector(HomePageManager.toCompareCheckBox));
        pageItems.get(index).click();

        homePageManager.verifyImageOfElement(index);

        //click on compare button
        getWebDriver().findElement(By.cssSelector(HomePageManager.compareButton)).click();

        //verify alert message
        homePageManager.verifyCompareAlert("Va rugam sa selectati cel putin 2 produse");
    }


    /**
     * try to add more that 4 elements in compare box and verify if if show the alert message
     */
    @Test
    public void verifyMaxNumberOfItemsInCart() {

        //search after "telefon"
        homePageManager.search("telefon");

        //try to add random 5 elements in compare box
        homePageManager.randomSelectProductsForCompare(5);

        //verify if the alert box is displayed
        homePageManager.verifyCompareAlert("Poti compara maximum 4 produse!");
    }


    /***
     * add a random number [2,4] of elements in compare and after verify the cart
     * */
    @Test
    public void verifyCompare() {

        //search after "telefon"
        homePageManager.search(HomePageManager.itemsName[random.nextInt(HomePageManager.itemsName.length)]);

        int number = random.nextInt(5) + 2;
        List<HomePageManager.Product> productAdded = homePageManager.randomSelectProductsForCompare(number);

        //click on compare button
        getWebDriver().findElement(By.cssSelector(HomePageManager.compareButton)).click();

        //verify if the product from the compare page is the same with those selected by us
        new WebDriverWait(getWebDriver(), ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CartPageManager.titleOfComparePage)));
        cartPageManager.verifyItemsFromComparePage(productAdded);

    }

}
