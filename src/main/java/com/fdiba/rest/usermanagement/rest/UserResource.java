package com.fdiba.rest.usermanagement.rest;

import com.fdiba.rest.usermanagement.repository.UserRepository;
import com.fdiba.rest.usermanagement.service.UserService;
import com.fdiba.rest.usermanagement.service.dto.CreateUserDTO;
import com.fdiba.rest.usermanagement.service.dto.UserDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;
    private final UserRepository userRepository;

    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllComments() {
        log.debug("REST request to get users");
        final List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countComments() {
        log.debug("REST request to count users");
        final Long count = userRepository.count();
        return ResponseEntity.ok().body(count);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get comment with ID: {}", id);
        UserDTO user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) throws URISyntaxException {
        log.debug("REST request to save user : {}", createUserDTO);

        UserDTO createdUser = userService.createUser(createUserDTO);

        if (createdUser == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity
                .created(new URI("/api/teams/" + createdUser.getId()))
                .body(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete user with ID: {}", id);

        boolean isDeleted = userService.delete(id);

        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
