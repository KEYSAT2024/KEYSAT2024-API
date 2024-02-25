package keysat.controller;

import keysat.entities.Role;
import keysat.repository.RoleRepository;
import keysat.repository.UserRepository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import keysat.UserService;
import keysat.entities.User;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

     private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @GetMapping("/{userId}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody User newUser, @RequestParam String role) {
        log.info("Creating user with role: {}", role);
        Role assignedRole = roleRepository.findByName(role.toUpperCase());

        if (assignedRole != null) {
            newUser.setRoles(Collections.singleton(assignedRole));
            User createdUser = userService.createUser(newUser.getUsername(), newUser.getPassword(), 
                    role.toUpperCase());
            log.info("User created successfully with username: {}", newUser.getUsername());
            return ResponseEntity.ok(createdUser);
        } else {
            log.error("Role not found: {}", role);
            return ResponseEntity.badRequest().body("Role not found");
        }
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(updatedUser.getUsername());

        return ResponseEntity.ok(userRepository.save(existingUser));
    }

    @PostMapping("/change-password")
    public User changePassword(@RequestParam String username, @RequestParam String newPassword) {
        return userService.changeUserPassword(username, newPassword);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}