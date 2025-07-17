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


}
