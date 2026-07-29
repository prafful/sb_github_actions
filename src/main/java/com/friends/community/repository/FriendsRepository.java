/*
 * =============================================================================
 * Step 06 — FriendsRepository.java  (CHANGED — added createFriend)
 * =============================================================================
 *
 * Purpose:
 *     Data access for Friend records.
 *
 *     Changes from Step 05:
 *       - NEW method: createFriend(Friend friend)
 *         Uses jdbcTemplate.update() with INSERT statement.
 *         Retrieves the auto-generated primary key using GeneratedKeyHolder.
 *         Returns a Friend with the assigned id.
 * =============================================================================
 */

package com.friends.community.repository;

import com.friends.community.model.Friend;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final FriendRowMapper friendRowMapper;

    public FriendsRepository(
            JdbcTemplate jdbcTemplate,
            FriendRowMapper friendRowMapper) {

        this.jdbcTemplate = jdbcTemplate;
        this.friendRowMapper = friendRowMapper;
    }

    /** Returns all friends. Unchanged from Step 04/05. */
    public ArrayList<Friend> getAllFriendsR() {
        String sql = """
                SELECT id, name, age, city
                FROM friends
                ORDER BY id
                """;
        List<Friend> databaseFriends = jdbcTemplate.query(sql, friendRowMapper);
        return new ArrayList<>(databaseFriends);
    }

    /** Finds one friend by ID. Unchanged from Step 05. */
    public Friend findById(Long id) {
        String sql = """
                SELECT id, name, age, city
                FROM friends
                WHERE id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, friendRowMapper, id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Inserts a new friend into the database and returns the object
     * with the auto-generated primary key.
     *
     * This is the NEW method added in Step 06.
     *
     * jdbcTemplate.update() is used for INSERT, UPDATE, and DELETE statements.
     * It returns the number of affected rows (should be 1 for a successful insert).
     *
     * GeneratedKeyHolder captures the AUTO_INCREMENT value that MySQL assigns
     * when the row is inserted without specifying an id.
     *
     * @param friend the Friend to insert (without an id)
     * @return the same Friend with its database-generated id populated
     */
    public Friend createFriend(Friend friend) {

        /*
         * SQL INSERT statement.
         * We insert all columns except 'id' because id is AUTO_INCREMENT.
         * MySQL will assign the next available integer automatically.
         *
         * Using PreparedStatement with RETURN_GENERATED_KEYS tells MySQL
         * to return the auto-generated id value.
         */
        String sql = """
                INSERT INTO friends (name, age, city)
                VALUES (?, ?, ?)
                """;

        /*
         * KeyHolder stores the auto-generated key returned by the database.
         * GeneratedKeyHolder is a concrete implementation of KeyHolder.
         */
        KeyHolder keyHolder = new GeneratedKeyHolder();

        /*
         * jdbcTemplate.update(PreparedStatementCreator, KeyHolder):
         *
         * The first argument is a lambda that creates a PreparedStatement.
         *
         * PreparedStatement is a JDBC interface that represents a
         * pre-compiled SQL statement. Unlike a regular Statement:
         *   - It accepts ? placeholders for input values
         *   - It prevents SQL injection (values are bound, not concatenated)
         *   - MySQL can cache and reuse the compiled execution plan
         *
         * We use Statement.RETURN_GENERATED_KEYS so MySQL returns
         * the auto-generated id after executing the INSERT.
         *
         * The PreparedStatement parameters (? placeholders) are set
         * using typed setXxx() methods. Each method:
         *   - Converts the Java value to the appropriate SQL type
         *   - Escapes special characters if needed (e.g., quotes in strings)
         *   - Prevents SQL injection by separating data from SQL structure
         *
         * Parameter index positions (1-based):
         *   ps.setString(1, friend.getName())  — first  ? → name column
         *   ps.setInt(2, friend.getAge())       — second ? → age column
         *   ps.setString(3, friend.getCity())   — third  ? → city column
         */
        jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, friend.getName());
            ps.setInt(2, friend.getAge());
            ps.setString(3, friend.getCity());
            return ps;
        }, keyHolder);

        /*
        function myFunction(){
            //implementation
        }
        myFunction();
        var myFunction = ()=>{

        }

         */

        /*
         * Extract the auto-generated ID from the KeyHolder.
         * keyHolder.getKey() returns a Number (the database-assigned id).
         * We convert it to Long and set it on the Friend object.
         *
         * The Friend now has the database-generated primary key.
         */
        long generatedId = keyHolder.getKey().longValue();
        friend.setId(generatedId);

        /*
         * Return the saved Friend with the id populated.
         * The response to the HTTP client will include this id.
         */
        return friend;
    }
}
