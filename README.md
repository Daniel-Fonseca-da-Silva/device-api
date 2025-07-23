# Device API

## Description
RESTful API for device management, allowing you to create, update, retrieve, and delete device resources. Developed in Java 21+ with Spring Boot, persistence in a relational database, and documentation via Swagger/OpenAPI.

## Device Domain
- **id**: Unique identifier
- **name**: Device name
- **brand**: Device brand
- **state**: State (`AVAILABLE`, `IN_USE`, `INACTIVE`)
- **creationTime**: Creation date/time (immutable)

## Features
- Create a new device
- Fully or partially update an existing device
- Retrieve a device by ID
- Retrieve all devices
- Retrieve devices by brand
- Retrieve devices by state
- Delete a device

## Domain Validations
- `creationTime` cannot be updated
- `name` and `brand` cannot be updated if the device is `IN_USE`
- Devices in `IN_USE` state cannot be deleted

## Endpoints
| Method | Endpoint                        | Description                  |
|--------|----------------------------------|------------------------------|
| POST   | /api/v1/devices                  | Create a new device          |
| PATCH  | /api/v1/devices/{id}             | Update a device              |
| GET    | /api/v1/devices/{id}             | Get device by ID             |
| GET    | /api/v1/devices                  | Get all devices              |
| GET    | /api/v1/devices/brand/{brand}    | Get devices by brand         |
| GET    | /api/v1/devices/state/{state}    | Get devices by state         |
| DELETE | /api/v1/devices/{id}             | Delete a device              |

## Requirements
- Java 21+
- Maven 3.9+
- Relational database (e.g., PostgreSQL, MySQL)
- Docker (required for containerization)
- Docker Compose (recommended for local development and testing)
- Lombok (for reducing boilerplate code in Java classes)
- Spring Boot 3.5.3 (framework for rapid application development)
- Swagger / OpenAPI 2.7.0 (for API documentation and testing)

## About Dockerfile and Docker Compose
- The **Dockerfile** defines how the application is packaged into a Docker image. It is used both by Docker Compose and can be used standalone for manual deployments or in cloud environments.
- The **docker-compose.yml** is the recommended way to run the environment locally, as it orchestrates the application, database, and automated tests with a single command.
- For most users, **it is recommended to use Docker Compose** for local development, testing, and validation.
- For production deployments, CI/CD, or cloud environments, use the Dockerfile as needed by your provider.

## How to Run the Project
1. Clone the repository:
   ```bash
   git clone <repo-url>
   cd device-api
   ```
2. Configure the database in `src/main/resources/application.properties`.
3. Build and run:
   ```bash
   ./mvnw spring-boot:run
   ```

## How to Run the Tests
```bash
./mvnw test
```

## API Documentation
Access `/swagger-ui.html` after starting the application to view and test the endpoints.

## Containerization
To run with Docker:
1. Make sure Docker is installed.
2. Build the image:
   ```bash
   docker build -t device-api .
   ```
3. Run the container:
   ```bash
   docker run -p 8080:8080 device-api
   ```

## Orchestration with Docker Compose
The project includes a `docker-compose.yml` file that makes it easy to run the complete environment (application + database) and automated tests.

### Starting the Complete Environment
```bash
docker-compose up -d dev-api
```
The application will be available at `http://localhost:8080` and the PostgreSQL database at `localhost:5432`.

### Running Automated Tests via Docker Compose
```bash
docker-compose up --abort-on-container-exit test
```
This will:
- Start the database
- Run automated tests in an isolated container
- Stop the test containers after completion

### Services defined in docker-compose:
- **db**: Persistent PostgreSQL database
- **dev-api**: Device API running in production mode
- **test**: Dedicated container for running automated tests

## Notes
- The `creationTime` field is set automatically and cannot be changed.
- Devices in use cannot be deleted or have their name/brand changed.
- The project follows SOLID principles, best practices, and clean code.
- Automated tests cover the main flows and validations.

## Future Improvements
- Implement authentication/authorization
- Pagination and sorting in listing endpoints
- Monitoring and metrics
- Automated deployment (CI/CD)