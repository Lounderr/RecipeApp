Feature: Favorites Management
  As a logged-in user (USER or ADMIN)
  I want to add and remove recipes from my personal favorites list
  So that I can quickly revisit the recipes I love

  Background:
    Given the recipe "Pasta Carbonara" is available in the system
    And the recipe "Caesar Salad" is available in the system

  Scenario: A regular user successfully adds a recipe to favorites
    Given I am logged in as a USER named "alice"
    When I add "Pasta Carbonara" to my favorites
    Then my favorites list should contain "Pasta Carbonara"

  Scenario: Adding the same recipe to favorites twice is rejected
    Given I am logged in as a USER named "alice"
    And I have already added "Pasta Carbonara" to my favorites
    When I add "Pasta Carbonara" to my favorites
    Then the favorites action should fail with message "Recipe is already in your favorites."

  Scenario: A regular user successfully removes a recipe from favorites
    Given I am logged in as a USER named "alice"
    And I have already added "Pasta Carbonara" to my favorites
    When I remove "Pasta Carbonara" from my favorites
    Then my favorites list should be empty

  Scenario: Removing a recipe that is not in favorites is rejected
    Given I am logged in as a USER named "alice"
    When I remove "Caesar Salad" from my favorites
    Then the favorites action should fail with message "Recipe not found in favorites."

  Scenario: An admin user can also add a recipe to favorites
    Given I am logged in as an ADMIN named "seniorAdmin"
    When I add "Caesar Salad" to my favorites
    Then my favorites list should contain "Caesar Salad"
