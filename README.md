# Selenium Test Automation Framework

A robust and maintainable test automation framework built with Selenium WebDriver, TestNG, and Java for web application testing.

## Features

- **Page Object Model (POM) Design Pattern** for better maintainability and reusability
- **TestNG** for test management and execution
- **Maven** for dependency management
- **Configuration Management** for different environments (dev, qa, etc.)
- **Reusable Components** for common web interactions
- **Logging** for better test execution tracking
- **Cross-browser Testing** support

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6.0 or higher
- Chrome/Firefox browser installed
- Internet connection (for downloading dependencies)

## Getting Started

1. **Clone the repository**
   ```bash
   git clone [your-repository-url]
   cd AutomationFramework
   ```

2. **Install dependencies**
   
   mvn clean install
   ```

3. **Configuration**
   - Update the `src/main/resources/config.properties` file with your test environment details
   - Browser-specific settings can be configured in the respective property files under `src/main/resources/`

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── org/example/
│   │       ├── pages/        # Page Object classes
│   │       ├── utils/        # Utility classes
│   │       └── config/       # Configuration classes
│   └── resources/
│       ├── config.properties # Main configuration
│       ├── dev.properties    # Development environment settings
│       └── qa.properties     # QA environment settings
└── test/
    └── java/
        └── org/example/
            └── tests/        # Test classes
```

## Running Tests

### Run all tests
```bash
mvn test
```

### Run specific test suite
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run tests with specific browser
```bash
mvn test -Dbrowser=firefox
```

### Run tests in parallel
```bash
mvn test -Dparallel=methods -DthreadCount=3
```

## Test Reports

TestNG generates HTML reports in the `test-output` directory after test execution. You can find detailed test results, including passed/failed tests, execution times, and error messages.

## Best Practices

1. **Page Object Model**: Each page should have its own class with all the elements and actions related to that page.
2. **Reusable Methods**: Common actions should be placed in the `BasePage` class.
3. **Configuration Management**: Use property files for environment-specific configurations.
4. **Test Data**: Externalize test data using external files or data providers.
5. **Logging**: Add appropriate logging for better debugging.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

