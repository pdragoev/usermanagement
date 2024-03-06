package com.fdiba.rest.usermanagement.service;

import com.fdiba.rest.usermanagement.domain.User;
import com.fdiba.rest.usermanagement.repository.UserRepository;
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
        final List<User> foundUsers = userService.getAllUsers();

        //Then
        Assertions.assertEquals(foundUsers.size(), 0);
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

}
