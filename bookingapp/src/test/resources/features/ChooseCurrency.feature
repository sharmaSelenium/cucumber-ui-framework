@chooseCurrency
Feature: Choose Currency
  As a guest user
  I can choose my currency
  To view hotel prices in selected currency

  Background:
    Given I am on booking.com home page

  @regression
  Scenario Outline: View hotel prices in different currencies
    When I choose my "<currency>" currency
    Then I can see "<currency>" currency symbol "<symbol>" in header section
    And I search hotel with following criteria
      | city  | checkInDaysFromToday | checkoutDaysFromToday | adults | children | rooms |
      | Dubai | 10                   | 18                    | 4      | 1        | 2     |
    Then I can see hotel prices in selected "<symbol>" currency
    Examples:
      | currency | symbol |
      | USD      | US$    |
      | INR      | Rs     |
      | EUR      | €      |









