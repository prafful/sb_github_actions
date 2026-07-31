/*
 * =============================================================================
 * Step 06 — FriendsRestController.java  (CHANGED — added create endpoint)
 * =============================================================================
 *
 * Purpose:
 *     REST controller handling HTTP requests for Friend operations.
 *
 *     Changes from Step 05:
 *       - NEW endpoint: POST /friends/api/1.0/
 *         Uses @PostMapping and @RequestBody to accept JSON input.
 *         Returns HTTP 201 (Created) with the new Friend (including its id).
 *
 *     Step 06 now exposes three endpoints:
 *       1. GET  /friends/api/1.0/all     — all friends
 *       2. GET  /friends/api/1.0/{id}    — one friend by ID
 *       3. POST /friends/api/1.0/        — create a friend  (NEW)
 * =============================================================================
 */

package com.friends.community.api;

import com.friends.community.model.Friend;
import com.friends.community.service.FriendsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/friends/api/1.0")
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500"
})
public class FriendsRestController {

    private final FriendsService friendService;

    public FriendsRestController(FriendsService friendService) {
        this.friendService = friendService;
    }

    /** GET /friends/api/1.0/all — returns all friends. */
    @GetMapping("/all")
    public ArrayList<Friend> getAllFriends() {
        System.out.println("Incoming GET request for /friends/api/1.0/all");
        return friendService.getAllFriendsS();
    }

    /** GET /friends/api/1.0/{id} — returns one friend or 404. */
    @GetMapping("/{id}")
    public ResponseEntity<Friend> findById(@PathVariable Long id) {
        System.out.println("Incoming GET request for /friends/api/1.0/" + id);
        Friend friend = friendService.findById(id);
        if (friend == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(friend);
    }

    /*
     * POST /friends/api/1.0/
     *
     * NEW endpoint added in Step 06.
     *
     * @RequestBody tells Spring MVC to deserialize the HTTP request body
     * (which is JSON) into a Friend object. Jackson reads the JSON and
     * calls the setters (setName, setAge, setCity) on a new Friend instance.
     *
     * Example POST body:
     *   {
     *     "name": "Ananya",
     *     "age": 28,
     *     "city": "Pune"
     *   }
     *
     * The client does NOT send an 'id' — MySQL auto-generates it.
     * If the client does send an 'id', it is ignored by the INSERT query.
     *
     * @PostMapping("/") maps HTTP POST requests to this method.
     *
     * We return ResponseEntity.status(HttpStatus.CREATED) which sends
     * HTTP 201 (Created) — the standard status code for successful creates.
     * The body contains the Friend with its auto-generated 'id'.
     */
    @PostMapping("/")
    public ResponseEntity<Friend> createFriend(@RequestBody Friend friend) {

        System.out.println("Incoming POST request to create friend: " + friend.getName());

        /*
         * At this point, friend.getId() is null because the client
         * does not send an id. After createFriend() executes, the
         * returned friend will have the id assigned by MySQL.
         */
        Friend createdFriend = friendService.createFriend(friend);

        System.out.println("Created friend with id=" + createdFriend.getId());

        /*
         * HTTP 201 Created with the new friend in the response body.
         * The response includes the id so the client can use it
         * immediately (e.g., for subsequent operations).
         */
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFriend);
    }
}
