package edu.dsa.service;

import java.util.List;
import java.util.Map;

public interface CommunityService {
    int getNumberOfCommunities();
    Map<String, List<String>> getCommunities();
}
