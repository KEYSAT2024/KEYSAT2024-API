package keysat.prototype.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import keysat.Auth.User;
import keysat.Auth.UserRepository;

import java.util.Optional;
@SpringBootTest
class KeysatAlphaApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testDatabaseConnection() {
		// Create an instance of UserRepository
		UserRepository userRepositoryInstance = userRepository;

		// Test database connection by retrieving a user from the database
		Optional<User> user = userRepositoryInstance.findById(1L);
		if (user.isPresent()) {
			System.out.println("User found: " + user.get().getUsername());
		} else {
			System.out.println("User not found");
		}
	}

}
