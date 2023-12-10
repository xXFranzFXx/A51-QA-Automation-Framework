Feature: Login feature
  #this step sets the initial step of opening the login page for all the scenarios.
  Background:
    Given User opens Log in page

  Scenario: Login Success
#    Given I open Login Page
    When I enter email "fake@fakeaccount.com"
    And I enter password "te$t$tudent"
    And I click submit
    Then I should be logged in

  Scenario Outline: Login incorrect password
    When I enter email "<email>"
    And I enter incorrect password "<password>"
    And I click submit
    Then I should still be on Login page

  Examples:
    | email                 | password           |
    | fake@fakeaccount.com  | fakePassword       |
    | fake@fakeaccount.com  | fakePassword2      |


  Scenario Outline: Login with invalid email
    When I enter "<email>"
    And I enter password "<password>"
    And I click submit
    Then I should still be on Login page
    Examples:
      | email                | password           |
      | fake@fakeaccountcom  | te$t$tudent1       |
      | fake@,.com           | te$t$tudent1     |


  Scenario: Empty Login and Password
    When I enter empty email " "
    And I enter emtpy password " "
    And I click submit
    Then I should still be on Login page


