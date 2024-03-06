package com.fdiba.rest.usermanagement.factory;

import com.fdiba.rest.usermanagement.domain.User;
import com.fdiba.rest.usermanagement.service.dto.UserDTO;

public class UserFactory {

    private UserFactory() {
    }

    public static User createUser() {
        final User user = new User();
        user.setId(1L);
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("changeme");
        user.setUsername("testuser");

        return user;
    }

    public static UserDTO createUserDTO() {
        final UserDTO userDTO = new UserDTO();

        userDTO.setId(1L);
        userDTO.setFirstName("Test");
        userDTO.setLastName("Test");
        userDTO.setUsername("testuser");

        return userDTO;
    }
}
