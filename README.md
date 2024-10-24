# Authentication and Authorization API - Postman Collection

This API provides functionality for user authentication and authorization, with role-based access control using JWT. The collection can be used to test different endpoints related to public and protected resources, and user roles.

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
   Use the `/login` endpoint to log in and receive a JWT token. This token must be included in the `Authorization` header as a Bearer token when accessing protected endpoints.

3. **Access Protected Endpoints**:  
   After logging in and receiving a JWT token, use it to access private endpoints based on the assigned user roles (ADMIN or USER).

## Postman Collection

The provided Postman collection includes pre-configured requests for all the endpoints mentioned above, which can be used to test the API locally. Simply import the collection into Postman, and adjust the environment variables or URLs if necessary.

### Importing the Collection

1. Download the provided Postman JSON file.
2. Open Postman and click on the "Import" button.
3. Select the downloaded JSON file and import it.
4. You can now test the API using the requests included in the collection.

Make sure to adjust any parameters, headers, or authentication methods as needed based on your setup.