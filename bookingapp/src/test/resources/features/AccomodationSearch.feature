@hotelSearch
Feature: Accommodation search
  As a guest user
  I want to search accommodation by city names
  To view list of all accommodations

  @regression
  Scenario Outline: Search accommodation by city names
    Given I am on booking.com home page
    When I search hotel with following criteria
      | city  | checkInDaysFromToday | checkoutDaysFromToday | adults | children | rooms |
      | Dubai | 10                   | 18                    | 4      | 1        | 2     |
    Then I can see hotels for "<city>" destination
    Examples:
      | city  |
      | Dubai |





