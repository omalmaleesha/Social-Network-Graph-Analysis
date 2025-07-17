package edu.dsa.service.impl;

import edu.dsa.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private final UserServiceImpl userService;

    @Override
    public List<String> suggestFriends(String user) {
        if (!userService.userExists(user)) {
            return new ArrayList<>();
        }
        Set<String> directFriends = userService.getUsers().get(user).getFriends();
        Map<String, Integer> suggestionCount = new HashMap<>();
        for (String friend : directFriends) {
            for (String fof : userService.getUsers().get(friend).getFriends()) {
                if (!fof.equals(user) && !directFriends.contains(fof)) {
                    suggestionCount.put(fof, suggestionCount.getOrDefault(fof, 0) + 1);
                }
            }
        }
        int sizeU = directFriends.size();
        return suggestionCount.entrySet().stream()
                .sorted((a, b) -> {
                    String v1 = a.getKey();
                    int count1 = a.getValue();
                    int sizeV1 = userService.getUsers().get(v1).getFriends().size();
                    double J1 = (double) count1 / (sizeU + sizeV1 - count1);
                    String v2 = b.getKey();
                    int count2 = b.getValue();
                    int sizeV2 = userService.getUsers().get(v2).getFriends().size();
                    double J2 = (double) count2 / (sizeU + sizeV2 - count2);
                    return Double.compare(J2, J1);
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


}
