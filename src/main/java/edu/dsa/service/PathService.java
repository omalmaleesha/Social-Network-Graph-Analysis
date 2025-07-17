package edu.dsa.service;

import java.util.List;

public interface PathService {
    List<String> shortestPath(String src, String dest);
    List<String> findStrongestPath(String src, String dest);
}
