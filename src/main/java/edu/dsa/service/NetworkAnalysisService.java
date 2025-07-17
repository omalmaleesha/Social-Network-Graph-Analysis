package edu.dsa.service;

import java.util.Set;

public interface NetworkAnalysisService {
    double getNetworkDensity();
    double getAverageClusteringCoefficient();
    int getTriangleCount(String user);
    int getNetworkTriangleCount();
    Set<String> findInfluencers(int k);
}
