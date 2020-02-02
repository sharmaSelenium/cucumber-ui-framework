@carSearch
Feature: Care Hire search
  As a guest user
  I can search car hire by location
  To check the count of all available cars

  Background:
    Given I am on booking.com home page

  @regression
  Scenario Outline: Search car hire by different pick-up location
    When I navigate to care hire rental page
    And  I search car hire for "<location>" location
    And  I select pick-up "<pickUpHours>" HRS "<pickUpMins>" MINS
    And  I select drop-off "<dropOffHours>" HRS "<dropOffMins>" MINS
    Then I can see car hire result for "<location>" location
    Examples:
      | location         | pickUpHours | pickUpMins | dropOffHours | dropOffMins |
      | Dubai Airport    | 10          | 15         | 16           | 30          |
      | Heathrow Airport | 12          | 30         | 16           | 30          |









