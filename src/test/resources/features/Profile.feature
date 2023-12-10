@todo
Feature: Testing functionality of Profile page
  Background:
    Given User is logged in
    And User navigates to profile page

    Scenario Outline: User can update the theme
      When User clicks on a theme "<theme>"
      Then The profile theme will be updated to "<theme>"

      Examples:
      |theme|
      |Astronaut|
      | Violet  |
      | Nemo    |

    Scenario Outline: Attempting to update profile with invalid info results in error
      When User enters current password
      And User provides invalidly formatted email address "<email>"
      And User clicks submit
      Then User will receive error message

      Examples:
      |email|
      | testpro.com |
      | email@      |
      | " "         |
      | testpro@testpro |

