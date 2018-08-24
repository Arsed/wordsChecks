package de.test.automatedTests.managers;

import org.openqa.selenium.WebDriver;

public class ProductPageManager  {

    WebDriver driver;

    public static String productStarsRatingSelector=".row div div a .star-rating-inner";
    public static String productNumberRatingSelector=".row div div a .star-rating-text";


    public ProductPageManager(WebDriver webDriver) {
        driver=webDriver;
    }
}
