package keysat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import keysat.entities.User;

// MAGIC CODE BELOW PLS DO NOT TOUCH

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user details from the users table
        User user = jdbcTemplate.queryForObject(
            "SELECT username, password, enabled FROM users WHERE username=?",
            new Object[]{username},
            new BeanPropertyRowMapper<>(User.class)
        );

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Fetch authorities based on your custom logic
        List<GrantedAuthority> authorities = jdbcTemplate.query(
            "SELECT u.username, r.authority FROM users u " +
            "JOIN user_roles ur ON u.user_id = ur.user_id " +
            "JOIN role r ON ur.role_id = r.role_id WHERE u.username=?",
            new Object[]{username},
            (rs, rowNum) -> new SimpleGrantedAuthority(rs.getString("authority"))
        );

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            true, true, true,
            authorities
        );
    }
}