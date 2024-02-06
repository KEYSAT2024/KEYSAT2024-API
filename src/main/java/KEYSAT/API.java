package KEYSAT;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class API {

    @GetMapping("/")
    public String keysat() {
        return "KEYSAT";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/secret")
    public String secret() {
        return "secret";
    }

    }
