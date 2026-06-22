Feature: Authentication
  As a visitor to MyRecipes
  I want to register a new account and log in
  So that I can access personalized recipe features

  Scenario: Successful registration with valid credentials
    Given no user with username "newchef" exists in the system
    When I register with username "newchef" and password "chef123"
    Then the registration should succeed
    And the registered user should have the USER role

  Scenario: Registration fails when the username is already taken
    Given a user "alice" already exists in the system
    When I register with username "alice" and password "anotherpass"
    Then the registration should fail with message "Username already exists!"

  Scenario: Registration fails when the password is too short
    Given no user with username "shortpass" exists in the system
    When I register with username "shortpass" and password "ab"
    Then the registration should fail with message "Password must be at least 3 characters long."

  Scenario: Successful login with correct credentials
    Given a user "alice" already exists in the system
    When I log in with username "alice" and password "pass123"
    Then the login should succeed
    And the logged-in user should be "alice"

  Scenario: Login fails when the password is incorrect
    Given a user "alice" already exists in the system
    When I log in with username "alice" and password "wrongpass"
    Then the login should fail with message "Invalid password!"
