## Automation project to test UI/API [demoblaze](https://www.demoblaze.com/) website.

---
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
```

## How to run

---------
You could use the following Maven command
> mvn test

## Cross-browser testing

You can run tests with different browsers. To run tests with specific browser you can change property value in
this [config.properties](config.properties) file.
> browserName=chrome

Available browsers are:

```
chrome
safari
firefox
```

<p style='color:red'>Note: Selected browser should be installed on your local machine.</p>

# Reporting

---------

## Generate Allure Report

You can generate a report using this Maven command:

> mvn allure:serve