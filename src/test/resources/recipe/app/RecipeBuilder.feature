Feature: Building and publishing a recipe

  Scenario: Successful publishing of recipe
    Given The user is logged in with username "chef_john" and 10 credits
    And The user selects ingredient "Tomato" category "Vegetable"
    When The user enters instructions "Chop tomatoes and cook."
    And enters title "Tomato Soup"
    And clicks publish
    Then the recipe is published successfully
    And the user has 9 credits

  Scenario: Publish with empty instructions
    Given The user is logged in with username "chef_john" and 10 credits
    And The user selects ingredient "Tomato" category "Vegetable"
    When The user enters instructions ""
    And enters title "Tomato Soup"
    And clicks publish
    Then an error is shown "Instructions cannot be empty"

  Scenario: Publish with empty title
    Given The user is logged in with username "chef_john" and 10 credits
    And The user selects ingredient "Tomato" category "Vegetable"
    When The user enters instructions "Chop tomatoes."
    And enters title ""
    And clicks publish
    Then an error is shown "Title is required"

  Scenario: Publish without enough credits
    Given The user is logged in with username "chef_john" and 0 credits
    And The user selects ingredient "Tomato" category "Vegetable"
    When The user enters instructions "Chop tomatoes."
    And enters title "Tomato Soup"
    And clicks publish
    Then an error is shown "Insufficient credits"

  Scenario: View recipe history
    Given The user is logged in with username "chef_john" and 5 credits
    And the user has 3 published recipes in history
    When The user opens the history
    Then 3 recipes are shown
