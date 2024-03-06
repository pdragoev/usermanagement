package com.fdiba.rest.usermanagement.service;

import com.fdiba.rest.usermanagement.domain.User;
import com.fdiba.rest.usermanagement.repository.UserRepository;
import com.fdiba.rest.usermanagement.service.dto.CreateUserDTO;
import com.fdiba.rest.usermanagement.service.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserById(final Long id) {
        if (userRepository.isEmpty()) {
            return null;
        }

        if (!userRepository.existsById(id)) {
            return null;
        }

        User foundUser = userRepository.findById(id);
        UserDTO dto = new UserDTO();
        dto.setId(foundUser.getId());
        dto.setFirstName(foundUser.getFirstName());
        dto.setLastName(foundUser.getLastName());
        dto.setUsername(foundUser.getUsername());
        return dto;
    }

    public boolean createUser(final CreateUserDTO createUserDTO) {
        final String password = createUserDTO.getPassword();
        final String confirmationPassword = createUserDTO.getConfirmationPassword();

        if (!password.equals(confirmationPassword)) {
            return false;
        }

        final Long userId = userRepository.count() + 1L;

        User newUser = new User();
        newUser.setId(userId);
        newUser.setFirstName(createUserDTO.getFirstName());
        newUser.setLastName(createUserDTO.getLastName());
        newUser.setUsername(createUserDTO.getUsername());
        newUser.setPassword(createUserDTO.getPassword());

        userRepository.save(newUser);

        return true;
    }
}
