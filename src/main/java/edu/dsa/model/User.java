package edu.dsa.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class User {
    private String name;
    private Map<String, Integer> friendsWithWeights;

    public User(String name) {
        this.name = name;
        this.friendsWithWeights = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Set<String> getFriends() {
        return friendsWithWeights.keySet();
    }

    public int getFriendshipWeight(String friend) {
        return friendsWithWeights.getOrDefault(friend, 0);
    }

    public void addFriend(String friendName, int weight) {
        friendsWithWeights.put(friendName, weight);
    }
}