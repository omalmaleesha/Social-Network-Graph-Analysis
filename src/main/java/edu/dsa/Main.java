package edu.dsa;

import edu.dsa.service.SocialNetwork;

public class Main {
    public static void main(String[] args) {
        SocialNetwork sn = new SocialNetwork();

        sn.addFriendship("Alice", "Bob");
        sn.addFriendship("Alice", "Charlie");
        sn.addFriendship("Bob", "David");
        sn.addFriendship("Charlie", "David");
        sn.addFriendship("David", "Eve");

        System.out.println("Mutual Friends (Alice & Bob): " + sn.getMutualFriends("Alice", "Bob"));
        System.out.println("Friend Suggestions for Alice: " + sn.suggestFriends("Alice"));
        System.out.println("Shortest Path Alice -> Eve: " + sn.shortestPath("Alice", "Eve"));
    }
}

