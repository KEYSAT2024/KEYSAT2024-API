package keysat.сontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import keysat.UserService;
import keysat.entities.User;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestParam String username, @RequestParam String password) {
        return userService.createUser(username, password);
    }

    @PostMapping("/change-password")
    public User changePassword(@RequestParam String username, @RequestParam String newPassword) {
        return userService.changeUserPassword(username, newPassword);
    }
}