Feature: Login feature

  Scenario: Login Success
    Given I open browser
    And I open Login Page
    When I enter email "fake@fakeaccount.com"
    And I enter password "te$t$tudent"
    And I submit
    Then I should get logged in
