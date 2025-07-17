package edu.dsa.service;

import java.util.List;

public interface RecommendationService {
    List<String> suggestFriends(String user);
}
