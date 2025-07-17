package edu.dsa.service;

import java.util.List;
import java.util.Set;

public interface UserService {
    void addUser(String name);
    List<String> getAllUsers();
    Set<String> getFriends(String user);
    boolean userExists(String user);
}
