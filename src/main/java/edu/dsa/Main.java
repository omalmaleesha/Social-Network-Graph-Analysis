package edu.dsa;

import edu.dsa.service.SocialNetwork;

public class Main {
    public static void main(String[] args) {
        // Create a new SocialNetwork instance
        SocialNetwork sn = new SocialNetwork();

        // Add users and friendships to form a sample network
        sn.addFriendship("Alice", "Bob", 5);
        sn.addFriendship("Alice", "Charlie", 3);
        sn.addFriendship("Bob", "David", 2);
        sn.addFriendship("Charlie", "David", 4);
        sn.addFriendship("David", "Eve", 1);

        // Add another isolated community
        sn.addFriendship("Frank", "Grace", 2);
        sn.addFriendship("Grace", "Heidi", 3);

        // Demonstrate existing functionality (optional, for context)
        System.out.println("Mutual Friends (Alice & Bob): " + sn.getMutualFriends("Alice", "Bob"));
        System.out.println("Friend Suggestions for Alice: " + sn.suggestFriends("Alice"));
        System.out.println("Shortest Path Alice -> Eve: " + sn.shortestPath("Alice", "Eve"));
        System.out.println("Strongest Path Alice -> Eve: " + sn.findStrongestPath("Alice", "Eve"));
        System.out.println("Weakest Path Alice -> Eve: " + sn.findWeakestPath("Alice", "Eve"));

        // Demonstrate community detection (optional, for context)
        System.out.println("Number of Communities: " + sn.getNumberOfCommunities());
        System.out.println("Communities: " + sn.getCommunities());

        // Test the new centrality measures
        System.out.println("\n--- Centrality Measures ---");

        // Test Degree Centrality
        System.out.println("Degree Centrality of Alice: " + sn.getDegreeCentrality("Alice"));
        System.out.println("Degree Centrality of David: " + sn.getDegreeCentrality("David"));
        System.out.println("Most Connected User: " + sn.getMostConnectedUser());

        // Test Closeness Centrality
        System.out.println("Closeness Centrality of Alice: " + sn.getClosenessCentrality("Alice"));
        System.out.println("Closeness Centrality of David: " + sn.getClosenessCentrality("David"));
        System.out.println("User with Highest Closeness Centrality: " + sn.getUserWithHighestCloseness());
    }
}