package edu.dsa.service.impl;

import edu.dsa.service.PathService;
import edu.dsa.service.impl.UserServiceImpl;
import edu.dsa.service.impl.FriendshipServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PathServiceImpl implements PathService {
    private final UserServiceImpl userService;
    private final FriendshipServiceImpl friendshipService;

    @Override
    public List<String> shortestPath(String src, String dest) {
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.offer(src);
        visited.add(src);
        parent.put(src, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(dest)) break;

            for (String neighbor : userService.getUsers().get(current).getFriends()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                }
            }
        }

        if (!parent.containsKey(dest)) {
            List<String> noPath = new ArrayList<>();
            noPath.add("No path found");
            return noPath;
        }

        List<String> path = new ArrayList<>();
        for (String at = dest; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public List<String> findStrongestPath(String src, String dest) {
        if (!userService.userExists(src) || !userService.userExists(dest)) {
            List<String> noPath = new ArrayList<>();
            noPath.add("No path found");
            return noPath;
        }
        return dijkstraPath(src, dest, true);
    }

    @Override
    public List<String> findWeakestPath(String src, String dest) {
        if (!userService.userExists(src) || !userService.userExists(dest)) {
            List<String> noPath = new ArrayList<>();
            noPath.add("No path found");
            return noPath;
        }
        return dijkstraPath(src, dest, false);
    }

    private List<String> dijkstraPath(String src, String dest, boolean isStrongest) {
        Map<String, Double> distance = new HashMap<>();
        distance.put(src, 0.0);
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>((a, b) -> Double.compare(
                distance.getOrDefault(a, Double.POSITIVE_INFINITY),
                distance.getOrDefault(b, Double.POSITIVE_INFINITY)
        ));
        queue.add(src);

        while (!queue.isEmpty()) {
            String u = queue.poll();
            double distU = distance.getOrDefault(u, Double.POSITIVE_INFINITY);
            if (distU == Double.POSITIVE_INFINITY) break;

            if (u.equals(dest)) break;

            for (String v : userService.getUsers().get(u).getFriends()) {
                int weight = friendshipService.getFriendshipWeight(u, v);
                double cost = isStrongest ? 1.0 / weight : weight;
                double newDist = distU + cost;
                if (newDist < distance.getOrDefault(v, Double.POSITIVE_INFINITY)) {
                    distance.put(v, newDist);
                    parent.put(v, u);
                    queue.add(v);
                }
            }
        }

        if (!distance.containsKey(dest)) {
            List<String> noPath = new ArrayList<>();
            noPath.add("No path found");
            return noPath;
        }

        List<String> path = new ArrayList<>();
        for (String at = dest; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
