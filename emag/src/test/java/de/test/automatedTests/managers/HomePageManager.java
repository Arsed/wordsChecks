package de.test.automatedTests.managers;

import de.test.automatedTests.utils.UtilsActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.*;

public class HomePageManager {

    WebDriver driver;
    public static String searchBar = "#searchboxTrigger";
    public static String searchButton = ".btn.btn-default.searchbox-submit-button";
    // public static String productsContainer = ".card-section-wrapper.js-section-wrapper";
    public static String productsContainer = ".card-item.js-product-data";
    public static String productPrice = ".card-section-wrapper.js-section-wrapper .card-section-btm .card-body:nth-child(2) .product-new-price";
    public static String productsAddToCartButton = ".card-section-wrapper.js-section-wrapper .card-section-btm .card-footer.hidden-xs";
    public static String closePopUpWindow = ".modal-header .em.em-close";
    public static String buttonInContainerSelector = ".card-section-btm .card-footer.hidden-xs";
    public static String numberOfItemsFromCart = ".jewel.jewel-danger";
    public static String myCartSelector = "#my_cart";
    public static String titleOfItemsSelector = "a.product-title.js-product-url";
    public static String imageOfItemsFromComparare = ".product-comparison-list .product-comparison-item div span img ";
    public static String toCompareCheckBox = ".card-toolbox button:nth-child(1)";
    public static String imageOfProductInMainPage = ".card-heading .thumbnail img";
    public static String compareButton = ".product-comparison-trigger";
    public static String compareAlertSelector = ".ns-content";
    public static String showDetailCartButton = "a.btn.btn-primary.btn-sm.btn-block";

   public static String[] itemsName={"televizor","telefon","minge","xbox","tastatura","incarcator","nike","adidas"};

    public HomePageManager(WebDriver driver) {
        this.driver = driver;
    }


    public void search(String itemName) {
        driver.findElement(By.cssSelector(searchBar)).clear();
        driver.findElement(By.cssSelector(searchBar)).sendKeys(itemName);
        driver.findElement(By.cssSelector(searchButton)).click();
    }


    public List<Product> lowPrices(int number) {

        List<WebElement> pricesElementsList = driver.findElements(By.cssSelector(productPrice));

        List<Product> pageProducts = new ArrayList<>(pricesElementsList.size());
        List<Product> lastElements = new ArrayList<>(number);


        for (int i = 0; i < pricesElementsList.size(); i++)
            pageProducts.add(i, getProduct(i));


        Collections.sort(pageProducts, new Comparator<Product>() {
            public int compare(Product o1, Product o2) {
                if (o1.getPrice() < o2.getPrice()) return -1;
                else if (o1.getPrice() == o2.getPrice()) return 0;
                else return 1;
            }
        });

        for (int i = 0; i < number; i++)
            lastElements.add(i, pageProducts.get(i));

        return lastElements;
    }


    public Product getProduct(int index) {
        List<WebElement> itemsContainer = driver.findElements(By.cssSelector(productsContainer));
        List<WebElement> pricesElementsList = driver.findElements(By.cssSelector(productPrice));
        Float price;
        String id;
        String title;
        price = Float.parseFloat(pricesElementsList.get(index).getText().replaceAll("[^\\d]", "")) / 100;
        title = itemsContainer.get(index).findElement(By.cssSelector(titleOfItemsSelector)).getText();
        id = itemsContainer.get(index).getAttribute("data-offer-id");
        return new Product(itemsContainer.get(index), price, index, title, id);
    }


    public static class Product {

        WebElement elementContainer;
        float price;
        int index;
        String title;
        String id;

        public Product(WebElement elementContainer, float price, int index, String title, String id) {
            this.price = price;
            this.index = index;
            this.title = title;
            this.elementContainer = elementContainer;
            this.id = id;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getPrice() {
            return price;
        }

        public int getIndex() {
            return index;
        }

        public String getTitle() {
            return title;
        }

        public WebElement getElementContainer() {
            return elementContainer;
        }

        public String getId() {
            return id;
        }
    }


