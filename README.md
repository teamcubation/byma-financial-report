# Reporting API

## Overview

This API allows users to generate and manage various types of reports and export them in PDF format. The API offers
functionality to create, retrieve, and delete reports, as well as to download them in PDF. Additionally, the API
supports authentication using JWT and enforces role-based access control for managing the reports.

## Features

- Generate new reports with various filters and parameters.

## Technologies

- **Spring Boot** - Backend framework.
- **Java** - Programming language.
- **Maven** - Dependency management.
- **Lombok** - For reducing boilerplate code.
- **Jakarta Validation** - Validation of DTOs.
- **Swagger/OpenAPI** - API documentation.
- **H2 Database (Optional)** - In-memory database for development/testing.
- **Postman** - API testing.
- **JUnit 5** - Unit testing framework.
- **Mockito** - Mocking framework for unit tests.
- **Docker (Optional)** - Containerization.
- **Spring Data JPA** - Data access layer.
- **iText (Optional)** - PDF generation library.
- **JWT** - Authentication and authorization.

## Prerequisites

- Java 17 or higher.
- Maven 3.6 or higher.
- Postman (for testing API requests).
- (Optional) Docker, if you wish to use a containerized database.
- (Optional) IDE (Eclipse, IntelliJ IDEA, etc.).
- (Optional) Lombok plugin for your IDE.
- (Optional) H2 Database (if you don't want to use Docker).
- (Optional) Swagger UI (included in the project).
- (Optional) JUnit 5 and Mockito (for running tests).
- (Optional) Spring Data JPA (for data access).
- (Optional) Jakarta Validation (for validating DTOs).
- (Optional) iText (for PDF generation).
- (Optional) JWT (for securing endpoints).

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-repo/reporting-api.git
cd reporting-api
```

### 2. Build the Project

Use Maven to install dependencies and build the project:

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

By default, the application runs on `http://localhost:8080`. You can access the API via this URL.

### 4. Access Swagger UI

Once the application is running, you can view the API documentation and interact with it through the Swagger UI:

```bash
http://localhost:8080/swagger-ui/index.html
```

### 5. Sample API Requests

Here are some sample requests you can use to interact with the API via Postman:

1. **Create a Report (POST /api/reports)**

   ```json
   {
     "title": "Sales Report",
     "description": "Monthly sales report for the team",
     "parameters": {
       "dateRange": "2023-09-01 to 2023-09-30",
       "region": "North America"
     }
   }
   ```

2. **Get All Reports (GET /api/reports)**

   `GET http://localhost:8080/api/reports`

3. **Get Report by ID (GET /api/reports/{id})**

   `GET http://localhost:8080/api/reports/1`

4. **Delete Report (DELETE /api/reports/{id})**

   `DELETE http://localhost:8080/api/reports/1`

### 6. Export Report as PDF (GET /api/reports/{id}/pdf)

This endpoint allows you to download a report as a PDF file.

```bash
GET http://localhost:8080/api/reports/1/pdf
```

### 7. Running Tests

To run unit tests and integration tests:

```bash
mvn test
```

## Authentication

The API uses **JWT (JSON Web Tokens)** for authentication. To access protected endpoints, you need to include the JWT
token in the `Authorization` header.

### Steps to authenticate:

1. **Register a User (POST /api/auth/register)**

   ```json
   {
     "username": "admin",
     "email": "admin@gmail.com",
     "password": "password123"
   }
   ```

2. **Login to Get JWT (POST /api/auth/login)**

   ```json
   {
     "email": "admin@gmail.com",
     "password": "password123"
   }
   ```

   The response will include a JWT token:

   ```json
   {
     "token": "your-jwt-token-here"
   }
   ```

3. **Use JWT for Authorization**

   Include the token in the `Authorization` header of subsequent requests:

   ```bash
   Authorization: Bearer your-jwt-token-here
   ```

## Postman Collection

A Postman collection with pre-configured requests for all the API endpoints can be imported to test the API locally.
Simply import the provided Postman JSON file and adjust the environment variables as necessary.

### Importing the Collection

1. Download the provided Postman JSON file.
2. Open Postman and click on the "Import" button.
3. Select the downloaded JSON file and import it.
4. You can now test the API using the requests included in the collection.

This is a Spring Boot-based API that allows you get reports for a given user or admin can get reports for all users. The
API is documented using Swagger, and you can interact with it using the Swagger UI.

# Authentication and Authorization API - Postman Collection

This API provides functionality for user authentication and authorization, with role-based access control using JWT. The
collection can be used to test different endpoints related to public and protected resources, and user roles.

## Prerequisites

- **Admin Credentials**:  
  By default, an admin user is available with the following credentials:
    - **Email**: `admin@gmail.com`
    - **Password**: `test@1234`

  You can use these credentials to log in as an admin user via the `/login` endpoint.

- **Password Encryption**:  
  Passwords are stored hashed using the following code:

  ```java
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
  
  @Override
  public User register(User user) {
      UserEntity userEntity = UserPersistenceMapper.userToUserEntity(user);
      userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
      UserEntity saved = userRepository.save(userEntity);
      return UserPersistenceMapper.userEntityToUser(saved);
  }
  ```

## Endpoints

### Public and Private Resources

The collection contains several endpoints to test different levels of access (public, authenticated, user roles):

1. **Public Endpoint**
    - **URL**: `http://localhost:8080/mock/public`
    - **Method**: `GET`
    - **Authorization**: No authentication required

2. **Private Endpoint (Authenticated)**
    - **URL**: `http://localhost:8080/mock/auth`
    - **Method**: `GET`
    - **Authorization**: Basic authentication required (admin credentials)

3. **Private Endpoint (Role: USER)**
    - **URL**: `http://localhost:8080/mock/public/user`
    - **Method**: `GET`
    - **Authorization**: Basic authentication required (admin credentials)

4. **Private Endpoint (Role: ADMIN)**
    - **URL**: `http://localhost:8080/mock/auth`
    - **Method**: `GET`
    - **Authorization**: Basic authentication required (admin credentials)

### User Authentication

These endpoints manage user registration and login, with support for role-based access.

1. **User Registration**
    - **URL**: `http://localhost:8080/api/v1/auth/register`
    - **Method**: `POST`
    - **Body**:
      ```json
      {
          "username": "username",
          "email": "test@gmail.com",
          "password": "test@1234"
      }
      ```

2. **Login**
    - **URL**: `http://localhost:8080/api/v1/auth/login`
    - **Method**: `POST`
    - **Body**:
      ```json
      {
          "email": "test@gmail.com",
          "password": "test@1234"
      }
      ```

3. **Admin Login**
    - Same as regular login, using the admin credentials provided earlier.

## Usage Instructions

1. **Register as Admin**:  
   Use the `/register` endpoint with the admin credentials to ensure the user is assigned the admin role.  
   Example body:
   ```json
   {
       "username": "admin",
       "email": "admin@gmail.com",
       "password": "test@1234"
   }
   ```

2. **Login to Get JWT**:  
   Use the `/login` endpoint to log in and receive a JWT token. This token must be included in the `Authorization`
   header as a Bearer token when accessing protected endpoints.

3. **Access Protected Endpoints**:  
   After logging in and receiving a JWT token, use it to access private endpoints based on the assigned user roles (
   ADMIN or USER).

## Postman Collection

The provided Postman collection includes pre-configured requests for all the endpoints mentioned above, which can be
used to test the API locally. Simply import the collection into Postman, and adjust the environment variables or URLs if
necessary.

### Importing the Collection

1. Download the provided Postman JSON file.
2. Open Postman and click on the "Import" button.
3. Select the downloaded JSON file and import it.
4. You can now test the API using the requests included in the collection.

Make sure to adjust any parameters, headers, or authentication methods as needed based on your setup.