# Spring Boot CRUD Example

A Spring Boot CRUD API project using MySQL and JPA/Hibernate with these domains:

- Employee
- Department
- Address
- Project
- Task

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

## Run Locally

1. Start MySQL.
2. Ensure credentials in `src/main/resources/application.properties` are correct:
   - `spring.datasource.url=jdbc:mysql://localhost:3306/crud_example_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC`
   - `spring.datasource.username=root`
   - `spring.datasource.password=root`
3. Run:

```bash
mvn clean install
mvn spring-boot:run
```

4. API base URL:

```text
http://localhost:8080
```

## Entity Relationship Order (Important for Testing)

Create data in this order to avoid relationship issues:

1. Department
2. Address (optional, because Employee can cascade-create Address)
3. Project
4. Employee (references department/project/address)
5. Task (references employee)

## Postman Collection

A ready-to-import Postman collection is included at:

- `crudExample.postman_collection.json`

### Import Steps

1. Open Postman.
2. Click **Import**.
3. Choose `crudExample.postman_collection.json`.
4. Set collection variable `baseUrl` to:
   - `http://localhost:8080`
5. Run requests folder-by-folder in this order:
   - Department
   - Address
   - Project
   - Employee
   - Task

## API Checklist (Quick)

### Employee

- `POST /employees`
- `GET /employees`
- `GET /employees/{id}`
- `PUT /employees/{id}`
- `DELETE /employees/{id}`
- `GET /employees/search?name=...`
- `GET /employees/high-salary?amount=...`

### Project

- `POST /projects`
- `GET /projects`
- `GET /projects/{id}`
- `GET /projects/search?name=...`
- `DELETE /projects/{id}`

### Task

- `POST /tasks`
- `GET /tasks`
- `GET /tasks/{id}`
- `GET /tasks/employee/{empId}`
- `DELETE /tasks/{id}`

### Department

- `POST /departments`
- `GET /departments`
- `GET /departments/{id}`
- `GET /departments/search?name=...`
- `DELETE /departments/{id}`

### Address

- `POST /addresses`
- `GET /addresses`
- `GET /addresses/{id}`
- `DELETE /addresses/{id}`

## Suggested MySQL Test Data

Use API requests to create:

- Departments: `IT`, `HR`, `Finance`
- Projects: `EmployeePortal`, `BankingApp`
- Employees:
  - `Alice` (salary `75000`, department `IT`)
  - `Bob` (salary `45000`, department `HR`)
- Tasks:
  - `Create API Docs` for Alice
  - `Fix Login Bug` for Bob
- Addresses:
  - `Hyderabad, TS`
  - `Bangalore, KA`

## Common Bugs to Watch During Testing

- Inconsistent not-found handling:
  - Employee APIs return structured `404`.
  - Other APIs often return `null`/`200` when record is missing.
- Missing input validations:
  - Empty names, negative salary, null fields may still persist.
- Relationship delete issues:
  - Deleting Department/Project/Address while linked to Employee may fail or behave unexpectedly.
- Serialization/circular reference risk in nested entities.
- Update side effects:
  - `PUT /employees/{id}` may overwrite relationships if payload is partial.

## Push This Project to GitHub

### 1) Initialize git (if not already)

```bash
git init
git add .
git commit -m "Initial commit: Spring Boot CRUD API with Postman guide"
```

### 2) Create a new GitHub repository

1. Go to [https://github.com/new](https://github.com/new)
2. Create a repository (for example: `crudExample`)
3. Do **not** add README there (you already have one locally)

### 3) Connect local repo to GitHub and push

Replace `<your-username>`:

```bash
git branch -M main
git remote add origin https://github.com/<your-username>/crudExample.git
git push -u origin main
```

### 4) Future updates

```bash
git add .
git commit -m "Describe your changes"
git push
```

## Optional Next Improvements

- Add Bean Validation annotations (`@NotBlank`, `@Positive`, etc.).
- Add API documentation via Swagger/OpenAPI.
- Add integration tests for all CRUD endpoints.
