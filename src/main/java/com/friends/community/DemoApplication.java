/*
 * =============================================================================
 * Step 05 — DemoApplication.java
 * =============================================================================
 *
 * Purpose:
 *     Main entry point. Same as Steps 01-04.
 *     Step 05 adds a find-by-ID endpoint; the startup class is unchanged.
 * =============================================================================
 */

package com.friends.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        System.out.println("Before RUN");
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("After RUN");
    }
}
