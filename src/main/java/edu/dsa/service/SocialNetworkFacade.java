package edu.dsa.service;

import edu.dsa.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SocialNetworkFacade {
    private final UserService userService;
    private final FriendshipService friendshipService;
    private final PathService pathService;
    private final CommunityService communityService;
    private final CentralityService centralityService;
    private final NetworkAnalysisService networkAnalysisService;
    private final RecommendationService recommendationService;

    // User Management
    public void addUser(String name) {
        userService.addUser(name);
    }

    public List<String> getAllUsers() {
        return userService.getAllUsers();
    }

    public Set<String> getFriends(String user) {
        return userService.getFriends(user);
    }

    // Friendship Management
    public void addFriendship(String user1, String user2, int weight) {
        friendshipService.addFriendship(user1, user2, weight);
    }

    public int getFriendshipWeight(String user1, String user2) {
        return friendshipService.getFriendshipWeight(user1, user2);
    }

    public List<String> getMutualFriends(String user1, String user2) {
        return friendshipService.getMutualFriends(user1, user2);
    }

    // Path Finding
    public List<String> shortestPath(String src, String dest) {
        return pathService.shortestPath(src, dest);
    }

    public List<String> findStrongestPath(String src, String dest) {
        return pathService.findStrongestPath(src, dest);
    }



    // Community Analysis
    public int getNumberOfCommunities() {
        return communityService.getNumberOfCommunities();
    }

    public Map<String, List<String>> getCommunities() {
        return communityService.getCommunities();
    }



    // Centrality Measures
    public int getDegreeCentrality(String user) {
        return centralityService.getDegreeCentrality(user);
    }

    public String getMostConnectedUser() {
        return centralityService.getMostConnectedUser();
    }

    public double getClosenessCentrality(String user) {
        return centralityService.getClosenessCentrality(user);
    }

    public String getUserWithHighestCloseness() {
        return centralityService.getUserWithHighestCloseness();
    }

    public double getPageRank(String user) {
        return centralityService.getPageRank(user);
    }

    public String getMostInfluentialUser() {
        return centralityService.getMostInfluentialUser();
    }



    public double getClusteringCoefficient(String user) {
        return centralityService.getClusteringCoefficient(user);
    }

    // Network Analysis
    public double getNetworkDensity() {
        return networkAnalysisService.getNetworkDensity();
    }

    public double getAverageClusteringCoefficient() {
        return networkAnalysisService.getAverageClusteringCoefficient();
    }



    // Recommendations
    public List<String> suggestFriends(String user) {
        return recommendationService.suggestFriends(user);
    }


}
