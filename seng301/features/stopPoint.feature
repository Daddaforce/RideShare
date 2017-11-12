Feature: StopPoint

  Scenario: Create a stop point
    Given  my "stopPoint" is 123 "Fake Street" in "RandomSuburb"
    When I add my stopPoint
    Then A new stopPoint should be added

