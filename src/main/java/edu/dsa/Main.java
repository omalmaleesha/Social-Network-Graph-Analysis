package edu.dsa;

import edu.dsa.service.SocialNetwork;

public class Main {
    public static void main(String[] args) {
        SocialNetwork sn = new SocialNetwork();

        sn.addFriendship("Alice", "Bob", 5);
        sn.addFriendship("Alice", "Charlie", 3);
        sn.addFriendship("Bob", "David", 2);
        sn.addFriendship("Charlie", "David", 4);
        sn.addFriendship("David", "Eve", 1);

        System.out.println("Mutual Friends (Alice & Bob): " + sn.getMutualFriends("Alice", "Bob"));
        System.out.println("Friend Suggestions for Alice: " + sn.suggestFriends("Alice"));
        System.out.println("Shortest Path Alice -> Eve: " + sn.shortestPath("Alice", "Eve"));
        System.out.println("Strongest Path Alice -> Eve: " + sn.findStrongestPath("Alice", "Eve"));
        System.out.println("Weakest Path Alice -> Eve: " + sn.findWeakestPath("Alice", "Eve"));
    }
}
