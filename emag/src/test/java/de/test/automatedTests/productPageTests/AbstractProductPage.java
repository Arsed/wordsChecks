package de.test.automatedTests.productPageTests;

import de.test.automatedTests.config.AbstractAcceptanceTest;
import de.test.automatedTests.managers.CartPageManager;
import de.test.automatedTests.managers.HomePageManager;
import de.test.automatedTests.managers.ProductPageManager;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class AbstractProductPage extends AbstractAcceptanceTest {

    public HomePageManager homePageManager;
    public ProductPageManager productPageManager;
    public SoftAssert softAssert;

    @BeforeMethod
    public void init() {
        homePageManager = new HomePageManager(getWebDriver());
        productPageManager = new ProductPageManager(getWebDriver());
        softAssert = new SoftAssert();
    }
}
