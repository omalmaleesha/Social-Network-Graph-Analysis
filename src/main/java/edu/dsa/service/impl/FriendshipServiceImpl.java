package edu.dsa.service.impl;

import edu.dsa.service.DSU;
import edu.dsa.service.FriendshipService;
import edu.dsa.service.impl.UserServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Getter
public class FriendshipServiceImpl implements FriendshipService {
    private final UserServiceImpl userService;
    private final DSU dsu = new DSU();

    @Override
    public void addFriendship(String user1, String user2, int weight) {
        userService.addUser(user1);
        userService.addUser(user2);
        userService.getUsers().get(user1).addFriend(user2, weight);
        userService.getUsers().get(user2).addFriend(user1, weight);
        dsu.addUser(user1);
        dsu.addUser(user2);
        dsu.union(user1, user2);
    }

    @Override
    public int getFriendshipWeight(String user1, String user2) {
        if (!userService.userExists(user1) || !userService.userExists(user2)) {
            return 0;
        }
        return userService.getUsers().get(user1).getFriendshipWeight(user2);
    }

    @Override
    public List<String> getMutualFriends(String user1, String user2) {
        Set<String> set1 = userService.getUsers().get(user1).getFriends();
        Set<String> set2 = userService.getUsers().get(user2).getFriends();

        List<String> mutual = new ArrayList<>();
        for (String friend : set1) {
            if (set2.contains(friend)) {
                mutual.add(friend);
            }
        }
        return mutual;
    }


}
