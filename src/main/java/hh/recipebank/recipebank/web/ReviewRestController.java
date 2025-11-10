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

import hh.recipebank.recipebank.domain.Review;
import hh.recipebank.recipebank.domain.ReviewRepository;
import hh.recipebank.recipebank.domain.Recipe;
import hh.recipebank.recipebank.domain.RecipeRepository;
import hh.recipebank.recipebank.domain.AppUser;
import hh.recipebank.recipebank.domain.AppUserRepository;

@CrossOrigin
@RestController
@SuppressWarnings("null")

public class ReviewRestController {

	// Fields
	private final ReviewRepository reviewRepository;
	private final RecipeRepository recipeRepository;
	private final AppUserRepository appUserRepository;

	// Constructors
	public ReviewRestController(ReviewRepository reviewRepository,
		RecipeRepository recipeRepository,
		AppUserRepository appUserRepository) {
		this.reviewRepository = reviewRepository;
		this.recipeRepository = recipeRepository;
		this.appUserRepository = appUserRepository;
	}

	// Endpoints
	@GetMapping("/api/reviews")
	public List<Review> getAllReviews() {
		return reviewRepository.findAll();
	}

	@GetMapping("/api/reviews/{id}")
	public Optional<Review> getReview(@PathVariable long id) {
		return reviewRepository.findById(id);
	}

	@PostMapping("/api/reviews")
	public Review createReview(@RequestBody Review review) {
		Recipe r = review.getRecipe();
		if (r != null && r.getRecipeId() != null) {
			recipeRepository.findById(r.getRecipeId()).ifPresent(review::setRecipe);
		}
		AppUser u = review.getUser();
		if (u != null && u.getUserId() != null) {
			appUserRepository.findById(u.getUserId()).ifPresent(review::setUser);
		}
		return reviewRepository.save(review);
	}

	@DeleteMapping("/api/reviews/{id}")
	public void deleteReview(@PathVariable long id) {
		reviewRepository.deleteById(id);
	}
}
