Feature: Homepage
  Background:
  As a user, I want to be able to open Homepage
  Acceptance Criteria:
  Welcome message for a new user should be the following: 'Hello, Student!'
  Recently played songs should be present if user played at least one song
  'View All' button should be displayed next to Recently played songs
  Recently added songs should be present
  Album name should be displayed for the Recently added songs
  Download and Shuffle icons should be present for Recently added songs
  Search field should be present and user should be able to access Search from the Homepage
  Your music panel should include the following pages: Home, Current Queue, All Songs, Albums, Artists
  PLAYLISTS panel should include:  button for new playlist creation and new smart playlist
  Playlists panel should include, Favorites playlist, Recently played playlist, smart playlists and user's created playlists
  Profile link, Logout and About Koel icons should be displayed and linked to correct pages.

    Rule: User logs in as a new user
      -User should see a welcome message
      -User should have no recently played songs
      Background: New user logs in
        Given User logs in as new user

      Scenario Outline: User will see a welcome message for new users
        Given User is on homepage
        Then User should see welcome message "<welcomeMessage>"
        Examples:
        |welcomeMessage|
        |Hey, student!|

      Scenario: Recently played songs should be present if user plays at least one song
        Given User is on homepage
        Then User should not see recently played songs
        When User plays a song
        Then User should see recently played songs

    Rule: User logs in as regular user
      - User has already played a song
      - User has updated profile name
      Background: Regular user logs in
        Given User logs in

      Scenario: User will see a welcome message
        Given User is on homepage
        Then welcome message should contain user's name


    Scenario: Recently played songs should be present if user played at least one song
       Given User is on homepage
       Then User should see recently played songs
