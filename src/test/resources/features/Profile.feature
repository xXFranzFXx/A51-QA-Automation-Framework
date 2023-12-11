@functionalityTest @profile
Feature: Testing functionality of Profile page
  Background:
    Given User is logged in
    And User navigates to profile page

    Scenario Outline: User can update the theme
      When User clicks on a theme "<theme>"
      Then The profile theme will be updated to "<theme>"

      Examples:
      |theme|
      |pines|
      |classic|
      |violet |
      |oak    |
      |slate  |
      |madison|
      |astronaut|
      |chocolate|
      |laura    |
      |rose-petals|
      |purple-waves|
      |pop-culture |
      |jungle      |
      |mountains   |
      |nemo        |
      |cat         |

    @ignore
    Scenario Outline: User can update profile theme
      When clicks on a theme "<theme>"


      Examples:
      |email|
      | testpro.com |
      | email@      |
      | " "         |
      | testpro@testpro |

