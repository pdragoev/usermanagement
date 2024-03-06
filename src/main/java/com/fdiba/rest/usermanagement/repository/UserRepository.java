package com.fdiba.rest.usermanagement.repository;

import com.fdiba.rest.usermanagement.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRepository {

    private final List<User> userInMemoryDatabase = new ArrayList<>();

    public int count() {
        return userInMemoryDatabase.size();
    }

    public List<User> findAll() {
        return userInMemoryDatabase;
    }

    public User findById(final Long id) {
        return userInMemoryDatabase.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst().get();
    }

    public boolean save(final User user) {
        userInMemoryDatabase.add(user);
        return true;
    }

    public boolean isEmpty() {
        return userInMemoryDatabase.isEmpty();
    }

    public boolean existsById(final Long id) {
        return userInMemoryDatabase.stream().anyMatch(user -> user.getId().equals(id));
    }
}
