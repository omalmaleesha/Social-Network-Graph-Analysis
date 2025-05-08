package edu.dsa;

import edu.dsa.service.SocialNetwork;

public class Main {
    public static void main(String[] args) {
        SocialNetwork sn = new SocialNetwork();

        // Add users and friendships
        sn.addFriendship("Alice", "Bob", 5);
        sn.addFriendship("Alice", "Charlie", 3);
        sn.addFriendship("Bob", "David", 2);
        sn.addFriendship("Charlie", "David", 4);
        sn.addFriendship("David", "Eve", 1);

        // Add another isolated community
        sn.addFriendship("Frank", "Grace", 2);
        sn.addFriendship("Grace", "Heidi", 3);

        // Demonstrate existing functionality
        System.out.println("Mutual Friends (Alice & Bob): " + sn.getMutualFriends("Alice", "Bob"));
        System.out.println("Friend Suggestions for Alice: " + sn.suggestFriends("Alice"));
        System.out.println("Shortest Path Alice -> Eve: " + sn.shortestPath("Alice", "Eve"));
        System.out.println("Strongest Path Alice -> Eve: " + sn.findStrongestPath("Alice", "Eve"));
        System.out.println("Weakest Path Alice -> Eve: " + sn.findWeakestPath("Alice", "Eve"));

        // Demonstrate community detection
        System.out.println("Number of Communities: " + sn.getNumberOfCommunities());
        System.out.println("Communities: " + sn.getCommunities());

        // Print Alice's community
        System.out.println("Alice's community: " + String.join(", ", sn.getCommunity("Alice")));

        // Print summaries of all communities
        System.out.println("Communities summary:");
        for (String summary : sn.getCommunitySummaries()) {
            System.out.println(summary);
        }
    }
}