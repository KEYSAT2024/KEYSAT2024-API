package keysat.controller;

import keysat.entities.User;
import keysat.UserService;
import keysat.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Call the deleteAllUsers() method before each test
        userService.deleteAllUsers();
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllUsers() throws Exception {

        // Step 1: Create a user
        String username = "newUser";
        String password = "password";
        String role = "ROLE_ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }")
                .param("role", "ROLE_ADMIN")
        ).andExpect(status().isOk());

        // Perform GET request to /users
        ResultActions resultActions = mockMvc.perform(get("/users")
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
        String role = "ROLE_ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }")
                .param("role", "ROLE_ADMIN")
        ).andExpect(status().isOk());

        // Perform GET request to /users/{userId}
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", 1)
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
        String role = "ROLE_ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }")
                .param("role", "ROLE_ADMIN")
        ).andExpect(status().isOk());

        // Perform GET request to /users
        ResultActions resultActions = mockMvc.perform(get("/users")
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
        String role = "ROLE_ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }")
                .param("role", "ROLE_ADMIN")
        ).andExpect(status().isOk());

        // Step 2: Get the first user in the array
        User updatedUser = new User();
        updatedUser.setUsername("updatedUser");

        // Step 3: Update the user
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + updatedUser.getUsername() + "\", \"role\": \"" + role + "\" }")
        ).andExpect(status().isOk());

        // Step 4: Check if the user was updated
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()));
    }

    @Test
    public void testChangePassword() throws Exception {
        // TODO: Implement this test

        // Step 1: Empty the database
        // Step 2: Create a user with password "password" - userService.createUser(username, password, roleName);
        // Step 3: Check if the user has password "password"
        // Step 4: Change the password to "newPassword" - userService.changeUserPassword(username, newPassword);

        // Step 1: Create a user
        String username = "newUser";
        String password = "password";
        String role = "ROLE_ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }")
                .param("role", "ROLE_ADMIN")
        ).andExpect(status().isOk());

        // Change the password
        String newPassword = "newPassword";
        mockMvc.perform(MockMvcRequestBuilders.post("/users/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"newPassword\": \"" + newPassword + "\" }")
        ).andExpect(status().isOk());

        // Check if the user has the new password

        // Perform GET request to /users
        ResultActions resultActions = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(jsonPath("$[0].password").value(newPassword));

    }

    @Test
    public void testDeleteUser() throws Exception {
        // TODO: Implement this test

        // Step 1: Empty the database
        // Step 2: Check if the first user in the database is null
        // Step 3: Create a new user
        // Step 4: Check if the first user in the database is not null
        // Step 5: Delete the user - userService.deleteUser(userId);
        // Step 6: Check if the first user in the database is null

        // Step 1: Create a user
        String username = "newUser";
        String password = "password";
        String role = "ROLE_ADMIN";

        // Create the user
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }")
                .param("role", "ROLE_ADMIN")
        ).andExpect(status().isOk());

        // Perform GET request to /users
        ResultActions resultActions = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").exists()) // Check if the first user has a username
                .andExpect(jsonPath("$[0].password").exists());// Ensure password is returned

        // Step 5: Delete the user
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        // Step 6: Check if the first user in the database is null
        resultActions = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").doesNotExist());

    }
}
