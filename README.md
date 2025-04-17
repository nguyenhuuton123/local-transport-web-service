# Local Transport Web Service

A Spring Boot-based web service for managing local transport operations.

## Project Overview

This project is a RESTful web service built with Spring Boot that provides APIs for managing local transport operations. It includes features for user authentication, data persistence, and secure API endpoints.

## Technology Stack

- **Framework**: Spring Boot 3.2.6
- **Language**: Java 17
- **Database**: MySQL
- **ORM**: Spring Data JPA
- **Security**: Spring Security with JWT
- **Database Migration**: Flyway
- **Build Tool**: Gradle
- **Testing**: JUnit 5

## Key Features

- RESTful API endpoints
- JWT-based authentication
- Database migration support
- Input validation
- Model mapping with ModelMapper
- Secure password handling
- Development tools support

## Project Structure

```
src/
├── main/
│   ├── java/          # Java source code
│   └── resources/     # Configuration files
│       ├── application.properties
│       ├── application-local.properties
│       └── application-dev.properties
├── test/             # Test source code
build.gradle          # Project dependencies and configuration
```

## Getting Started

### Prerequisites

- Java 17 or higher
- MySQL database
- Gradle

### Installation

1. Clone the repository
2. Configure database connection in `application.properties`
3. Run database migrations using Flyway
4. Build the project:
   ```bash
   ./gradlew build
   ```
5. Run the application:
   ```bash
   ./gradlew bootRun
   ```

## Configuration

The application supports multiple environments:
- Development (`application-dev.properties`)
- Local (`application-local.properties`)
- Production (`application.properties`)

## Security

The application uses Spring Security with JWT for authentication and authorization. All endpoints are secured by default and require proper authentication.

## Testing

Run the test suite:
```bash
./gradlew test
```

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
