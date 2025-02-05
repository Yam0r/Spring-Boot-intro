# Book-store

## Description
Welcome to the BookStore API! 
This is a fully developed API built with **Spring Boot** for managing books, categories, and orders. 
It allows users to register, log in, browse books, and place orders securely. 
Inspired by the need for an efficient, user-friendly platform for book management, this project is aimed at enhancing the user experience while maintaining secure and role-based access.

## Technologies & Tools 

- **Spring Boot**: Main framework used for building the application.
- **Spring Security**: Provides security, user authentication, and authorization.
- **Spring Data JPA**: Used for easy database interaction and ORM support.
- **MySQL**: Database for storing the application's data.
- **Swagger**: API documentation and testing tool.
- **Liquibase**: For automatic database schema management.
- **JUnit**: For writing unit and integration tests.
- **Postman**: For testing API endpoints.

## Technologies Required
- **Docker**: For containerization and running the application in a containerized environment.
- **Docker Compose**: For managing multi-container applications.
- **Java 17+**: For running the Spring Boot application locally.
- **MySQL**: Ensure you have MySQL running locally or use the Dockerized MySQL container.

## DB Diagram
![Example Image](images/exported_from_idea.drawio.png)

## Features

- **User Registration & Authentication**: Allows users to register, log in, and access the system securely.
- **Book Management**: CRUD operations for managing books, including adding, updating, and deleting books.
- **Order Management**: Users can create orders, view their order history, and process payments.
- **Role-based Access Control**: Users and admins have different access levels to the system.
- **Swagger UI**: Provides a user-friendly interface for exploring the API and testing endpoints.

## Project's API
[View Postman Collection](https://dark-robot-243308.postman.co/workspace/Team-Workspace~73e08a28-8587-4cdc-8945-ea1930a0c4cf/collection/23287144-18af10de-dcfe-474a-babd-8ce040ac944f?action=share&creator=23287144)

[Swagger UI](http://localhost:8080/api/swagger-ui/index.html)

## How to Use

- **Clone the Repository**:
   ```bash
   https://github.com/Yam0r/Spring-Boot-intro.git
  
- Create the **.env** File: Create a file named **.env** in the root directory of the project and add the following variables:
   ```dotenv
  MYSQLDB_USER=yourname
  MYSQLDB_PASSWORD=yourpassword
  MYSQLDB_DATABASE=yourdatabase
  MYSQLDB_ROOT_PASSWORD=yourrootpassword
  MYSQL_LOCAL_PORT=3307
  MYSQL_DOCKER_PORT=3306

  SPRING_LOCAL_PORT=8081
  SPRING_DOCKER_PORT=8080
  DEBUG_PORT=5005
- Run Docker Compose:
   ```bash
  docker-compose up --build
- Access the Application: Open your browser and go to http://localhost:8080.