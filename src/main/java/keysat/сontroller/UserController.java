package keysat.сontroller;

import keysat.entities.Role;
import keysat.repository.RoleRepository;
import keysat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import keysat.UserService;
import keysat.entities.User;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('Admin')")
    public User createUser(@RequestBody User newUser, @RequestParam String role) {
        Role assignedRole = roleRepository.findByName(role.toUpperCase());

        if (assignedRole != null) {
            newUser.setRoles(Collections.singleton(assignedRole));
            return userService.createUser(newUser.getUsername(), newUser.getPassword());
        } else {
            return null;
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

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }
}