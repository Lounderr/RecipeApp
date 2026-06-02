Feature: Ingredient selection

  Scenario: Free user selects a free ingredient
    Given The user is logged in with role "USER"
    When The user selects ingredient "Tomato" with category "Vegetable"
    Then the ingredient is selected successfully

  Scenario: Free user selects a premium ingredient
    Given The user is logged in with role "USER"
    When The user selects ingredient "Truffle" with category "Fungi"
    Then an ingredient error is shown "This ingredient requires a premium subscription"

  Scenario: Premium user selects a premium ingredient
    Given The user is logged in with role "PREMIUM"
    When The user selects ingredient "Truffle" with category "Fungi"
    Then the ingredient is selected successfully

  Scenario: Free user sees only free ingredients
    Given The user is logged in with role "USER"
    When The user opens the ingredient list
    Then 2 ingredients are shown

  Scenario: Premium user sees all ingredients
    Given The user is logged in with role "PREMIUM"
    When The user opens the ingredient list
    Then 4 ingredients are shown
