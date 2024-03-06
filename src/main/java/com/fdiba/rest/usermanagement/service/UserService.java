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

    public Long countUsers() {
        return userRepository.count();
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();

                    dto.setId(user.getId());
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setUsername(user.getUsername());

                    return dto;
                }).toList();
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

    public UserDTO createUser(final CreateUserDTO createUserDTO) {
        final String password = createUserDTO.getPassword();
        final String confirmationPassword = createUserDTO.getConfirmationPassword();

        if (!password.equals(confirmationPassword)) {
            return null;
        }

        final Long userId = userRepository.count() + 1L;

        User newUser = new User();
        newUser.setId(userId);
        newUser.setFirstName(createUserDTO.getFirstName());
        newUser.setLastName(createUserDTO.getLastName());
        newUser.setUsername(createUserDTO.getUsername());
        newUser.setPassword(createUserDTO.getPassword());

        userRepository.save(newUser);

        UserDTO dto = new UserDTO();
        dto.setId(newUser.getId());
        dto.setFirstName(newUser.getFirstName());
        dto.setLastName(newUser.getLastName());
        dto.setUsername(newUser.getUsername());

        return dto;
    }

    public boolean delete(Long id) {
        final User foundUser = userRepository.findById(id);

        if (foundUser == null) {
            return false; // skip delete for non-existing user
        }

        return userRepository.delete(foundUser);
    }
}
