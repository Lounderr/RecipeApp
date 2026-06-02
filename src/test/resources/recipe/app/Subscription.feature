Feature: Subscription management

  Scenario: Successful purchase of credits
    Given The user has a balance of 5 credits
    When The user purchases 10 credits
    Then the balance is 15 credits

  Scenario: Spend credit
    Given The user has a balance of 3 credits
    When The user spends 1 credit
    Then the balance is 2 credits

  Scenario: Spend without credits
    Given The user has a balance of 0 credits
    When The user spends 1 credit
    Then a credit error is shown "Insufficient credits"

  Scenario: Purchase with invalid amount
    Given The user has a balance of 5 credits
    When The user purchases -5 credits
    Then a credit error is shown "Amount must be a positive number"

  Scenario: Check current balance
    Given The user has a balance of 7 credits
    When The user checks the balance
    Then the balance is 7 credits
