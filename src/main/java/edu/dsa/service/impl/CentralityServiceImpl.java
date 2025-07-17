package edu.dsa.service.impl;

import edu.dsa.service.CentralityService;
import edu.dsa.service.PathService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CentralityServiceImpl implements CentralityService {
    private final UserServiceImpl userService;
    private final PathService pathService;

    @Override
    public int getDegreeCentrality(String user) {
        if (!userService.userExists(user)) {
            return 0;
        }
        return userService.getUsers().get(user).getFriends().size();
    }

    @Override
    public String getMostConnectedUser() {
        String mostConnected = null;
        int maxDegree = -1;
        for (String user : userService.getAllUsers()) {
            int degree = getDegreeCentrality(user);
            if (degree > maxDegree) {
                maxDegree = degree;
                mostConnected = user;
            }
        }
        return mostConnected;
    }

    @Override
    public double getClosenessCentrality(String user) {
        if (!userService.userExists(user)) {
            return 0.0;
        }
        int sumDistances = 0;
        int count = 0;
        for (String other : userService.getAllUsers()) {
            if (!other.equals(user)) {
                List<String> path = pathService.shortestPath(user, other);
                if (!path.get(0).equals("No path found")) {
                    sumDistances += path.size() - 1;
                    count++;
                }
            }
        }
        if (count == 0) {
            return 0.0;
        }
        return (double) count / sumDistances;
    }

    @Override
    public String getUserWithHighestCloseness() {
        String bestUser = null;
        double maxCloseness = -1.0;
        for (String user : userService.getAllUsers()) {
            double closeness = getClosenessCentrality(user);
            if (closeness > maxCloseness) {
                maxCloseness = closeness;
                bestUser = user;
            }
        }
        return bestUser;
    }

    @Override
    public double getPageRank(String user) {
        if (!userService.userExists(user)) {
            return 0.0;
        }
        Map<String, Double> pr = computePageRank();
        return pr.get(user);
    }

    @Override
    public String getMostInfluentialUser() {
        Map<String, Double> pr = computePageRank();
        if (pr.isEmpty()) {
            return null;
        }
        String mostInfluential = null;
        double maxPR = -1.0;
        for (Map.Entry<String, Double> entry : pr.entrySet()) {
            if (entry.getValue() > maxPR) {
                maxPR = entry.getValue();
                mostInfluential = entry.getKey();
            }
        }
        return mostInfluential;
    }

    @Override
    public double getBetweennessCentrality(String user) {
        if (!userService.userExists(user)) return 0.0;
        double betweenness = 0.0;
        for (String s : userService.getAllUsers()) {
            for (String t : userService.getAllUsers()) {
                if (!s.equals(t) && !s.equals(user) && !t.equals(user)) {
                    // Implementation requires tracking all shortest paths
                }
            }
        }
        return betweenness / ((userService.getAllUsers().size() - 1) * (userService.getAllUsers().size() - 2) / 2);
    }

    @Override
    public String getHighestBetweennessUser() {
        String maxUser = null;
        double maxBetweenness = -1.0;
        for (String user : userService.getAllUsers()) {
            double betweenness = getBetweennessCentrality(user);
            if (betweenness > maxBetweenness) {
                maxBetweenness = betweenness;
                maxUser = user;
            }
        }
        return maxUser;
    }

    @Override
    public double getClusteringCoefficient(String user) {
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

    private Map<String, Double> computePageRank() {
        int N = userService.getAllUsers().size();
        if (N == 0) {
            return new HashMap<>();
        }
        double d = 0.85;
        double threshold = 0.0001;
        Map<String, Double> currentPR = new HashMap<>();
        for (String user : userService.getAllUsers()) {
            currentPR.put(user, 1.0 / N);
        }
        boolean converged = false;
        while (!converged) {
            Map<String, Double> nextPR = new HashMap<>();
            double maxChange = 0.0;
            for (String u : userService.getAllUsers()) {
                double sum = 0.0;
                Set<String> friends = userService.getUsers().get(u).getFriends();
                for (String v : friends) {
                    int degreeV = userService.getUsers().get(v).getFriends().size();
                    if (degreeV > 0) {
                        sum += currentPR.get(v) / degreeV;
                    }
                }
                double newPR = (1 - d) / N + d * sum;
                nextPR.put(u, newPR);
                double change = Math.abs(newPR - currentPR.get(u));
                if (change > maxChange) {
                    maxChange = change;
                }
            }
            currentPR = nextPR;
            if (maxChange < threshold) {
                converged = true;
            }
        }
        return currentPR;
    }
}
