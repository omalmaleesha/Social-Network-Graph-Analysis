package edu.dsa.service;

public interface CentralityService {
    int getDegreeCentrality(String user);
    String getMostConnectedUser();
    double getClosenessCentrality(String user);
    String getUserWithHighestCloseness();
    double getPageRank(String user);
    String getMostInfluentialUser();
    double getBetweennessCentrality(String user);
    String getHighestBetweennessUser();
    double getClusteringCoefficient(String user);
}
