# Friends Community — Step 06: Create Friend

This project continues directly from **Step 05**.

Step 05 added the find-by-ID endpoint. Step 06 adds a create endpoint that accepts JSON from the client, inserts a new row into MySQL, and returns the inserted record with its auto-generated primary key.

## Scope of Step 06

- `POST /friends/api/1.0/` — create a new friend
- Accepts JSON body with `name`, `age`, `city`
- Returns HTTP 201 (Created) with the new friend including its auto-generated `id`
- Keeps all existing endpoints unchanged

## Quick Start

1. **Import** into IntelliJ IDEA as a Maven project
2. **Ensure MySQL** is running and `friends_community` database exists
3. **Configure credentials**: env vars `DB_USERNAME=root`, `DB_PASSWORD=your_mysql_password`
4. **Run** `DemoApplication.main()`
5. **Test** create with curl:

```bash
curl -X POST http://localhost:8080/friends/api/1.0/ \
  -H "Content-Type: application/json" \
  -d '{"name":"Ananya","age":28,"city":"Pune"}'
```

## Expected Response

```json
{
  "id": 5,
  "name": "Ananya",
  "age": 28,
  "city": "Pune"
}
```
HTTP Status: **201 Created**

## Architecture Flow

```
Client sends POST with JSON body
     |
     v
FriendsRestController.createFriend(@RequestBody Friend friend)
     |  Jackson deserializes: {"name":"Ananya","age":28,"city":"Pune"}
     v
FriendsService.createFriend(friend)
     |
     v
FriendsRepository.createFriend(friend)
     |  
     |  jdbcTemplate.update() with INSERT
     |  KeyHolder captures auto-generated id
     v
MySQL: INSERT INTO friends (name, age, city) VALUES ('Ananya', 28, 'Pune')
     |
     v
Return Friend with id=5, name=Ananya, age=28, city=Pune
     |
     v
HTTP 201 + JSON response
```

## What Changed (from Step 05)

| File | Change |
|------|--------|
| `FriendsRepository.java` | Added `createFriend(Friend)` with INSERT and GeneratedKeyHolder |
| `FriendsService.java` | Added `createFriend(Friend)` |
| `FriendsRestController.java` | Added `@PostMapping("/")` endpoint with `@RequestBody` |

## Key Concepts

### @RequestBody

`@RequestBody` tells Spring MVC to deserialize the HTTP request body into a Java object. Jackson reads the JSON and uses setters to populate a `Friend` instance.

### jdbcTemplate.update()

`jdbcTemplate.update()` executes INSERT, UPDATE, or DELETE statements. It returns the number of affected rows. Unlike `query()`, it does not return result rows — only a row count.

### GeneratedKeyHolder / KeyHolder

When inserting a row with an AUTO_INCREMENT column, MySQL generates the ID. `KeyHolder` captures this generated value:

```java
KeyHolder keyHolder = new GeneratedKeyHolder();
jdbcTemplate.update(connection -> {
    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    // set parameters...
    return ps;
}, keyHolder);
long generatedId = keyHolder.getKey().longValue();
```

### HTTP 201 Created

HTTP 201 is the standard status code for successful resource creation. It signals to the client that a new resource was created, and the response body contains that resource.

## What Is Deliberately Not Included

- No PUT/update (Step 07)
- No DELETE (Step 08)
- No filters (Steps 09-10)
- No validation (Step 11)
- No structured error handling (Step 12)
