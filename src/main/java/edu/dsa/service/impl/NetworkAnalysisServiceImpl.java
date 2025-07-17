package edu.dsa.service.impl;

import edu.dsa.service.NetworkAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NetworkAnalysisServiceImpl implements NetworkAnalysisService {
    private final UserServiceImpl userService;

    @Override
    public double getNetworkDensity() {
        int n = userService.getAllUsers().size();
        if (n < 2) return 0.0;
        int edges = 0;
        for (String user : userService.getAllUsers()) {
            edges += userService.getUsers().get(user).getFriends().size();
        }
        edges /= 2;
        return (2.0 * edges) / (n * (n - 1));
    }

    @Override
    public double getAverageClusteringCoefficient() {
        double sum = 0.0;
        int count = 0;
        for (String user : userService.getAllUsers()) {
            double cc = getClusteringCoefficient(user);
            if (cc > 0) {
                sum += cc;
                count++;
            }
        }
        return count > 0 ? sum / count : 0.0;
    }

    @Override
    public int getTriangleCount(String user) {
        if (!userService.userExists(user)) return 0;
        Set<String> friends = userService.getUsers().get(user).getFriends();
        int triangles = 0;
        for (String f1 : friends) {
            for (String f2 : friends) {
                if (!f1.equals(f2) && userService.getUsers().get(f1).getFriends().contains(f2)) {
                    triangles++;
                }
            }
        }
        return triangles / 2;
    }

    @Override
    public int getNetworkTriangleCount() {
        int total = 0;
        for (String user : userService.getAllUsers()) {
            total += getTriangleCount(user);
        }
        return total / 3;
    }

    @Override
    public Set<String> findInfluencers(int k) {
        Set<String> influencers = new HashSet<>();
        for (int i = 0; i < k; i++) {
            String bestUser = null;
            double maxMarginalGain = -1;
            for (String user : userService.getAllUsers()) {
                if (!influencers.contains(user)) {
                    double gain = estimateMarginalGain(influencers, user);
                    if (gain > maxMarginalGain) {
                        maxMarginalGain = gain;
                        bestUser = user;
                    }
                }
            }
            if (bestUser != null) influencers.add(bestUser);
        }
        return influencers;
    }

    private double estimateMarginalGain(Set<String> currentSet, String user) {
        return userService.getUsers().get(user).getFriends().size();
    }

    private double getClusteringCoefficient(String user) {
        if (!userService.userExists(user)) return 0.0;
        Set<String> friends = userService.getUsers().get(user).getFriends();
        int k = friends.size();
        if (k < 2) return 0.0;
        int edges = 0;
        for (String f1 : friends) {
            for (String f2 : friends) {
                if (!f1.equals(f2) && userService.getUsers().get(f1).getFriends().contains(f2)) {
                    edges++;
                }
            }
        }
        edges /= 2;
        return (2.0 * edges) / (k * (k - 1));
    }
}
