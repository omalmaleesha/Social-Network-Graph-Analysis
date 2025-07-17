package edu.dsa.config;

import edu.dsa.service.SocialNetworkFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class SocialNetworkDataInitializer {
    private final SocialNetworkFacade socialNetwork;

    @PostConstruct
    public void initializeSampleData() {
        socialNetwork.addFriendship("Alice", "Bob", 5);
        socialNetwork.addFriendship("Alice", "Charlie", 3);
        socialNetwork.addFriendship("Bob", "David", 2);
        socialNetwork.addFriendship("Charlie", "David", 4);
        socialNetwork.addFriendship("David", "Eve", 1);

        socialNetwork.addFriendship("Frank", "Grace", 2);
        socialNetwork.addFriendship("Grace", "Heidi", 3);

        System.out.println("Sample social network data initialized successfully!");
        System.out.println("- Component 1: Alice, Bob, Charlie, David, Eve (densely connected)");
        System.out.println("- Component 2: Frank, Grace, Heidi (linear chain)");
    }

    public void initializeCustomData() {
        System.out.println("Custom data initialization - implement as needed");
    }

    public void initializeLargeDataset() {
        socialNetwork.addFriendship("Alice", "Bob", 5);
        socialNetwork.addFriendship("Alice", "Charlie", 4);
        socialNetwork.addFriendship("Bob", "Charlie", 3);
        socialNetwork.addFriendship("Charlie", "David", 4);

        socialNetwork.addFriendship("Eve", "Frank", 5);
        socialNetwork.addFriendship("Frank", "Grace", 4);
        socialNetwork.addFriendship("Grace", "Heidi", 3);
        socialNetwork.addFriendship("Heidi", "Eve", 4);

        socialNetwork.addFriendship("David", "Eve", 2);
        socialNetwork.addFriendship("Charlie", "Frank", 1);

        System.out.println("Large dataset initialized with multiple communities and bridges");
    }
}
