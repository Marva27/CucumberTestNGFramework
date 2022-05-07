@Google
Feature: Google Website
  To test Google website

  @HomePage
  Scenario: Visit Google Home Page
    Given I connect to https://www.google.com
    Then I should see Google Home page
