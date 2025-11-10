package hh.recipebank.recipebank.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hh.recipebank.recipebank.domain.AppUser;
import hh.recipebank.recipebank.domain.AppUserRepository;

@CrossOrigin
@RestController
@SuppressWarnings("null")
public class AppUserRestController {

	// Fields
	private final AppUserRepository appUserRepository;

	// Constructors
	public AppUserRestController(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	// Endpoints
	@GetMapping("/api/users")
	public List<AppUser> getAllUsers() {
		return appUserRepository.findAll();
	}

	@GetMapping("/api/users/{id}")
	public Optional<AppUser> getUser(@PathVariable long id) {
		return appUserRepository.findById(id);
	}

	@PostMapping("/api/users")
	public AppUser createUser(@RequestBody AppUser user) {
		return appUserRepository.save(user);
	}

	@DeleteMapping("/api/users/{id}")
	public void deleteUser(@PathVariable long id) {
		appUserRepository.deleteById(id);
	}
}
