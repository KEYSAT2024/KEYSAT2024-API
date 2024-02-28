package keysat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import keysat.dto.LoginDTO;
import keysat.entities.Role;
import keysat.entities.User;
import keysat.repository.UserRepository;
import keysat.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(AuthenticationControllerTest.class)
@AutoConfigureWebMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserDetailsService userDetailsService;

    @BeforeEach
    void SetUp() {
        // Step 1: Create a user
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        // Create a role and add it to the user
        Role role = new Role();
        role.setName("USER");
        user.setRoles(Collections.singleton(role));

        // Mock data for userRepository
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        // Mock data for userDetailsService
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(role.getName()) // Pass the role name directly
                .build();
        when(userDetailsService.loadUserByUsername(user.getUsername())).thenReturn(userDetails);

        // Mock data for userService
        when(userService.loadUserByUsername(user.getUsername())).thenReturn(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(role.getName()) // Pass the role name directly
                        .build());
    }




    @Test
    public void testLoginWithValidCredentials() throws Exception {
        // Mock login DTO
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testUser");
        loginDTO.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Logged in successfully."));
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        // Mock login DTO with incorrect password
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testUser");
        loginDTO.setPassword("incorrectPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Authentication failed."));
    }

}
