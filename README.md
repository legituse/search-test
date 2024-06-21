# Search-Test

## Overview
This project demonstrates automated testing of search functionality on eBay using Selenium, Cucumber, and Maven.

### Technologies Used
- Selenium
- Cucumber
- WebDriverManager
- JUnit

## Prerequisites
- Java Development Kit (JDK) version 17
- Maven
- Google Chrome

## Instructions
1. Clone the repository.
2. Run tests using Maven (`mvn test`).

## Test Scenarios
- **User Search**: Verifies search functionality with multiple scenarios:
    - Searching with a valid query.
    - Verifying results for valid and invalid queries.
    - Navigating through multiple result pages.
    - Checking search suggestions.

## Note
- After running the tests, a `cucumber_report.html` is created under the `target` directory for viewing the results in a more organized and detailed manner in a browser. You can view the report for the tests ran locally [here](https://html-preview.github.io/?url=https://github.com/legituse/search-test/blob/main/cucumber_report.html).