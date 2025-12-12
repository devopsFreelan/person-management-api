# Create Person (curl example)

Request:

```bash
curl -X POST http://localhost:8080/api/persons \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Jane","lastName":"Doe","email":"jane@example.com","age":25}'
```

Expected response (HTTP 201):

```json
{
  "id": 1,
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane@example.com",
  "age": 25
}
```

Use the `Location` header to retrieve the created resource.
