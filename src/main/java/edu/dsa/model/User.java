package edu.dsa.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    private Set<String> friends;


    public User(String name) {
        this.name = name;
        this.friends = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<String> getFriends() {
        return friends;
    }

    public void addFriend(String friendName) {
        friends.add(friendName);
    }
}

