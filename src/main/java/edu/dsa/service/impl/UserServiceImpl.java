package edu.dsa.service.impl;

import edu.dsa.model.User;
import edu.dsa.service.UserService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter
public class UserServiceImpl implements UserService {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void addUser(String name) {
        if (!users.containsKey(name)) {
            users.put(name, new User(name));
        }
    }

    @Override
    public List<String> getAllUsers() {
        return new ArrayList<>(users.keySet());
    }

    @Override
    public Set<String> getFriends(String user) {
        if (!users.containsKey(user)) {
            return new HashSet<>();
        }
        return users.get(user).getFriends();
    }

    @Override
    public boolean userExists(String user) {
        return users.containsKey(user);
    }
}
