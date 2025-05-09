package edu.dsa.service;

import edu.dsa.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class SocialNetwork {
    private Map<String, User> users = new HashMap<>();
    private DSU dsu = new DSU();

    public void addUser(String name) {
        if (!users.containsKey(name)) {
            users.put(name, new User(name));
            dsu.addUser(name);
        }
    }

    public void addFriendship(String user1, String user2, int weight) {
        addUser(user1);
        addUser(user2);
        users.get(user1).addFriend(user2, weight);
        users.get(user2).addFriend(user1, weight);
        dsu.union(user1, user2);
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

    public List<String> findStrongestPath(String src, String dest) {
        if (!users.containsKey(src) || !users.containsKey(dest)) {
            return List.of("No path found");
        }
        return dijkstraPath(src, dest, true);
    }

    public List<String> findWeakestPath(String src, String dest) {
        if (!users.containsKey(src) || !users.containsKey(dest)) {
            return List.of("No path found");
        }
        return dijkstraPath(src, dest, false);
    }

    private List<String> dijkstraPath(String src, String dest, boolean isStrongest) {
        Map<String, Double> distance = new HashMap<>();
        distance.put(src, 0.0);
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>((a, b) -> Double.compare(
                distance.getOrDefault(a, Double.POSITIVE_INFINITY),
                distance.getOrDefault(b, Double.POSITIVE_INFINITY)
        ));
        queue.add(src);

        while (!queue.isEmpty()) {
            String u = queue.poll();
            double distU = distance.getOrDefault(u, Double.POSITIVE_INFINITY);
            if (distU == Double.POSITIVE_INFINITY) break;

            if (u.equals(dest)) break;

            for (String v : users.get(u).getFriends()) {
                int weight = getWeight(u, v);
                double cost = isStrongest ? 1.0 / weight : weight;
                double newDist = distU + cost;
                if (newDist < distance.getOrDefault(v, Double.POSITIVE_INFINITY)) {
                    distance.put(v, newDist);
                    parent.put(v, u);
                    queue.add(v);
                }
            }
        }

        if (!distance.containsKey(dest)) {
            return List.of("No path found");
        }

        List<String> path = new ArrayList<>();
        for (String at = dest; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private int getWeight(String user1, String user2) {
        return users.get(user1).getFriendshipWeight(user2);
    }

    public int getNumberOfCommunities() {
        return dsu.getNumberOfCommunities();
    }

    public Map<String, List<String>> getCommunities() {
        return dsu.getCommunities();
    }

    public List<String> getCommunity(String user) {
        String root = dsu.find(user);
        if (root == null) {
            return List.of();
        }
        Map<String, List<String>> communities = dsu.getCommunities();
        return communities.getOrDefault(root, List.of());
    }

    public List<String> getCommunitySummaries() {
        Map<String, List<String>> communities = dsu.getCommunities();
        List<String> summaries = new ArrayList<>();
        for (String root : communities.keySet()) {
            int size = communities.get(root).size();
            summaries.add("Community " + root + ": " + size + " members");
        }
        Collections.sort(summaries);
        return summaries;
    }

    // Degree Centrality: Returns the number of direct friends a user has
    public int getDegreeCentrality(String user) {
        if (!users.containsKey(user)) {
            return 0;
        }
        return users.get(user).getFriends().size();
    }

    // Finds the user with the highest degree centrality
    public String getMostConnectedUser() {
        String mostConnected = null;
        int maxDegree = -1;
        for (String user : users.keySet()) {
            int degree = getDegreeCentrality(user);
            if (degree > maxDegree) {
                maxDegree = degree;
                mostConnected = user;
            }
        }
        return mostConnected;
    }

    // Closeness Centrality: Measures how close a user is to all others
    public double getClosenessCentrality(String user) {
        if (!users.containsKey(user)) {
            return 0.0;
        }
        int sumDistances = 0;
        int count = 0;
        for (String other : users.keySet()) {
            if (!other.equals(user)) {
                List<String> path = shortestPath(user, other);
                if (!path.get(0).equals("No path found")) {
                    sumDistances += path.size() - 1; // path length
                    count++;
                }
            }
        }
        if (count == 0) {
            return 0.0;
        }
        return (double) count / sumDistances; // Normalized closeness
    }

    // Finds the user with the highest closeness centrality
    public String getUserWithHighestCloseness() {
        String bestUser = null;
        double maxCloseness = -1.0;
        for (String user : users.keySet()) {
            double closeness = getClosenessCentrality(user);
            if (closeness > maxCloseness) {
                maxCloseness = closeness;
                bestUser = user;
            }
        }
        return bestUser;
    }
}