/*
 * =============================================================================
 * Step 05 — FriendTableCheck.java
 * =============================================================================
 *
 * Purpose:
 *     Verify the friends table has seed data. Same as Steps 02-04.
 * =============================================================================
 */

package com.friends.community.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FriendTableCheck implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public FriendTableCheck(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        Integer friendCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM friends", Integer.class
        );
        System.out.println();
        System.out.println("========== FRIEND TABLE CHECK ==========");
        System.out.println("Table checked: friends");
        System.out.println("Rows available: " + friendCount);
        System.out.println("schema.sql and data.sql were executed.");
        System.out.println("========================================");
        System.out.println();
    }
}
