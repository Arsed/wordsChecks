package de.test.automatedTests.productPageTests;

import de.test.automatedTests.homePagePackage.AbstractHomePage;
import de.test.automatedTests.managers.HomePageManager;
import de.test.automatedTests.managers.ProductPageManager;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductFeedbackAcceptanceTest extends AbstractHomePage {

    /**
     * verify if the width of the stars bar is good calculated from rating number
     */
    @Test
    public void verifyProductRating() {

        //search random a category of products
        homePageManager.search(HomePageManager.itemsName[random.nextInt(HomePageManager.itemsName.length)]);
        // search random an product from that category
        homePageManager.selectProduct(random.nextInt(60));


        //if the rating number is visible on the page we will calculate the width and compare
        if (getWebDriver().findElement(By.cssSelector(ProductPageManager.productNumberRatingSelector)) != null) {

            //save the width of the yellow stars bar
            Float widthFloat = Float.parseFloat(getWebDriver().findElement(By.cssSelector(ProductPageManager.productStarsRatingSelector)).getAttribute("style").replaceAll("[^\\d]", ""));

             //save the rating number
            Float rating = Float.parseFloat(getWebDriver().findElement(By.cssSelector(ProductPageManager.productNumberRatingSelector)).getText());
            Assert.assertEquals(widthFloat, rating * 20, "the width of the yellow stars bar is wrong");
        }
    }
}
