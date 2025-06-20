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
        if (!users.containsKey(user)) {
            return new ArrayList<>();
        }
        Set<String> directFriends = users.get(user).getFriends();
        Map<String, Integer> suggestionCount = new HashMap<>();
        for (String friend : directFriends) {
            for (String fof : users.get(friend).getFriends()) {
                if (!fof.equals(user) && !directFriends.contains(fof)) {
                    suggestionCount.put(fof, suggestionCount.getOrDefault(fof, 0) + 1);
                }
            }
        }
        int sizeU = directFriends.size();
        return suggestionCount.entrySet().stream()
                .sorted((a, b) -> {
                    String v1 = a.getKey();
                    int count1 = a.getValue();
                    int sizeV1 = users.get(v1).getFriends().size();
                    double J1 = (double) count1 / (sizeU + sizeV1 - count1);
                    String v2 = b.getKey();
                    int count2 = b.getValue();
                    int sizeV2 = users.get(v2).getFriends().size();
                    double J2 = (double) count2 / (sizeU + sizeV2 - count2);
                    return Double.compare(J2, J1); // descending order
                })
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

        if (!parent.containsKey(dest)) {
            List<String> noPath = new ArrayList<>();
            noPath.add("No path found");
            return noPath;
        }

        List<String> path = new ArrayList<>();
        for (String at = dest; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public List<String> findStrongestPath(String src, String dest) {
        if (!users.containsKey(src) || !users.containsKey(dest)) {
            List<String> noPath = new ArrayList<>();
            noPath.add("No path found");
            return noPath;
        }
        return dijkstraPath(src, dest, true);
    }

    public List<String> findWeakestPath(String src, String dest) {
        if (!users.containsKey(src) || !users.containsKey(dest)) {
            List<String> noPath = new ArrayList<>();
            noPath.add("No path found");
            return noPath;
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
            List<String> noPath = new ArrayList<>();
            noPath.add("No path found");
            return noPath;
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
            return new ArrayList<>();
        }
        Map<String, List<String>> communities = dsu.getCommunities();
        return communities.getOrDefault(root, new ArrayList<>());
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

    public int getDegreeCentrality(String user) {
        if (!users.containsKey(user)) {
            return 0;
        }
        return users.get(user).getFriends().size();
    }

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

    private Map<String, Double> computePageRank() {
        int N = users.size();
        if (N == 0) {
            return new HashMap<>();
        }
        double d = 0.85; // Damping factor
        double threshold = 0.0001; // Convergence threshold
        Map<String, Double> currentPR = new HashMap<>();
        // Initialize PageRank for each user
        for (String user : users.keySet()) {
            currentPR.put(user, 1.0 / N);
        }
        boolean converged = false;
        while (!converged) {
            Map<String, Double> nextPR = new HashMap<>();
            double maxChange = 0.0;
            for (String u : users.keySet()) {
                double sum = 0.0;
                Set<String> friends = users.get(u).getFriends();
                // Sum contributions from friends
                for (String v : friends) {
                    int degreeV = users.get(v).getFriends().size();
                    if (degreeV > 0) {
                        sum += currentPR.get(v) / degreeV;
                    }
                }
                double newPR = (1 - d) / N + d * sum;
                nextPR.put(u, newPR);
                double change = Math.abs(newPR - currentPR.get(u));
                if (change > maxChange) {
                    maxChange = change;
                }
            }
            currentPR = nextPR;
            if (maxChange < threshold) {
                converged = true;
            }
        }
        return currentPR;
    }

    public double getPageRank(String user) {
        if (!users.containsKey(user)) {
            return 0.0;
        }
        Map<String, Double> pr = computePageRank();
        return pr.get(user);
    }

    public String getMostInfluentialUser() {
        Map<String, Double> pr = computePageRank();
        if (pr.isEmpty()) {
            return null;
        }
        String mostInfluential = null;
        double maxPR = -1.0;
        for (Map.Entry<String, Double> entry : pr.entrySet()) {
            if (entry.getValue() > maxPR) {
                maxPR = entry.getValue();
                mostInfluential = entry.getKey();
            }
        }
        return mostInfluential;
    }

    public double getNetworkDensity() {
        int n = users.size();
        if (n < 2) return 0.0;
        int edges = 0;
        for (String user : users.keySet()) {
            edges += users.get(user).getFriends().size();
        }
        edges /= 2; // Each edge counted twice
        return (2.0 * edges) / (n * (n - 1));
    }

    public double getBetweennessCentrality(String user) {
        if (!users.containsKey(user)) return 0.0;
        double betweenness = 0.0;
        for (String s : users.keySet()) {
            for (String t : users.keySet()) {
                if (!s.equals(t) && !s.equals(user) && !t.equals(user)) {
                    // Compute shortest paths and count those passing through 'user'
                    // (Implementation requires tracking all shortest paths)
                }
            }
        }
        return betweenness / ((users.size() - 1) * (users.size() - 2) / 2);
    }

    public String getHighestBetweennessUser() {
        String maxUser = null;
        double maxBetweenness = -1.0;
        for (String user : users.keySet()) {
            double betweenness = getBetweennessCentrality(user);
            if (betweenness > maxBetweenness) {
                maxBetweenness = betweenness;
                maxUser = user;
            }
        }
        return maxUser;
    }

    public double getClusteringCoefficient(String user) {
        if (!users.containsKey(user)) return 0.0;
        Set<String> friends = users.get(user).getFriends();
        int k = friends.size();
        if (k < 2) return 0.0;
        int edges = 0;
        for (String f1 : friends) {
            for (String f2 : friends) {
                if (!f1.equals(f2) && users.get(f1).getFriends().contains(f2)) {
                    edges++;
                }
            }
        }
        edges /= 2; // Each edge counted twice
        return (2.0 * edges) / (k * (k - 1));
    }

    public double getAverageClusteringCoefficient() {
        double sum = 0.0;
        int count = 0;
        for (String user : users.keySet()) {
            double cc = getClusteringCoefficient(user);
            if (cc > 0) { // Only count users with at least 2 friends
                sum += cc;
                count++;
            }
        }
        return count > 0 ? sum / count : 0.0;
    }

    public List<String> predictNewFriends(String user, int topN) {
        Map<String, Integer> scores = new HashMap<>();
        Set<String> friends = users.get(user).getFriends();
        for (String other : users.keySet()) {
            if (!other.equals(user) && !friends.contains(other)) {
                Set<String> otherFriends = users.get(other).getFriends();
                int common = 0;
                for (String f : friends) {
                    if (otherFriends.contains(f)) common++;
                }
                scores.put(other, common);
            }
        }
        return scores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    public Set<String> findInfluencers(int k) {
        Set<String> influencers = new HashSet<>();
        for (int i = 0; i < k; i++) {
            String bestUser = null;
            double maxMarginalGain = -1;
            for (String user : users.keySet()) {
                if (!influencers.contains(user)) {
                    double gain = estimateMarginalGain(influencers, user);
                    if (gain > maxMarginalGain) {
                        maxMarginalGain = gain;
                        bestUser = user;
                    }
                }
            }
            if (bestUser != null) influencers.add(bestUser);
        }
        return influencers;
    }

    private double estimateMarginalGain(Set<String> currentSet, String user) {
        // Placeholder: Use degree as a simple heuristic
        return users.get(user).getFriends().size();
    }

    public int getTriangleCount(String user) {
        if (!users.containsKey(user)) return 0;
        Set<String> friends = users.get(user).getFriends();
        int triangles = 0;
        for (String f1 : friends) {
            for (String f2 : friends) {
                if (!f1.equals(f2) && users.get(f1).getFriends().contains(f2)) {
                    triangles++;
                }
            }
        }
        return triangles / 2; // Each triangle counted twice
    }

    public int getNetworkTriangleCount() {
        int total = 0;
        for (String user : users.keySet()) {
            total += getTriangleCount(user);
        }
        return total / 3; // Each triangle counted by all three users
    }

    // Additional methods for REST API support
    public List<String> getAllUsers() {
        return new ArrayList<>(users.keySet());
    }

    public Set<String> getFriends(String user) {
        if (!users.containsKey(user)) {
            return new HashSet<>();
        }
        return users.get(user).getFriends();
    }

    public int getFriendshipWeight(String user1, String user2) {
        if (!users.containsKey(user1) || !users.containsKey(user2)) {
            return 0;
        }
        return users.get(user1).getFriendshipWeight(user2);
    }
}