/*
 * =============================================================================
 * Step 05 — Friend.java
 * =============================================================================
 *
 * Purpose:
 *     Friend POJO with id field. Same as Step 04.
 *     The id field is essential for the find-by-ID endpoint added in Step 05.
 * =============================================================================
 */

package com.friends.community.model;

public class Friend {

    private Long id;
    private String name;
    private Integer age;
    private String city;

    /**
     * Creates a Friend with all four fields.
     *
     * @param id    database primary key
     * @param name  friend's name
     * @param age   friend's age
     * @param city  friend's city
     */
    public Friend(Long id, String name, Integer age, String city) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
