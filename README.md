# Employee Management System

A comprehensive Employee Records Management System developed using **Spring Boot** and **Swing** for a desktop-based UI. The application includes RESTful APIs for backend operations, a user-friendly Swing-based frontend, and the ability to generate employee reports in CSV format.

---

## Features

### Core Features
1. **Employee Management:**
   - Create, Read, Update, Delete (CRUD) operations.
   - Search and filter employees.

2. **Role-Based Access Control:**
   - **HR Personnel:** Full CRUD access to employee data.
   - **Managers:** Limited access to employees in their department.
   - **Administrators:** Full system access, including user management.

3. **Audit Trail:**
   - Logs every change made to employee records.

4. **Reporting:**
   - Generate employee data reports in CSV format.
   - Download reports directly via the API or Swing-based UI.

5. **Validation:**
   - Ensures valid data input (e.g., email format, unique IDs).

---

## Getting Started

### Prerequisites
- **Java 17**
- **Maven**
- **Oracle SQL** (or use the provided Docker setup)
- **Postman** (for API testing)

---

## Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-repository/employee-management-system.git
   cd employee-management-system

2.  **Set Up the Database:**
- Use the `Dockerfile` or manually set up Oracle SQL with the provided schema.

3. **Build the Application**
- Run the following command to build the application:
    ```bash
    mvn clean install

4. **Run the Backend:**
    ```bash
    mvn spring-boot:run

5. **Run the Swing Application: Execute the LoginScreen main method in your IDE:**
    ```bash
    LoginScreen.main();

## API Usage

The system provides RESTful APIs documented with **Swagger/OpenAPI**.

### Access Swagger UI

After starting the backend, open the Swagger UI to explore and test the APIs:

- **URL:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Available Endpoints

| **HTTP Method** | **Endpoint**                          | **Description**                          |
|------------------|---------------------------------------|------------------------------------------|
| `GET`           | `/api/employees`                     | Get a list of all employees.             |
| `GET`           | `/api/employees/{id}`                | Get details of a specific employee.      |
| `POST`          | `/api/employees`                     | Add a new employee.                      |
| `PUT`           | `/api/employees/{id}`                | Update an existing employee.             |
| `DELETE`        | `/api/employees/{id}`                | Delete an employee.                      |
| `GET`           | `/api/reports/employees/csv`         | Generate and download a CSV report.      |

## Technologies Used

- **Backend:** Spring Boot (Spring Data JPA, Spring Security, Swagger/OpenAPI)
- **Frontend:** Java Swing (MigLayout)
- **Database:** Oracle SQL (or H2 for testing)
- **Build Tool:** Maven

---

## Backlog Tasks

See [backlog.md](backlog.md) for a list of ongoing and completed tasks.

---

## Contributing

1. Fork the repository.
2. Create a new branch.
3. Commit your changes.
4. Open a pull request.