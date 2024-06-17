Feature: Verify the Creditcard Record in Database

  Scenario Outline: Fetch the credit card from excel and retrieve Data from Database
    Given Retrieve the Credit card data from excel for each Testcase "<Testcase>"
    When Get the credit card information from Database
    And Generate a request body
    Then Do a post call and validate the response of an API.
    And Validate Credit card number is Mapped with a PAN card in DB
    Examples:
      | Testcase |
      |TC01    |
      |TC02    |
      |TC03    |


