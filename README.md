## Automation project to test UI/API [demoblaze](https://www.demoblaze.com/) website.

Тhe following frameworks have been used in the creation of this project

```
- Selenium
- REST Assured
- Test NG
```

The following Libraries have been used to make writing this test easier

```
- Faker - Library to generate fake data.
- WebDriverManager - Automated driver management.
- Awaitility - A Java DSL for synchronizing asynchronous operations.
- Hamcrest - Core API and libraries of hamcrest matcher.
- Jackson-databind - Basic data binding (mapping) library that allows for reading JSON content.
- Allure-testng - Flexible lightweight multi-language test report tool.
- Test Results Reporter - Can publish test results to Microsoft Teams, Slack, Google Chat and many more
```

## How to run

You could use the following Maven command
> mvn -Dtest=UiApiDemoBlazeTest test

To run with headless browser
> mvn test -Dbrowser.headless=true

## Cross-browser testing

You can run tests with different browsers. To run tests with specific browser you can change property value in
this [config.properties](config.properties) file.
> browser.name=chrome

Available browsers are:

```
chrome
safari
firefox
```

<p style='color:green'>Note: Selected browser should be installed on your local machine.</p>

# Reporting

## Generate Allure Report

You can generate a report using this Maven command:

> mvn allure:serve

## Generate Report to your Slack channel

<p style='color:green'>Note: You need to install Node.js 10 or above on your local machine.</p>

- Please create Slack webhook application and link it to your channel. (How to do
  this, you can watch video... [Link to YouTube video](https://www.youtube.com/watch?v=6NJuntZSJVA))
- Copy webhook link and paste it to [config.json](config.json) file as follows:

> "url": "https://hooks.slack.com/services/AAAAAAAAAAA/BBBBBBBBBBB/XXXXXXXXXXXXXXX",

- Change to `true` in [config.properties](config.properties) file as follows:

> publish.report=true

or set System variable when running a test
> -Dpublish.report=true

- As a result you will receive a message to your channel.

Ultimate command to run test
> mvn -Dtest=UiApiDemoBlazeTest -Dpublish.report=true -Dbrowser.headless=true test
