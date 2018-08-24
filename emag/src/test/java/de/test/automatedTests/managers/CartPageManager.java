package de.test.automatedTests.managers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class CartPageManager {

    WebDriver driver;
    public static String cartPageTitle = "#cartProductsPage h1.cart";
    public static String itemContainerSelector = ".line-item.main-product";
    public static String priceOfItemSelector = ".price-main";
    public static String titleOfTheItems = ".line-item-title.main-product-title";
    public static String productPriceSelector = ".product-pricing-wrapper .product-new-price";
    public static String productTitleSelector = ".table-compare-column .product-specs-col .clearfix .product-title";
    public static String titleOfComparePage = ".page-header h1";
    public static String totalPriceSelector = "p.price.order-summary-total-price";
    public static String selectNumberSelector = ".gui-input.select2.product-quantity-select.select2-hidden-accessible";
    public static String loadingImageAtSelect = ".loader-overlay .ajax-loader img";
    public static String loadingImageAtTotal = ".cart-widget.order-summary-widget.cart-widget-side.emg-right .loader-overlay .ajax-loader";
    public static String loadingimageAtVoucher = "#vouchersWidget .loader-overlay";
    public static String titleOfCartPage=".cart";

    //sumar comanda
    public static String summarComandaSelector = "span.emg-right.order-summary-items-price";
    public static String deliveryCost=".emg-right.order-summary-shipping-cost";

    public CartPageManager(WebDriver driver) {
        this.driver = driver;
    }


    public void verifyItemsFromComparePage(List<HomePageManager.Product> productAdded) {
        List<WebElement> productsPricesList = driver.findElements(By.cssSelector(productPriceSelector));
        List<WebElement> productsTitlesList = driver.findElements(By.cssSelector(productTitleSelector));

        for (int i = 0; i < productsPricesList.size(); i++) {
            Assert.assertEquals(productAdded.get(i).getTitle(), productsTitlesList.get(i).getText(), "the title is wrong for items");

            Float price = Float.parseFloat(productsPricesList.get(i).getText().replaceAll("[^\\d]", "")) / 100;
            Assert.assertEquals(productAdded.get(i).getPrice(), price, "the title is wrong for items");

        }
    }

    public void verifyTotalPrice(int numberOfElements, SoftAssert softAssert) {
        Select selectNumber = new Select(driver.findElement(By.cssSelector(selectNumberSelector)));


        if (!selectNumber.getFirstSelectedOption().getText().equals("1")) {
            selectNumber.selectByValue("1");
            waitLoadingChangesInCartPage();
        }

        float priceForElement = Float.parseFloat(driver.findElement(By.cssSelector(priceOfItemSelector)).getText().replaceAll("[^\\d]", "")) / 100;
        selectNumber = new Select(driver.findElement(By.cssSelector(selectNumberSelector)));
        selectNumber.selectByValue(String.valueOf(numberOfElements));
        waitLoadingChangesInCartPage();

        float totalPrice = Float.parseFloat(driver.findElement(By.cssSelector(priceOfItemSelector)).getText().replaceAll("[^\\d]", "")) / 100;
        float summarComanda = Float.parseFloat(driver.findElement(By.cssSelector(summarComandaSelector)).getText().replaceAll("[^\\d]", "")) / 100;
        float totalPriceCalculate = (float) (Math.floor(priceForElement * numberOfElements * 1e2) / 1e2);

        assertPrice(totalPrice, totalPriceCalculate, softAssert);

        softAssert.assertEquals(summarComanda, totalPrice);


    }

    public void waitLoadingChangesInCartPage() {
        new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(loadingImageAtSelect)));
    }


    public void assertPrice(float totalPrice, float totalPriceCalculate, SoftAssert softAssert) {
        if (Math.abs(totalPrice - totalPriceCalculate) > 0.02) {
            softAssert.fail("the calculated and total price shon on the page dosen t match");
        }
    }


    private void verifyPriceForMultipleItemsInCart() {
         List<WebElement> cartProduct=driver.findElements(By.cssSelector(itemContainerSelector));

         for(int i=0;i<cartProduct.size();i++){


         }


    }
}
