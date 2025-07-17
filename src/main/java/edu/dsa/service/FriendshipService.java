package edu.dsa.service;

import java.util.List;

public interface FriendshipService {
    void addFriendship(String user1, String user2, int weight);
    int getFriendshipWeight(String user1, String user2);
    List<String> getMutualFriends(String user1, String user2);
}
