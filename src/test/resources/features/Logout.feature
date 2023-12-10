Feature: Test logout button functionality
    Background:
      Given User opens Log in page

    Scenario: User can logout after successfully logging in
      Given User is logged in
      And Logout button is visible next to profile pic
      When User clicks logout button
      Then User is logged out and navigates back to login screen


    Scenario: User can logout after successfully changing profile name
      Given User is logged in
      When User clicks profile pic
      Then Profile page is opened
      When User enters current password
      And  User enters updated name
      And  User clicks save button
      Then Success message is displayed
      When Success message disappears
      And User clicks logout button
      Then User navigates back to login screen
