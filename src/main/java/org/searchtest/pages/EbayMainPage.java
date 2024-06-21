package org.searchtest.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

// page_url = https://ebay.ca
public class EbayMainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public EbayMainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @FindBy(id = "gh-ac")
    private WebElement searchField;

    @FindBy(xpath = "//input[@id='gh-btn'] | //button[@id='gh-search-btn']")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id='srp-river-results']/ul")
    private WebElement searchResultsList;

    @FindBy(xpath = "//nav[@role='navigation' and @class='pagination']")
    private WebElement navPagination;

    public void goToEbayHomePage() {
        driver.get("https://www.ebay.ca");

        // Wait until search button is shown
        wait.until(ExpectedConditions.visibilityOf(searchButton));
    }

    public void enterSearchQuery(String searchQuery) {
        // Use JavaScript Executor to set value of the input field, used so emojis can be sent
        searchField.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", searchField, searchQuery);

    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public int getSearchResultListCount() {
        try {
            // Wait for visibility of searchResults element
            wait.until(ExpectedConditions.visibilityOf(searchResultsList));

            List<WebElement> liElements = searchResultsList.findElements(By.xpath(".//li"));
            return liElements.size();

        } catch (Exception e) {
            return -1;
        }
    }


    public boolean hasNextPage() {
        try {
            WebElement nextPageButton = navPagination.findElement(By.xpath(".//*[@type='next' and contains(@class, 'pagination__next') and not(@aria-disabled='true')]"));
            return nextPageButton.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void goToNextPage() {
        WebElement nextPageButton = navPagination.findElement(By.xpath(".//a[@type='next' and contains(@class, 'pagination__next')]"));
        nextPageButton.click();

        // Wait until next set of results are shown.
        wait.until(ExpectedConditions.visibilityOf(searchResultsList));
    }

    public List<String> getSearchSuggestionsText() {
        List<String> suggestionsText = new ArrayList<>();
        List<WebElement> liElements = searchResultsList.findElements(By.xpath(".//li[contains(@class, 's-item')]"));

        // Go through each search result and add the item's name/title to the text array.
        for (WebElement li : liElements) {
            WebElement spanElement = li.findElement(By.xpath(".//span[@role='heading']"));
            suggestionsText.add(spanElement.getText().trim());
        }
        return suggestionsText;
    }

}