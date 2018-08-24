package de.test.automatedTests.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class UtilsActions {

    public static void displayHeader(String display, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("document.querySelector('#masthead').style.display='" + display + "'");
    }

}