    public void closeCartPopUp() {
        WebElement popup = new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.elementToBeClickable(By.cssSelector(closePopUpWindow)));
        popup.click();
        new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.invisibilityOf(popup));
    }


    public int addToCart(List<Product> cheapElements) {

        int numberOfItems = 0;
        //make invisible the header because have problems for focus some elements
        UtilsActions.displayHeader("none", driver);

        //adding to cart the list of items
        for (int i = 0; i < cheapElements.size(); i++) {
            WebElement addToCartButton = cheapElements.get(i).getElementContainer().findElement(By.cssSelector(HomePageManager.buttonInContainerSelector));

            //scroll to the element and wait for it
            new Actions(driver).moveToElement(addToCartButton).perform();
            new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.elementToBeClickable(addToCartButton));

            //add to cart the item and close the popup window
            if (addToCartButton.getText().equals("Adauga in Cos")) {
                addToCartButton.click();
                closeCartPopUp();
                numberOfItems++;
            }
        }

        //make the header of the page visible
        UtilsActions.displayHeader("block", driver);
        return numberOfItems;
    }


    public boolean addToCartItem(int index) {

        //make invisible the header because have problems for focus some elements
        UtilsActions.displayHeader("none", driver);

        List<WebElement> itemsContainer = driver.findElements(By.cssSelector(productsContainer));
        WebElement addToCartButton = itemsContainer.get(index).findElement(By.cssSelector(HomePageManager.buttonInContainerSelector));

        //scroll to the element and wait for it
        new Actions(driver).moveToElement(addToCartButton).perform();
        new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.elementToBeClickable(addToCartButton));

        //add to cart the item and close the popup window
        if (addToCartButton.getText().equals("Adauga in Cos")) {
            addToCartButton.click();
            closeCartPopUp();
            //make the header of the page visible
            UtilsActions.displayHeader("block", driver);
            return true;
        }

        //make the header of the page visible
        UtilsActions.displayHeader("block", driver);
        return false;
    }


    public void verifyCartItems(List<Product> cheapElements) {
        clickOnCartIcon();

        List<WebElement> itemsContainer = driver.findElements(By.cssSelector(CartPageManager.itemContainerSelector));

        for (int i = 0; i < itemsContainer.size(); i++) {

            for (int j = 0; j < itemsContainer.size(); j++) {
                if (itemsContainer.get(i).getAttribute("data-id").equals(cheapElements.get(j).getId())) {
                    Float price = Float.parseFloat(itemsContainer.get(i).findElement(By.cssSelector(CartPageManager.priceOfItemSelector)).getText().replaceAll("[^\\d]", "")) / 100;
                    Assert.assertEquals(price, cheapElements.get(j).getPrice());
                    Assert.assertEquals(itemsContainer.get(i).findElement(By.cssSelector(CartPageManager.titleOfTheItems)).getText(), cheapElements.get(j).getTitle());
                }
            }

        }
    }


    public void verifyImageOfElement(int index) {
        List<WebElement> productsImages = driver.findElements(By.cssSelector(HomePageManager.imageOfProductInMainPage));
        WebElement element = driver.findElement(By.cssSelector(HomePageManager.imageOfItemsFromComparare));
        Assert.assertEquals(productsImages.get(index).getAttribute("src"), element.getAttribute("src"), "the scr of the element from homepage andfrom compare box is not the same");
    }


    public void verifyCompareAlert(String alert) {
        WebElement element = new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(HomePageManager.compareAlertSelector)));
        Assert.assertEquals(element.getText(), alert);
    }


    public List<Product> randomSelectProductsForCompare(int numberOfProducts) {

        UtilsActions.displayHeader("none", driver);
        Random random = new Random();

        //we need to save all the products that add in compare to can verify after in compare window
        List<Product> products = new ArrayList<>(numberOfProducts);
        int index;

        //here we will save the index of the products that was added in cart
        Integer[] indexList = new Integer[numberOfProducts];
        Arrays.fill(indexList, -1);

        List<WebElement> pageItems = driver.findElements(By.cssSelector(HomePageManager.toCompareCheckBox));
        //chose a random numbers
        for (int i = 0; i < numberOfProducts; i++) {
            index = random.nextInt(60);

            //if the items is already selected we will chose another
            while (Arrays.asList(indexList).contains(index))
                index = random.nextInt(60);

            //save the new index value
            indexList[i] = index;
            pageItems.get(index).click();

            products.add(i, getProduct(index));
        }
        UtilsActions.displayHeader("block", driver);
        return products;
    }


    public void clickOnCartIcon() {
        //enter on cart menu
        driver.findElement(By.cssSelector(HomePageManager.myCartSelector)).click();
        //wait to load the page
        new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CartPageManager.cartPageTitle)));
    }

    public void selectProduct(int index){
        List<WebElement> itemsContainer = driver.findElements(By.cssSelector(HomePageManager.titleOfItemsSelector));
        itemsContainer.get(index).click();
    }
}