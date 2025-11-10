package hh.recipebank.recipebank.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
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

	// GET: all reviews (public)
	@GetMapping("/api/reviews")
	public List<Review> getAllReviews() {
		return reviewRepository.findAll();
	}

	// GET: single review (public)
	@GetMapping("/api/reviews/{id}")
	public ResponseEntity<Review> getReview(@PathVariable long id) {
		return reviewRepository.findById(id)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	// POST: create review (USER or ADMIN)
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/api/reviews")
	public ResponseEntity<Review> createReview(@RequestBody Review review) {
		// Attach recipe if provided
		Recipe r = review.getRecipe();
		if (r != null && r.getRecipeId() != null) {
			Optional<Recipe> recipeOpt = recipeRepository.findById(r.getRecipeId());
			recipeOpt.ifPresent(review::setRecipe);
		} else {
			return ResponseEntity.badRequest().build();
		}
		// Attach user if provided (optional)
		AppUser u = review.getUser();
		if (u != null && u.getUserId() != null) {
			appUserRepository.findById(u.getUserId()).ifPresent(review::setUser);
		}

		Review saved = reviewRepository.save(review);
		return ResponseEntity.ok(saved);
	}

	// DELETE: remove review (ADMIN only)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/reviews/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable long id) {
		if (!reviewRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		reviewRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
