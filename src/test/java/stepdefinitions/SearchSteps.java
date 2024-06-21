package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.searchtest.pages.EbayMainPage;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SearchSteps {

    private WebDriver driver;
    private EbayMainPage ebayMainPage;

    @Before
    public void initializeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        ebayMainPage = new EbayMainPage(driver);
    }


    @Given("User is on the Ebay main page")
    public void userIsOnTheEbayMainPageTerms() {
        ebayMainPage.goToEbayHomePage();
    }

    @When("User fills in the Search field with {string}")
    public void userFillsInTheSearchFieldWith(String searchQuery) {
        ebayMainPage.enterSearchQuery(searchQuery);
    }

    @And("User clicks on the Search button")
    public void userClicksOnTheSearchButton() {
        ebayMainPage.clickSearchButton();
    }

    @Then("User verifies that a response relating to {string} is given")
    public void userVerifiesThatAResponseRelatingToIsGiven(String searchString) {
        int numberOfSearchResults = ebayMainPage.getSearchResultListCount();
        // If only one result is returned, that means no exact match was found, however the search term is valid
        assertTrue("Expected message \"" + searchString + "\" to appear on the page", numberOfSearchResults > 1);
    }

    @Then("User verifies that no results are displayed for {string}")
    public void userVerifiesThatNoResultsAreDisplayedFor(String searchString) {
        int numberOfSearchResults = ebayMainPage.getSearchResultListCount();
        assertTrue("Expected message \"" + searchString + "\" to not appear on the page", numberOfSearchResults < 1);
    }


    @Then("User verifies that multiple pages of search results are available for {string} and they are navigable")
    public void userVerifiesThatMultiplePagesOfSearchResultsAreAvailableForAndTheyAreNavigable(String searchString) {
        int maxPages = 3;
        int currentPage = 1;

        // Only navigate 3 pages maximum
        while (ebayMainPage.hasNextPage() && currentPage <= maxPages) {
            ebayMainPage.goToNextPage();
            currentPage++;
        }

        // After attempting navigation also verify that the page has results
        int numberOfSearchResults = ebayMainPage.getSearchResultListCount();
        assertTrue("Expected message \"" + searchString + "\" to appear on at least one page", numberOfSearchResults > 1);
    }

    @Then("User sees search suggestions related to {string}")
    public void userSeesSearchSuggestionsRelatedTo(String searchString) {
        List<String> suggestions = ebayMainPage.getSearchSuggestionsText();
        assertTrue("Expected search suggestions related to \"" + searchString + "\"",
                suggestions.stream().anyMatch(s -> s.toLowerCase().contains(searchString.toLowerCase())));
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

}
