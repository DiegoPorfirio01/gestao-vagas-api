# Job Management API

<div align="center">
  <h3>A modern REST API for job opportunity management</h3>
</div>

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Swagger](https://img.shields.io/badge/OpenAPI-3.0-85EA2D)
![License](https://img.shields.io/badge/license-MIT-blue)

## 🚀 Technologies

- Java 21
- Spring Boot 3.2.x
- PostgreSQL 16
- Flyway Migration
- Maven
- OpenAPI 3.0 / Swagger UI
- Spring Data JPA / Hibernate
- Lombok
- Docker & Docker Compose

## 📋 Prerequisites

Before you begin, ensure you have:
- Java 21+
- Maven
- Docker and Docker Compose
- PostgreSQL


## 🚀 Running the application

This will start:
- PostgreSQL database (port 5432)
- SonarQube (port 9000)

2. Access SonarQube:
- URL: http://localhost:9000
- Default credentials: 
  - Username: admin
  - Password: admin

3. Run SonarQube analysis:
```bash
mvn clean verify sonar:sonar \
  mvn clean verify sonar:sonar \                                                                                 
  -Dsonar.projectKey=gestao_vagas \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=your_sonar_token
```

obs: a sonar key é criada ao logar no sonarqube

## 🛠️ Installation

1. Clone the repository

```bash
git clone https://github.com/diegoporfirio01/gestao_vagas.git
```

2. Navigate to the project directory

```bash
cd gestao_vagas
```

3. Start PostgreSQL using Docker Compose

```bash
docker compose up -d
```

4. Run the application

```bash
mvn spring-boot:run
```

## 📚 API Documentation

The API documentation is available through Swagger UI. After running the application, access:

```bash
http://localhost:8080/swagger-ui/index.html
```

## 🔍 Features

### Company Management
- Company registration
- Company profile management
- List all companies

### Candidate Management
- Candidate registration
- Profile management
- List all candidates

## 🗄️ Database Structure

The application uses PostgreSQL with Flyway migrations for database versioning. Main tables:

- `companies`: Stores company information
  - id (UUID)
  - name
  - slug
  - email
  - website
  - password
  - description
  - created_at
  - updated_at

- `candidates`: Stores candidate information
  - id (UUID)
  - name
  - username
  - email
  - password
  - description
  - curriculum
  - created_at
  - updated_at

## 🔒 Validation

The API implements comprehensive validation for:
- Email formats
- Required fields
- Username patterns
- Password requirements
- Website format (for companies)

## 🛡 Security Features

- Password encryption using BCrypt
- JWT Authentication
- Input validation and sanitization
- Unique constraints for emails and usernames
- Rate limiting
- CORS configuration
- Custom exception handling with proper HTTP status codes

## 🌟 API Endpoints

### Authentication
- `POST /auth/login` - Authenticate user
- `POST /auth/refresh` - Refresh access token

### Companies
- `POST /api/v1/companies` - Register a new company
- `GET /api/v1/companies` - List all companies
- `GET /api/v1/companies/{id}` - Get company details
- `PUT /api/v1/companies/{id}` - Update company
- `DELETE /api/v1/companies/{id}` - Delete company

### Candidates
- `POST /api/v1/candidates` - Register a new candidate
- `GET /api/v1/candidates` - List all candidates
- `GET /api/v1/candidates/{id}` - Get candidate details
- `PUT /api/v1/candidates/{id}` - Update candidate
- `DELETE /api/v1/candidates/{id}` - Delete candidate

## 🔧 Configuration

### Application Properties

```properties
# Application
spring.application.name=gestao_vagas
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/gestao_vagas
spring.datasource.username=${DB_USERNAME:admin}
spring.datasource.password=${DB_PASSWORD:admin}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# OpenAPI/Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# Security
security.jwt.secret=${JWT_SECRET:your-secret-key}
security.jwt.expiration=3600000
```

## 🐳 Docker Support

The project includes Docker support with a docker-compose file for PostgreSQL:

```yaml
services:
postgres:
container_name: gestao_vagas_postgres
image: postgres
ports:
5432:5432
environment:
POSTGRES_USER=admin
POSTGRES_PASSWORD=admin
POSTGRES_DB=gestao_vagas
```

## 🤝 CI/CD Pipeline

This project implements a robust CI/CD pipeline using GitHub Actions and Docker for automated building, testing, and deployment.

### Pipeline Features

- Automated builds on push to main branch
- Docker image creation and publishing
- Automated deployment to production
- Environment-specific configurations
- Continuous integration with test execution

### Workflow Structure

```yaml
name: Gestao de Vagas Application

on:
  push:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - Checkout code
      - Setup Java 21
      - Build project with Maven
      - Login to Docker Hub
      - Build and publish Docker image
  
  deploy:
    needs: build
    runs-on: self-hosted
    steps:
      - Pull latest Docker image
      - Deploy container with environment variables
```

### Docker Configuration

The application is containerized using Docker for consistent deployment across environments:

```dockerfile
FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Environment Variables

The following environment variables are required for deployment:

- `DATABASE_URL`: Database connection URL
- `DATABASE_USERNAME`: Database username
- `DATABASE_PASSWORD`: Database password
- `DATABASE_NAME`: Database name
- `DOCKER_USERNAME`: Docker Hub username
- `DOCKER_PASSWORD`: Docker Hub password

### Deployment Process

1. The pipeline is triggered on push to the main branch
2. Maven builds and tests the application
3. Docker image is built and pushed to Docker Hub
4. The application is deployed to production server
5. Environment variables are injected during container startup

## 🤝 Contributing

Feel free to contribute to this project. Any contributions you make are greatly appreciated.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## ✨ Next Steps

- [ ] Implement authentication and authorization
- [ ] Add job posting functionality
- [ ] Implement candidate application system
- [ ] Add search and filtering capabilities
- [ ] Implement email notifications
- [ ] Add role-based access control
- [ ] Implement API rate limiting
- [ ] Add caching mechanisms
- [ ] Implement file upload for resumes
- [ ] Add job matching algorithm


## 🙏 Acknowledgments

- Spring Boot Documentation
- PostgreSQL Documentation
- Swagger UI
- The Spring Boot Community

---

Made with ❤️ by Diego Porfirio
