# Person Management API

Clean, modular Spring Boot REST API for managing people with CRUD.

Badges
- Build: [![CI](https://github.com/devopsFreelan/person-management-api/actions/workflows/ci.yml/badge.svg)](https://github.com/devopsFreelan/person-management-api/actions/workflows/ci.yml)

Prerequisites
- Java 17
- Maven 3.6+

Quickstart
1. Run the app locally:

```bash
mvn spring-boot:run
```

2. Run tests:

```bash
mvn test
```

Project structure

- `src/main/java/com/example/personmanagement` — application sources
	- `controller` — REST controllers
	- `dto` — request/response DTOs
	- `model` — JPA entities
	- `repository` — Spring Data JPA repositories
	- `service` — business logic
	- `exception` — custom exceptions and global handler
- `src/test/java` — unit and controller tests
- `docs/` — sample requests and test outputs

Endpoints (examples)

- List persons

```bash
curl http://localhost:8080/api/persons
```

- Create person

```bash
curl -X POST http://localhost:8080/api/persons \
	-H "Content-Type: application/json" \
	-d '{"firstName":"Jane","lastName":"Doe","email":"jane@example.com","age":25}'
```

Returns `201 Created` and the created resource in the response body.

Error handling & validation

- The API validates incoming JSON using Jakarta Bean Validation; invalid input returns `400 Bad Request` and a helpful JSON message.
- Requests for non-existent resources return `404 Not Found` with an explanatory message.

H2 Console

Visit http://localhost:8080/h2-console with JDBC URL `jdbc:h2:mem:persondb`.

Docs and screenshots

- `docs/curl-create.md` — curl example request & response
- `docs/postman-create.png` — placeholder Postman screenshot (replace with real screenshot)
- `docs/test-output.txt` — last `mvn test` run summary

Contributing

- Commit messages: use concise, conventional commits like `feat: add person controller`, `test: add service tests`.
# person-management-api
PI REST de gerenciamento de pessoas — desafio técnico
