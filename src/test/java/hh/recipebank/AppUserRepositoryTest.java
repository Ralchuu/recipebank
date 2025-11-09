package hh.recipebank;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.recipebank.recipebank.domain.AppUser;
import hh.recipebank.recipebank.domain.AppUserRepository;

@DataJpaTest
public class AppUserRepositoryTest {

	@Autowired
	private AppUserRepository repository;

	// Create new user and verify repository contains it (no try/catch, no lambdas)
	@Test
	public void createNewUser() {
		AppUser user = new AppUser();
		user.setUsername("testuser");
		user.setPassword("passhash");
		user.setEmail("testuser@example.test");
		user.setRole("USER");

		repository.save(user);

		List<AppUser> all = repository.findAll();
		boolean found = false;
		for (AppUser u : all) {
			if ("testuser".equals(u.getUsername())) {
				found = true;
				break;
			}
		}
		assertThat(found).isTrue();
	}

	// Save then delete user and verify removal (no try/catch, no lambdas)
	@Test
	public void deleteUserShouldRemoveIt() {
		AppUser user = new AppUser();
		user.setUsername("deleteuser");
		user.setPassword("passhash");
		user.setEmail("delete@example.test");
		user.setRole("USER");

		repository.save(user);

		repository.delete(user);

		List<AppUser> all = repository.findAll();
		boolean exists = false;
		for (AppUser u : all) {
			if ("deleteuser".equals(u.getUsername())) {
				exists = true;
				break;
			}
		}
		assertThat(exists).isFalse();
	}

	// Find by username via simple iteration (no repository-specific methods required)
	@Test
	public void findByUsernameShouldReturnUser() {
		AppUser user = new AppUser();
		user.setUsername("findme");
		user.setPassword("passhash");
		user.setEmail("findme@example.test");
		user.setRole("USER");

		repository.save(user);

		AppUser found = null;
		List<AppUser> all = repository.findAll();
		for (AppUser u : all) {
			if ("findme".equals(u.getUsername())) {
				found = u;
				break;
			}
		}

		assertThat(found).isNotNull();
		// Explicit null-check to satisfy analyzer
		if (found != null) {
			assertThat(found.getUsername()).isEqualTo("findme");
		}
	}
}
