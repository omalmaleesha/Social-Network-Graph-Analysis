package edu.dsa.service;

import edu.dsa.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class SocialNetwork {
    private Map<String, User> users = new HashMap<>();

    public void addUser(String name) {
        users.putIfAbsent(name, new User(name));
    }

    public void addFriendship(String user1, String user2) {
        addUser(user1);
        addUser(user2);
        users.get(user1).addFriend(user2);
        users.get(user2).addFriend(user1);
    }

    public List<String> getMutualFriends(String user1, String user2) {
        Set<String> set1 = users.get(user1).getFriends();
        Set<String> set2 = users.get(user2).getFriends();

        List<String> mutual = new ArrayList<>();
        for (String friend : set1) {
            if (set2.contains(friend)) {
                mutual.add(friend);
            }
        }
        return mutual;
    }

    public List<String> suggestFriends(String user) {
        Set<String> directFriends = users.get(user).getFriends();
        Map<String, Integer> suggestionCount = new HashMap<>();

        for (String friend : directFriends) {
            for (String fof : users.get(friend).getFriends()) {
                if (!fof.equals(user) && !directFriends.contains(fof)) {
                    suggestionCount.put(fof, suggestionCount.getOrDefault(fof, 0) + 1);
                }
            }
        }

        return suggestionCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> shortestPath(String src, String dest) {
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.offer(src);
        visited.add(src);
        parent.put(src, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(dest)) break;

            for (String neighbor : users.get(current).getFriends()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                }
            }
        }

        if (!parent.containsKey(dest)) return List.of("No path found");

        List<String> path = new ArrayList<>();
        for (String at = dest; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}

