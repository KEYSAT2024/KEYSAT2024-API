package keysat.config;

import keysat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity

public class Auth {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); 
}

	@Bean

	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic() // Enable Basic Authentication
				.and()
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/", "/home").permitAll()
						.requestMatchers("/secret").hasRole("USER")
						.requestMatchers("/teachersecret").hasRole("TEACHER")
						.requestMatchers("/users/create").permitAll()
						.requestMatchers("/users").permitAll())
				.csrf().disable(); // It's common to disable CSRF for APIs, but consider your security requirements

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
		return manager;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
				.authoritiesByUsernameQuery("SELECT username, authority FROM user_roles WHERE username=?");
	}
}