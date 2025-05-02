package edu.dsa.service;

import java.util.*;

public class DSU {
    private Map<String, String> parent = new HashMap<>();
    private Map<String, Integer> rank = new HashMap<>();

    public void addUser(String user) {
        parent.put(user, user);
        rank.put(user, 0);
    }

    public String find(String user) {
        if (!parent.containsKey(user)) {
            return null;
        }
        if (!parent.get(user).equals(user)) {
            parent.put(user, find(parent.get(user)));
        }
        return parent.get(user);
    }

    public void union(String user1, String user2) {
        String root1 = find(user1);
        String root2 = find(user2);
        if (root1 == null || root2 == null || root1.equals(root2)) {
            return;
        }
        int rank1 = rank.get(root1);
        int rank2 = rank.get(root2);
        if (rank1 > rank2) {
            parent.put(root2, root1);
        } else if (rank1 < rank2) {
            parent.put(root1, root2);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank1 + 1);
        }
    }

    public int getNumberOfCommunities() {
        Set<String> roots = new HashSet<>();
        for (String user : parent.keySet()) {
            roots.add(find(user));
        }
        return roots.size();
    }

    public Map<String, List<String>> getCommunities() {
        Map<String, List<String>> communities = new HashMap<>();
        for (String user : parent.keySet()) {
            String root = find(user);
            communities.computeIfAbsent(root, k -> new ArrayList<>()).add(user);
        }
        return communities;
    }
}
