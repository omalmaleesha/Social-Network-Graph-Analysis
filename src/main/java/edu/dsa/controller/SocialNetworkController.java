package edu.dsa.controller;

import edu.dsa.service.SocialNetworkFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;

import java.util.*;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class SocialNetworkController {
    private final SocialNetworkFacade socialNetwork;
    
    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addUser(@RequestBody Map<String, String> request) {
        String userName = request.get("name");
        if (userName == null || userName.trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User name is required");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        socialNetwork.addUser(userName);
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "User added successfully");
        successResponse.put("user", userName);
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/friendships")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addFriendship(@RequestBody Map<String, Object> request) {
        String user1 = (String) request.get("user1");
        String user2 = (String) request.get("user2");
        Integer weight = (Integer) request.get("weight");

        if (user1 == null || user2 == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Both users are required");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        if (weight == null) {
            weight = 5;
        }

        socialNetwork.addFriendship(user1, user2, weight);
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Friendship created successfully");
        return ResponseEntity.ok(successResponse);
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<List<String>> getAllUsers() {
        return ResponseEntity.ok(socialNetwork.getAllUsers());
    }
    
    @GetMapping("/graph")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getGraphData() {
        Map<String, Object> graphData = new HashMap<>();

        List<Map<String, String>> nodes = new ArrayList<>();
        for (String user : socialNetwork.getAllUsers()) {
            Map<String, String> node = new HashMap<>();
            node.put("id", user);
            nodes.add(node);
        }

        List<Map<String, Object>> links = new ArrayList<>();
        Set<String> processedPairs = new HashSet<>();

        for (String user : socialNetwork.getAllUsers()) {
            for (String friend : socialNetwork.getFriends(user)) {
                String pair1 = user + "-" + friend;
                String pair2 = friend + "-" + user;

                if (!processedPairs.contains(pair1) && !processedPairs.contains(pair2)) {
                    Map<String, Object> link = new HashMap<>();
                    link.put("source", user);
                    link.put("target", friend);
                    link.put("weight", socialNetwork.getFriendshipWeight(user, friend));
                    links.add(link);

                    processedPairs.add(pair1);
                    processedPairs.add(pair2);
                }
            }
        }

        graphData.put("nodes", nodes);
        graphData.put("links", links);

        return ResponseEntity.ok(graphData);
    }
    
    @GetMapping("/stats")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getNetworkStats() {
        Map<String, Object> stats = new HashMap<>();

        List<String> users = socialNetwork.getAllUsers();
        stats.put("totalUsers", users.size());

        int totalConnections = 0;
        Set<String> processedPairs = new HashSet<>();
        for (String user : users) {
            for (String friend : socialNetwork.getFriends(user)) {
                String pair1 = user + "-" + friend;
                String pair2 = friend + "-" + user;
                if (!processedPairs.contains(pair1) && !processedPairs.contains(pair2)) {
                    totalConnections++;
                    processedPairs.add(pair1);
                    processedPairs.add(pair2);
                }
            }
        }
        stats.put("totalConnections", totalConnections);

        stats.put("totalCommunities", socialNetwork.getNumberOfCommunities());
        stats.put("networkDensity", socialNetwork.getNetworkDensity());
        stats.put("avgClustering", socialNetwork.getAverageClusteringCoefficient());

        stats.put("mostConnected", socialNetwork.getMostConnectedUser());
        stats.put("mostInfluential", socialNetwork.getMostInfluentialUser());
        stats.put("highestCloseness", socialNetwork.getUserWithHighestCloseness());

        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/users/{userId}/stats")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserStats(@PathVariable String userId) {
        if (!socialNetwork.getAllUsers().contains(userId)) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> userStats = new HashMap<>();
        userStats.put("connections", socialNetwork.getDegreeCentrality(userId));
        userStats.put("degreeCentrality", socialNetwork.getDegreeCentrality(userId));
        userStats.put("closenessCentrality", socialNetwork.getClosenessCentrality(userId));
        userStats.put("pageRank", socialNetwork.getPageRank(userId));
        userStats.put("clusteringCoefficient", socialNetwork.getClusteringCoefficient(userId));
        userStats.put("friends", new ArrayList<>(socialNetwork.getFriends(userId)));

        return ResponseEntity.ok(userStats);
    }

    @GetMapping("/communities")
    @ResponseBody
    public ResponseEntity<List<List<String>>> getCommunities() {
        Map<String, List<String>> communitiesMap = socialNetwork.getCommunities();
        List<List<String>> communities = new ArrayList<>(communitiesMap.values());
        return ResponseEntity.ok(communities);
    }

    @GetMapping("/users/{userId}/suggestions")
    @ResponseBody
    public ResponseEntity<List<String>> getFriendSuggestions(@PathVariable String userId) {
        if (!socialNetwork.getAllUsers().contains(userId)) {
            return ResponseEntity.notFound().build();
        }

        List<String> suggestions = socialNetwork.suggestFriends(userId);
        return ResponseEntity.ok(suggestions);
    }
    
    @GetMapping("/path/{user1}/{user2}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getShortestPath(
            @PathVariable String user1,
            @PathVariable String user2,
            @RequestParam(defaultValue = "shortest") String type) {

        if (!socialNetwork.getAllUsers().contains(user1) || !socialNetwork.getAllUsers().contains(user2)) {
            return ResponseEntity.notFound().build();
        }

        List<String> path;
        switch (type.toLowerCase()) {
            case "strongest":
                path = socialNetwork.findStrongestPath(user1, user2);
                break;
            case "weakest":
                path = socialNetwork.findWeakestPath(user1, user2);
                break;
            default:
                path = socialNetwork.shortestPath(user1, user2);
                break;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("path", path);
        result.put("type", type);
        result.put("found", !path.isEmpty() && !path.get(0).equals("No path found"));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{userId}/mutual/{otherUserId}")
    @ResponseBody
    public ResponseEntity<List<String>> getMutualFriends(
            @PathVariable String userId,
            @PathVariable String otherUserId) {

        if (!socialNetwork.getAllUsers().contains(userId) || !socialNetwork.getAllUsers().contains(otherUserId)) {
            return ResponseEntity.notFound().build();
        }

        List<String> mutualFriends = socialNetwork.getMutualFriends(userId, otherUserId);
        return ResponseEntity.ok(mutualFriends);
    }
}
