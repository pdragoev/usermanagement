package com.fdiba.rest.usermanagement.rest;

import com.fdiba.rest.usermanagement.IntegrationTest;
import com.fdiba.rest.usermanagement.domain.User;
import com.fdiba.rest.usermanagement.factory.UserFactory;
import com.fdiba.rest.usermanagement.repository.UserRepository;
import com.fdiba.rest.usermanagement.service.dto.CreateUserDTO;
import com.fdiba.rest.usermanagement.util.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
@ExtendWith(MockitoExtension.class)
public class UserResourceIT {

    private static final String BASE_URL_PREFIX = "/api/users";
    private static final String BASE_URL_PREFIX_WITH_ID = BASE_URL_PREFIX + "/{id}";


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc restTeamMockMvc;

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Nested
    class Test_DtoValidations {
            
    }

    @Nested
    class Test_GetRestEndpoints {
        @Test
        void when_getByIdForNonExistingUser_shouldReturnNotFound() throws Exception {
            // Given
            // When
            restTeamMockMvc
                    .perform(get(BASE_URL_PREFIX_WITH_ID, Long.MAX_VALUE))
                    .andExpect(status().isNotFound());
            // Then
        }

        @Test
        void when_getByIdForExistingUser_shouldReturnUser() throws Exception {
            // Given
            User user = UserFactory.createUser();
            userRepository.save(user);

            // When
            restTeamMockMvc
                    .perform(get(BASE_URL_PREFIX_WITH_ID, user.getId()))
                    .andExpect(status().isOk());

            // Then
            User userInDatabase = userRepository.findById(user.getId());

            Assertions.assertNotNull(userInDatabase);
            Assertions.assertEquals(user.getId(), userInDatabase.getId());
            Assertions.assertEquals(user.getFirstName(), userInDatabase.getFirstName());
            Assertions.assertEquals(user.getLastName(), userInDatabase.getLastName());
            Assertions.assertEquals(user.getUsername(), userInDatabase.getUsername());
        }


        @Test
        void when_getAllForEmptyDatabase_shouldReturnEmptyUserList() throws Exception {
            // Given
            // When + Then
            restTeamMockMvc
                    .perform(get(BASE_URL_PREFIX))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
        }
    }

    @Nested
    class Test_PostRestEndpoints {
        @Test
        void when_createUser_shouldThrowBadRequest() throws Exception {
            // Given
            final CreateUserDTO invalidPasswordDTO = new CreateUserDTO();
            invalidPasswordDTO.setPassword("test");
            invalidPasswordDTO.setConfirmationPassword("changeme");

            final long databaseSizeBeforeCreate = userRepository.count();

            // When
            restTeamMockMvc
                    .perform(
                            post(BASE_URL_PREFIX)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(invalidPasswordDTO))
                    )
                    .andExpect(status().isBadRequest())
                    .andReturn();

            // Then
            final long databaseSizeAfterCreate = userRepository.count();
            assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate);
        }
    }
}
