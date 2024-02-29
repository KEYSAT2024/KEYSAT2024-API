package keysat.controller;

import keysat.entities.User;
import keysat.repository.UserRepository;
import keysat.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jayway.jsonpath.JsonPath;

import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Base64;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    public void cleanUp() {
      userRepository.deleteAllInBatch();
    }
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllUsers() throws Exception {

        // Step 1: Create a user
        String username = "newUser";
        String password = "password";
        String role = "ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role
                        + "\" }")
                .param("role", "ADMIN")).andExpect(status().isOk());

        // Perform GET request to /user
        ResultActions resultActions = mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").exists()) // Check if the first user has a username
                .andExpect(jsonPath("$[0].password").exists());// Ensure password is returned
    }

    @Test
    public void testGetUserById() throws Exception {
        // Step 1: Create a user
        String username = "newUser";
        String password = "password";
        String role = "ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role
                        + "\" }")
                .param("role", "ADMIN")).andExpect(status().isOk());

        // Perform GET request to /user/{userId}
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.password").exists());
    }

    @Test
    public void testCreateUser() throws Exception {

        // Step 1: Create a user
        String username = "newUser";
        String password = "password";
        String role = "ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role
                        + "\" }")
                .param("role", "ADMIN")).andExpect(status().isOk());

        // Perform GET request to /user
        ResultActions resultActions = mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").exists()) // Check if the first user has a username
                .andExpect(jsonPath("$[0].password").exists());// Ensure password is returned
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Step 1: Create a user
        String username = "newUser";
        String password = "password";
        String role = "ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role
                        + "\" }")
                .param("role", "ADMIN")).andExpect(status().isOk());

        // Step 2: Get the first user in the array
        User updatedUser = new User();
        updatedUser.setUsername("updatedUser");

        // Step 3: Update the user
        mockMvc.perform(MockMvcRequestBuilders.put("/user/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + updatedUser.getUsername() + "\", \"role\": \"" + role + "\" }"))
                .andExpect(status().isOk());

        // Step 4: Check if the user was updated
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()));
    }

    @Test
    @Transactional

    public void testChangePassword() throws Exception {
        // Given: a user exists
        String username = "newUser";
        String password = "password";
        String newPassword = "newPassword";
        String role = "USER";

        // Directly create the user using the service layer for consistent context
        User newUser = userService.createUser(username, password, role);

        // When: the user's password is changed
        userService.changeUserPassword(username, newPassword);

        // Then: attempt to fetch the user and verify the password was updated
        User updatedUser = userRepository.findByUsername(username);
            if (updatedUser == null) {
        throw new AssertionError("User not found after password update.");}
    

        // Use the encoder's matches method to check if the passwords match
        boolean passwordMatches = passwordEncoder.matches(newPassword, updatedUser.getPassword());

        assertTrue(passwordMatches, "The password should have been updated and match the new password.");
    }





    @Test
    public void testDeleteUser() throws Exception {
        // Step 1: Create a user
        String username = "newUser";
        String password = "password";
        String role = "ADMIN";

        // Create the user and capture the response
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/create?role=ADMIN") // As part of URL
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }"))                                     
                .andExpect(status().isOk())
                .andReturn();

        // Extract the user ID from the response
        String responseString = result.getResponse().getContentAsString();
        Long userId = JsonPath.parse(responseString).read("$.user_id", Long.class);

        // Now delete the user using the extracted user ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Check if the user has been deleted
        mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.user_id == %d)]", userId).doesNotExist());
    }
}