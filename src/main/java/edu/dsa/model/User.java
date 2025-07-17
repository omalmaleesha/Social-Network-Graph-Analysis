package edu.dsa.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class User {
    @NonNull
    private final String name;
    private final Map<String, Integer> friendsWithWeights = new HashMap<>();

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