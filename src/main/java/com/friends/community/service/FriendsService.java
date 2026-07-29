/*
 * =============================================================================
 * Step 06 — FriendsService.java  (CHANGED — added createFriend)
 * =============================================================================
 *
 * Purpose:
 *     Business logic layer for Friend operations.
 *
 *     Changes from Step 05:
 *       - NEW method: createFriend(Friend friend)
 *         Delegates to the repository's createFriend and returns the
 *         created Friend with the auto-generated id.
 * =============================================================================
 */

package com.friends.community.service;

import com.friends.community.model.Friend;
import com.friends.community.repository.FriendsRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class FriendsService {

    private final FriendsRepository friendsRepository;

    public FriendsService(FriendsRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }

    /** Returns all friends. Unchanged. */
    public ArrayList<Friend> getAllFriendsS() {
        return friendsRepository.getAllFriendsR();
    }

    /** Finds one friend by ID. Unchanged. */
    public Friend findById(Long id) {
        return friendsRepository.findById(id);
    }

    /**
     * Creates a new friend.
     *
     * @param friend the Friend data to insert
     * @return the Friend with its auto-generated database ID
     */
    public Friend createFriend(Friend friend)  {
        return friendsRepository.createFriend(friend);
    }
}
