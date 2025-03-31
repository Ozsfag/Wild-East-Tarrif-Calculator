# Tariff Calculator Project

This project calculates the delivery cost based on the weight of the packages included in the shipment.

## Technologies Used

- Spring Boot 3
- Maven 3
- JUnit 5
- Swagger

## Local Setup

### Prerequisites

- Java 17 (JDK 17) must be installed.

### Running with IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Wait for indexing to complete.
3. Create a run configuration or execute the `main` method in the
   class [Main.java](app/src/main/java/ru/fastdelivery/Main.java).

### Running with Docker

1. Build the project:
   ```shell
   ./mvnw clean package
   ```
2. Build the Docker image:
   ```shell
   docker build -t ru.fastdelivery:latest .
   ```
3. Run the Docker container:
   ```shell
   docker run -p 8081:8080 ru.fastdelivery:latest
   ```

### Running with JAR

1. Build the project:
   ```shell
   ./mvnw clean package
   ```
2. Run the JAR file:
   ```shell
   java -jar app/target/app-1.0-SNAPSHOT.jar
   ```

## Testing

### Running Tests

Use the built-in Maven Wrapper to run tests, which also checks code style (checkstyle). Checkstyle reports are available
at [target/site/checkstyle-aggregate.html](target/site/checkstyle-aggregate.html).

- **Linux/macOS:**
  ```shell
  ./mvnw clean test
  ```

- **Windows:**
  ```shell
  ./mvnw.cmd clean test
  ```

## Swagger

Once the application is running, you can use the Swagger interface to execute requests:

[Swagger UI](http://localhost:8080/swagger-ui/index.html)

## Application Structure

The application is divided into Maven modules, each responsible for a specific area:

- **[app](app):** Contains the application entry point, bean configuration, and property reading
  from [application.yml](app/src/main/resources/application.yml).

- **[domain](domain):** Contains all business logic classes such as Package, Weight, Currency, and Cost. Classes should
  not depend on Spring Boot or other project modules.

- **[useCase](useCase):** Contains business logic processes using domain classes. Classes should only depend on the
  `domain` module and not on Spring Boot.

- **[web](web):** Contains controllers and is the only module that interacts with the external world. It depends on
  Spring Boot, `domain`, and `useCase`.

> **Note:** Do not change dependencies between modules.
