Feature: Login feature

  Scenario: Login Success
    Given I open Login Page
    When I enter email "fake@fakeaccount.com"
    And I enter password "te$t$tudent"
    And I click submit
    Then I should be logged in