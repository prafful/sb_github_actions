# Step 06 — Detailed Walkthrough: Create Friend

## Overview

Step 06 introduces the first database write operation: inserting a new friend. This shows how to use `jdbcTemplate.update()` with a `PreparedStatement` and how to retrieve the auto-generated primary key.

## What This Step Does

1. Adds `createFriend(Friend friend)` to `FriendsRepository` with `INSERT INTO` and `GeneratedKeyHolder`
2. Adds `createFriend(Friend friend)` to `FriendsService`
3. Adds `POST /friends/api/1.0/` to `FriendsRestController` with `@RequestBody` and `@PostMapping`
4. Returns HTTP 201 (Created) with the new friend's auto-generated ID

## File-by-File Explanation

### FriendsRepository.java — createFriend(Friend friend)

```java
public Friend createFriend(Friend friend) {
    String sql = """
            INSERT INTO friends (name, age, city)
            VALUES (?, ?, ?)
            """;

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, friend.getName());
        ps.setInt(2, friend.getAge());
        ps.setString(3, friend.getCity());
        return ps;
    }, keyHolder);

    long generatedId = keyHolder.getKey().longValue();
    friend.setId(generatedId);
    return friend;
}
```

**Key details:**

**INSERT without id:**
The SQL inserts `name`, `age`, `city` but NOT `id`. The `id` column is `AUTO_INCREMENT` — MySQL assigns the next available integer automatically.

**PreparedStatementCreator:**
Instead of a simple SQL string, we pass a lambda that creates a `PreparedStatement` with `Statement.RETURN_GENERATED_KEYS` flag. This tells MySQL to return the auto-generated ID.

**KeyHolder:**
After `update()` executes, `keyHolder.getKey().longValue()` retrieves the auto-generated ID. We set this on the `Friend` object so the response includes it.

### FriendsRestController.java — create endpoint

```java
@PostMapping("/")
public ResponseEntity<Friend> createFriend(@RequestBody Friend friend) {
    Friend createdFriend = friendService.createFriend(friend);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdFriend);
}
```

**@PostMapping("/"):**
Maps POST requests to the base path `/friends/api/1.0/`.

**@RequestBody:**
Jackson reads the JSON request body and populates a `Friend` object. The `id` field will be `null` because the client does not send it.

**201 Created Status:**
`ResponseEntity.status(HttpStatus.CREATED)` returns HTTP 201, the standard status code for resource creation.

### Example Request and Response

**Request:**
```json
POST /friends/api/1.0/
Content-Type: application/json

{
  "name": "Vikram",
  "age": 32,
  "city": "Bangalore"
}
```

**Response:**
```json
HTTP 201 Created

{
  "id": 5,
  "name": "Vikram",
  "age": 32,
  "city": "Bangalore"
}
```

### All Other Files

Unchanged from Step 05:
- `Friend.java`, `FriendRowMapper.java` — no changes
- `friends` table, `schema.sql`, `data.sql` — unchanged
- `application.properties` — unchanged
- Startup checks — unchanged
- `findById` and `getAllFriends` — unchanged

## Key Concepts

### jdbcTemplate.update()

| Aspect | Description |
|--------|-------------|
| Purpose | Execute INSERT, UPDATE, DELETE |
| Returns | Number of affected rows (int) |
| Parameters | SQL string + parameter values (varargs) OR PreparedStatementCreator |
| Exception | Throws DataAccessException on failure |

### PreparedStatementCreator

A functional interface with one method:
```java
PreparedStatement createPreparedStatement(Connection con) throws SQLException
```

Using a lambda gives us access to the underlying `Connection` object so we can:
- Set `Statement.RETURN_GENERATED_KEYS`
- Configure statement-level settings
- Get access to database metadata

### KeyHolder

```java
KeyHolder keyHolder = new GeneratedKeyHolder();
jdbcTemplate.update(psCreator, keyHolder);
Number key = keyHolder.getKey();  // returns the generated key
long id = key.longValue();         // convert to Long
```

Only works with `PreparedStatement` created with `RETURN_GENERATED_KEYS`. The retrieved key value depends on the database. For MySQL with `BIGINT AUTO_INCREMENT`, it returns a `Long`.

## Request Body Format

The `@RequestBody` annotation expects valid JSON. The JSON must match the `Friend` class structure:

```json
{
  "name": "Ananya",    // required, String
  "age": 28,           // required, Integer
  "city": "Pune"       // required, String
}
```

The `id` field should be omitted. If included, it is ignored by the INSERT statement.

## Verification Steps

1. Call `GET /friends/api/1.0/all` — should return 4 friends
2. Call `POST /friends/api/1.0/` with a new friend's JSON
3. Call `GET /friends/api/1.0/all` again — should now show 5 friends, including the new one
4. Call `GET /friends/api/1.0/5` — should return the newly created friend

---

## What Changed (from Step 05)

| File | Change |
|------|--------|
| `FriendsRepository.java` | Added `createFriend(Friend)` with INSERT, PreparedStatementCreator, and GeneratedKeyHolder |
| `FriendsService.java` | Added `createFriend(Friend)` |
| `FriendsRestController.java` | Added `@PostMapping("/")` with `@RequestBody` returning HTTP 201 |
| `Friend.java` | No change |
| `FriendRowMapper.java` | No change |
| `schema.sql` / `data.sql` | No change |
| `application.properties` | No change |
