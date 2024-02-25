package keysat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import keysat.entities.Role;

import keysat.entities.User;
import keysat.repository.RoleRepository;
import keysat.repository.UserRepository;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String password, String roleName) {
        log.info("Creating user with username: {}", username);
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);

        Role role = roleRepository.findByName(roleName);
        if (role != null) {
            user.getRoles().add(role);
        } else {
            throw new RuntimeException("Role not found: " + roleName);
         }
        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getUser_id());
        return savedUser;
    }

    public User changeUserPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return user;
        }
        // Handle the case where the user doesn't exist
        return null;
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}