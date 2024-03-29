package com.fdiba.rest.usermanagement.repository;

import com.fdiba.rest.usermanagement.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRepository {

    private List<User> userInMemoryDatabase = new ArrayList<>();

    public long count() {
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

    public boolean delete(final User user) {
        userInMemoryDatabase.remove(user);
        return userInMemoryDatabase.stream().anyMatch(u -> u.getId().equals(user.getId()));
    }

    public void deleteAll() {
        this.userInMemoryDatabase = new ArrayList<>();
    }

    public boolean isEmpty() {
        return userInMemoryDatabase.isEmpty();
    }

    public boolean existsById(final Long id) {
        return userInMemoryDatabase.stream().anyMatch(user -> user.getId().equals(id));
    }
}
