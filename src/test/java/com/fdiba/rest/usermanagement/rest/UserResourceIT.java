package com.fdiba.rest.usermanagement.rest;

import com.fdiba.rest.usermanagement.IntegrationTest;
import com.fdiba.rest.usermanagement.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


    @Nested
    class Test_GetRestEndpoints {
        @Test
        void when_getByIdForNonExistingTeam_shouldReturnNotFound() throws Exception {
            // Given
            // When
            restTeamMockMvc
                    .perform(get(BASE_URL_PREFIX_WITH_ID, Long.MAX_VALUE))
                    .andExpect(status().isNotFound());
            // Then
        }
    }
}
