/*
 * =============================================================================
 * Step 05 — FriendRowMapper.java
 * =============================================================================
 *
 * Purpose:
 *     Convert one ResultSet row to one Friend object.
 *     Same as Step 04 — reads id, name, age, city.
 * =============================================================================
 */

package com.friends.community.repository;

import com.friends.community.model.Friend;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FriendRowMapper implements RowMapper<Friend> {

    @Override
    public Friend mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        String city = resultSet.getString("city");

        return new Friend(id, name, age, city);
    }
}
