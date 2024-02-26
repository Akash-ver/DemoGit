@tag
  Feature: Error Validation
    I want to use this template for my feature file

  @Error
  Scenario Outline: Title of your scenario outline
    Given I landed on Ecommerce page
    When Logged in with username <name> and password <password>
    Then "Incorrect email or password." message is displayed
    Examples:
      |name                    | password           |
      |test1234897@gmail.com   |Test@12345          |
      |Test8974@gmail.com      | Test897@gmail.com |