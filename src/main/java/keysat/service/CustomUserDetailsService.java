package keysat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import keysat.entities.User;

// MAGIC CODE BELOW PLS DO NOT TOUCH
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private JdbcClient jdbcClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = jdbcClient
                .sql("SELECT username, password, enabled FROM user WHERE username = :username;")
                .param("username", username)
                .query(User.class)
                .optional();

        return userOptional.map(user -> {
            List<SimpleGrantedAuthority> authorities = jdbcClient
                    .sql(
                            "SELECT u.username, r.authority FROM user u " +
                            "JOIN user_role ur ON u.user_id = ur.user_id " +
                            "JOIN role r ON ur.role_id = r.role_id WHERE u.username = :username;"
                    )
                    .param("username", username)
                    .query(
                            (rs, rowNum) -> new SimpleGrantedAuthority(
                                    rs.getString("authority")
                            )
                    )
                    .list();

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    true, true, true,
                    authorities
            );
        }).orElse(null);
    }
}