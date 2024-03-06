package com.fdiba.rest.usermanagement.service;

import com.fdiba.rest.usermanagement.domain.User;
import com.fdiba.rest.usermanagement.factory.UserFactory;
import com.fdiba.rest.usermanagement.repository.UserRepository;
import com.fdiba.rest.usermanagement.service.dto.CreateUserDTO;
import com.fdiba.rest.usermanagement.service.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    public UserRepository userRepository;

    @InjectMocks
    public UserService userService;

    @Test
    void when_getAllUsersIsEmpty_shouldReturnEmptyList() {
        //Given
        final List<User> emptyList = new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(emptyList);

        //When
        final List<UserDTO> foundUsers = userService.getAllUsers();

        //Then
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoInteractions(userRepository);

        Assertions.assertEquals(foundUsers.size(), 0);
    }

    @Test
    void when_getAllUsersIsNotEmpty_shouldReturnNonEmptyList() {
        //Given
        final User user = UserFactory.createUser();
        final List<User> userList = List.of(user);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        //When
        final List<UserDTO> foundUsers = userService.getAllUsers();

        //Then
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoInteractions(userRepository);

        Assertions.assertEquals(userList.size(), foundUsers.size());
    }

    @Test
    void when_getUserByIdIsEmpty_shouldReturnNull() {
        //Given
        Mockito.when(userRepository.isEmpty()).thenReturn(true);

        //When
        final UserDTO foundUser = userService.getUserById(1L);

        //Then
        Mockito.verify(userRepository, Mockito.times(1)).isEmpty();
        Mockito.verifyNoInteractions(userRepository);

        Assertions.assertNull(foundUser);
    }

    @Test
    void when_getUserByIdNotExists_shouldReturnNull() {
        //Given
        Mockito.when(userRepository.isEmpty()).thenReturn(false);
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(false);

        //When
        final UserDTO foundUser = userService.getUserById(1L);

        //Then
        Mockito.verify(userRepository, Mockito.times(1)).isEmpty();
        Mockito.verify(userRepository, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verifyNoInteractions(userRepository);

        Assertions.assertNull(foundUser);
    }

    @Test
    void when_getUserById_shouldReturnSucceed() {
        //Given
        final User user = UserFactory.createUser();

        Mockito.when(userRepository.isEmpty()).thenReturn(false);
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(user);

        //When
        final UserDTO foundUser = userService.getUserById(user.getId());

        //Then
        Mockito.verify(userRepository, Mockito.times(1)).isEmpty();
        Mockito.verify(userRepository, Mockito.times(1)).existsById(user.getId());
        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getId());
        Mockito.verifyNoInteractions(userRepository);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(user.getFirstName(), foundUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), foundUser.getLastName());
        Assertions.assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void when_createUserWithInvalidPassword_shouldReturnNull() {
        //Given
        final CreateUserDTO invalidPasswordDTO = new CreateUserDTO();
        invalidPasswordDTO.setPassword("test");
        invalidPasswordDTO.setConfirmationPassword("changeme");

        //When
        final UserDTO createdUser = userService.createUser(invalidPasswordDTO);

        //Then
        Mockito.verifyNoInteractions(userRepository);

        Assertions.assertNull(createdUser);

    }

}
