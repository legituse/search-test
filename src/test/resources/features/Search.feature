Feature: Search Functionality on Ebay Home Page

  Scenario Outline: Valid Search Query
    Given User is on the Ebay main page
    When User fills in the Search field with "<searchTerm>"
    And User clicks on the Search button
    Then User verifies that a response relating to "<searchTerm>" is given

    Examples:
      | searchTerm                     |
      | car                            |
      | laptop                         |
      | camera                         |
      | headphones                     |
      | some valid word but no results |

  Scenario Outline: Invalid Search Query
    Given User is on the Ebay main page
    When User fills in the Search field with "<invalidSearchTerm>"
    And User clicks on the Search button
    Then User verifies that no results are displayed for "<invalidSearchTerm>"

    Examples:
      | invalidSearchTerm |
      # Empty String
      |                   |
      | !@#$%^&*()_+      |
      | ..............    |
      | 😎😎😎😎😎        |


  Scenario Outline: Search Results Suggestions
    Given User is on the Ebay main page
    When User fills in the Search field with "<searchTerm>"
    And User clicks on the Search button
    Then User sees search suggestions related to "<searchTerm>"

    Examples:
      | searchTerm |
      | cat        |
      | drone      |
      | phone case |


  Scenario Outline: Search Results Pagination
    Given User is on the Ebay main page
    When User fills in the Search field with "<searchTerm>"
    And User clicks on the Search button
    Then User verifies that multiple pages of search results are available for "<searchTerm>" and they are navigable

    Examples:
      | searchTerm       |
      | iphone           |
      | shoes            |
      | desktop computer |


