package edu.dsa.service.impl;

import edu.dsa.service.CommunityService;
import edu.dsa.service.impl.FriendshipServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final FriendshipServiceImpl friendshipService;

    @Override
    public int getNumberOfCommunities() {
        return friendshipService.getDsu().getNumberOfCommunities();
    }

    @Override
    public Map<String, List<String>> getCommunities() {
        return friendshipService.getDsu().getCommunities();
    }

    @Override
    public List<String> getCommunity(String user) {
        String root = friendshipService.getDsu().find(user);
        if (root == null) {
            return new ArrayList<>();
        }
        Map<String, List<String>> communities = friendshipService.getDsu().getCommunities();
        return communities.getOrDefault(root, new ArrayList<>());
    }

    @Override
    public List<String> getCommunitySummaries() {
        Map<String, List<String>> communities = friendshipService.getDsu().getCommunities();
        List<String> summaries = new ArrayList<>();
        for (String root : communities.keySet()) {
            int size = communities.get(root).size();
            summaries.add("Community " + root + ": " + size + " members");
        }
        Collections.sort(summaries);
        return summaries;
    }
}
