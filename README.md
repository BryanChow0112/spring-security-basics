# Spring Security Employees

This project was created as a learning exercise to understand role-based access control in Spring Security. It demonstrates how to secure a basic Spring Boot application, exploring user management with both in-memory and JDBC stores. Passwords are securely handled with BCrypt hashing in a MySQL database. The configuration uses SecurityFilterChain to define role-based access for different application routes.


## Badges

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![BCrypt](https://img.shields.io/badge/bcrypt-grey?style=for-the-badge&logo=security-stackexchange&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)


## Features

- **User Authentication & Authorization:** Secures endpoints and requires authentication based on roles.
- **In-Memory & JDBC User Details:** Demonstrates both in-memory and database user management.
- **BCrypt Password Hashing:** Passwords are securely stored using BCrypt.
- **Role-Based Access Control:** Implements `ROLE_EMPLOYEE`, `ROLE_MANAGER` and `ROLE_ADMIN` with endpoint restrictions.


## API Endpoints & Roles

| HTTP Method | Endpoint             | Required Role | Description                     |
|-------------|----------------------|---------------|---------------------------------|
| `GET`       | `/api/employees`     | `ROLE_EMPLOYEE` | Retrieves a list of employees   |
| `GET`       | `/api/employees/{id}` | `ROLE_EMPLOYEE` | Retrieves a specific employee by ID |
| `POST`      | `/api/employees`     | `ROLE_MANAGER`  | Creates a new employee          |
| `PUT`       | `/api/employees/{id}` | `ROLE_MANAGER`  | Updates an existing employee     |
| `DELETE`    | `/api/employees/{id}` | `ROLE_ADMIN`    | Deletes an employee             |
