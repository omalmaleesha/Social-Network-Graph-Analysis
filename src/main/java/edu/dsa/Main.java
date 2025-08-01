package edu.dsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Set;
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        // Create a new SocialNetwork instance
//        SocialNetwork sn = new SocialNetwork();
//
//        // Add users and friendships to form a sample network
//        sn.addFriendship("Alice", "Bob", 5);
//        sn.addFriendship("Alice", "Charlie", 3);
//        sn.addFriendship("Bob", "David", 2);
//        sn.addFriendship("Charlie", "David", 4);
//        sn.addFriendship("David", "Eve", 1);
//
//        // Add another isolated community
//        sn.addFriendship("Frank", "Grace", 2);
//        sn.addFriendship("Grace", "Heidi", 3);
//
//        // Demonstrate existing functionality
//        System.out.println("Mutual Friends (Alice & Bob): " + sn.getMutualFriends("Alice", "Bob"));
//        System.out.println("Friend Suggestions for Alice: " + sn.suggestFriends("Alice"));
//        System.out.println("Shortest Path Alice -> Eve: " + sn.shortestPath("Alice", "Eve"));
//        System.out.println("Strongest Path Alice -> Eve: " + sn.findStrongestPath("Alice", "Eve"));
//        System.out.println("Weakest Path Alice -> Eve: " + sn.findWeakestPath("Alice", "Eve"));
//
//        // Demonstrate community detection
//        System.out.println("Number of Communities: " + sn.getNumberOfCommunities());
//        System.out.println("Communities: " + sn.getCommunities());
//
//        // Test centrality measures
//        System.out.println("\n--- Centrality Measures ---");
//        System.out.println("Degree Centrality of Alice: " + sn.getDegreeCentrality("Alice"));
//        System.out.println("Degree Centrality of David: " + sn.getDegreeCentrality("David"));
//        System.out.println("Most Connected User: " + sn.getMostConnectedUser());
//        System.out.println("Closeness Centrality of Alice: " + sn.getClosenessCentrality("Alice"));
//        System.out.println("Closeness Centrality of David: " + sn.getClosenessCentrality("David"));
//        System.out.println("User with Highest Closeness Centrality: " + sn.getUserWithHighestCloseness());
//
//        // Test new features: PageRank and Enhanced Friend Suggestions
//        System.out.println("\n--- New Features: PageRank and Friend Suggestions ---");
//
//        // Test PageRank
//        System.out.println("PageRank of Alice: " + sn.getPageRank("Alice"));
//        System.out.println("PageRank of David: " + sn.getPageRank("David"));
//        System.out.println("Most Influential User: " + sn.getMostInfluentialUser());
//
//        // Test Enhanced Friend Suggestions with Jaccard Similarity
//        System.out.println("Enhanced Friend Suggestions for Alice: " + sn.suggestFriends("Alice"));
//        System.out.println("Enhanced Friend Suggestions for Frank: " + sn.suggestFriends("Frank"));
//
//        // Test new features
//        System.out.println("\n--- New Network Analysis Features ---");
//
//        // Test Network Density
//        System.out.println("Network Density: " + sn.getNetworkDensity());
//
//        // Test Betweenness Centrality
//        System.out.println("Betweenness Centrality of Alice: " + sn.getBetweennessCentrality("Alice"));
//        System.out.println("Betweenness Centrality of David: " + sn.getBetweennessCentrality("David"));
//        System.out.println("User with Highest Betweenness: " + sn.getHighestBetweennessUser());
//
//        // Test Clustering Coefficient
//        System.out.println("Clustering Coefficient of Alice: " + sn.getClusteringCoefficient("Alice"));
//        System.out.println("Clustering Coefficient of David: " + sn.getClusteringCoefficient("David"));
//        System.out.println("Average Clustering Coefficient: " + sn.getAverageClusteringCoefficient());
//
//        // Test Influence Maximization: Find top 2 influencers
//        System.out.println("--- Influence Maximization ---");
//        Set<String> influencers = sn.findInfluencers(2);
//        System.out.println("Top 2 influencers: " + influencers);
//
//        // Test Link Prediction: Predict top 2 potential new friends for Alice
//        System.out.println("\n--- Link Prediction ---");
//        List<String> predictedFriends = sn.predictNewFriends("Alice", 2);
//        System.out.println("Top 2 predicted new friends for Alice: " + predictedFriends);
//
//        // Test Network Motif Analysis: Triangles for Bob and in the entire network
//        System.out.println("\n--- Network Motif Analysis ---");
//        int bobTriangles = sn.getTriangleCount("Bob");
//        System.out.println("Number of triangles for Bob: " + bobTriangles);
//        int networkTriangles = sn.getNetworkTriangleCount();
//        System.out.println("Total number of triangles in the network: " + networkTriangles);

    }
}