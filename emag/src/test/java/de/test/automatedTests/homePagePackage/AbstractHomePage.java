package de.test.automatedTests.homePagePackage;

import de.test.automatedTests.config.AbstractAcceptanceTest;
import de.test.automatedTests.managers.CartPageManager;
import de.test.automatedTests.managers.HomePageManager;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class AbstractHomePage extends AbstractAcceptanceTest {

    public HomePageManager homePageManager;
    public CartPageManager cartPageManager;
   public SoftAssert softAssert;

    @BeforeMethod
    public void init() {
        homePageManager = new HomePageManager(getWebDriver());
        cartPageManager = new CartPageManager(getWebDriver());
        softAssert = new SoftAssert();
    }
}
