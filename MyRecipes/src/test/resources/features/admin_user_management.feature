Feature: Admin User Management
  As an admin of MyRecipes
  I want to promote regular users to admin and demote junior admins
  So that I can manage the platform's administrator hierarchy

  Background:
    Given "seniorAdmin" exists as an ADMIN in the system
    And "juniorAdmin" exists as an ADMIN in the system
    And "alice" exists as a USER in the system
    And "seniorAdmin" was promoted before "juniorAdmin"

  Scenario: An admin successfully promotes a regular user to admin
    Given "seniorAdmin" is acting as the current admin
    When "seniorAdmin" promotes "alice" to admin
    Then "alice" should now have the ADMIN role
    And "alice" should have a promotion date assigned

  Scenario: A regular user cannot promote another user to admin
    Given "alice" is the current user
    When "alice" tries to promote "seniorAdmin"
    Then the admin action should fail with message "Permission denied: PROMOTE_USER"

  Scenario: An admin cannot promote a user who is already an admin
    Given "seniorAdmin" is acting as the current admin
    When "seniorAdmin" promotes "juniorAdmin" to admin
    Then the admin action should fail with message "User already has admin privileges."

  Scenario: A senior admin successfully demotes a junior admin
    Given "seniorAdmin" is acting as the current admin
    When "seniorAdmin" demotes "juniorAdmin"
    Then "juniorAdmin" should now have the USER role
    And "juniorAdmin" should have no promotion date

  Scenario: An admin cannot demote another admin who was promoted earlier
    Given "juniorAdmin" is acting as the current admin
    When "juniorAdmin" demotes "seniorAdmin"
    Then the admin action should fail with message "Cannot demote an admin who was promoted before or at the same time as you."
