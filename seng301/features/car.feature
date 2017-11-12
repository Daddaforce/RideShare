Feature: Car

  Scenario: Register a car
    Given "type", "model", "colour", "licence plate", year is 1993 and number of physical seats is 5
    When I enter "car" details
    Then A "car" object is created with the defining data

