# Reporting API

## Overview

This API allows users to generate and manage various types of reports and export them in PDF or CSV format. The API
offers
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
- **Spring Security** - Security framework.
- **Spring Web** - Web framework.
- **Spring Boot Starter Test** - Unit testing tools.
- **Spring Boot Starter Validation** - Validation tools.
- **Spring Boot Starter Data JPA** - Data access tools.

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
git clone https://github.com/teamcubation/byma-financial-report.git
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

By default, the application runs on `http://localhost:8000`. You can access the API via this URL.

### 4. Access Swagger UI

Once the application is running, you can view the API documentation and interact with it through the Swagger UI:

```bash
http://localhost:8000/swagger-ui/index.html
```

---

# Important Notice 🚨

**Before accessing most endpoints in this API, users must first generate an authentication token by calling an endpoint
in the `AuthController`.**

- For full access to all features, including those limited to administrators, it is recommended to generate a token with
  `ADMIN` permissions.

- The token will be required in the `Authorization` header (as `Bearer <token>`) for endpoints with restricted access.

> **To generate a token with `ADMIN` permissions:**
> Use the `POST /api/v1/auth/register` endpoint and log in with admin credentials.

in de body:

```json
{
  "username": "admin",
  "email": "admin@gmail.com",
  "password": "test@1234"
}
```

  ---

### 5. Sample API Requests

Here are some sample requests you can use to interact with the API via Postman:

1. **Get a Report in PDF format (GET /report/generateReport?typeFile=pdf)**

`GET http://localhost:8000/report/generateReport?typeFile=pdf`

2. **Get a Report in CSV format (GET /report/generateReport?typeFile=csv)**

`GET http://localhost:8000/report/generateReport?typeFile=csv`

### 6. Running Tests

To run unit tests and integration tests:

```bash
mvn test
```

## Authentication

The API uses **JWT (JSON Web Tokens)** for authentication. To access protected endpoints, you need to include the JWT
token in the `Authorization` header.

### Steps to authenticate:

1. **Register a User (POST /api/v1/auth/register)**

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

3. **Use JWT for Authorization**

   Include the token in the `Authorization` header of subsequent requests:

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

Here’s the content in English and formatted in Markdown, with Postman request details for each endpoint:

---

# UserController

The `UserController` handles basic CRUD operations for registered users in the system.

### 1. Create User

- **Endpoint:** `POST /api/users`
- **Description:** Allows registering a new user.

#### Request Body

```json
{
  "username": "PEPE",
  "email": "PEPE@example.com",
  "password": "pepe123"
}
```

#### Response

- **Status:** `201 Created`
- **Body:**
  ```json
   {
   "username": "PEPE",
   "email": "PEPE@example.com",
   "password": "42342lkhsdfsdfsds", // hashed password
   "role": "USER"
   }
  ```

### 2. Get All Users

- **Endpoint:** `GET /api/users`
- **Description:** Returns a list of all users.

#### Response

- **Status:** `200 OK`
- **Body:**
  ```json
  [
    {
        "username": "admin",
        "email": "admin@gmail.com",
        "password": "$2a$10$BgpXcCu9ML8hNx.2bT2ob.WPx4u0fuCMQxFPQbNatbzxIqC1IRYZ6",
        "role": "ADMIN"
    },
    {
        "username": "PEPE",
        "email": "pepe@gmail.com",
        "password": "$2a$10$BgpXcCu9ML8hNx.2bT2ob.WPx4u0fuCMQxFPQbNatbzxIqC1IRYZ6",
        "role": "USER"
    }
  ]
  ```

### 3. Get User by ID

- **Endpoint:** `GET /api/users/{id}`
- **Description:** Returns information of a specific user.

#### Response

- **Status:** `200 OK`
- **Body:**
  ```json
   {
   "username": "admin",
   "email": "admin@gmail.com",
   "password": "$2a$10$BgpXcCu9ML8hNx.2bT2ob.WPx4u0fuCMQxFPQbNatbzxIqC1IRYZ6",
   "role": "ADMIN"
   }
  ```

### 4. Delete User

- **Endpoint:** `DELETE /api/users/{id}`
- **Description:** Deletes a user based on the specified ID.

#### Response

- **Status:** `204 No Content`

### 5. Update User

- **Endpoint:** `PUT /api/users/{id}`
- **Description:** Updates the data of a specific user.

#### Request Body

```json
{
  "username": "newUsername",
  "email": "newemail@example.com",
  "password": "newPassword"
}
```

#### Response

- **Status:** `200 OK`
- **Body:**
  ```json
  {
    "id": "123",
    "username": "newUsername",
    "email": "newemail@example.com"
  }
  ```

---

# MockController

The `MockController` contains example endpoints that demonstrate user authentication and role-based access.

### 1. Public Access

- **Endpoint:** `GET /mock/public`
- **Description:** An endpoint accessible to all users.

#### Response

- **Status:** `200 OK`
- **Body:**
  ```json
  {
    "message": "mock"
  }
  ```

### 2. User Access

- **Endpoint:** `GET /mock/user`
- **Description:** Accessible only to users with `USER` or `ADMIN` roles.

#### Response

- **Status:** `200 OK`
- **Body:**
  ```json
  {
    "message": "Only for users with role USER or ADMIN"
  }
  ```

### 3. Admin Access

- **Endpoint:** `GET /mock/admin`
- **Description:** Restricted to users with the `ADMIN` role.

#### Response

- **Status:** `200 OK`
- **Body:**
  ```json
  {
    "message": "Only for users with role ADMIN"
  }
  ```

### 4. Authenticated Access

- **Endpoint:** `GET /mock/auth`
- **Description:** Returns a welcome message to the authenticated user. Throws an exception if authentication fails.

#### Response

- **Status:** `200 OK` *(or appropriate error status if unauthorized)*
- **Body:**
  ```json
  {
    "message": "Welcome, authenticated user!"
  }
  ```

---

Let me know if there's anything more you'd like to add!