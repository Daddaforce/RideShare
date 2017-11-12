Feature: NewPhoto

  Scenario: Add a photo
    Given  a "photo" of unknown size
    When I upload "photo"
    Then total size of "photo" is 150 width and 150 height

