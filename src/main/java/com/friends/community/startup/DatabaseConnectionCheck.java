/*
 * =============================================================================
 * Step 05 — DatabaseConnectionCheck.java
 * =============================================================================
 *
 * Purpose:
 *     Verify MySQL connection at startup. Same as Steps 01-04.
 * =============================================================================
 */

package com.friends.community.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionCheck implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseConnectionCheck(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        System.out.println();
        System.out.println("========== DATABASE CONNECTION CHECK ==========");
        System.out.println("Result returned by MySQL: " + result);
        System.out.println("JdbcTemplate database connection is working.");
        System.out.println("================================================");
        System.out.println();
    }
}
