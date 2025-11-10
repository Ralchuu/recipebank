package hh.recipebank.recipebank.web;

import hh.recipebank.recipebank.domain.Review;
import hh.recipebank.recipebank.domain.Recipe;
import hh.recipebank.recipebank.domain.ReviewRepository;
import hh.recipebank.recipebank.domain.RecipeRepository;
import hh.recipebank.recipebank.domain.AppUser;
import hh.recipebank.recipebank.domain.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/reviews") // all review paths start with /reviews
public class ReviewController {

	// Fields
	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private AppUserRepository userRepository;

	// Endpoints

	// Show form to add a review for a recipe: /reviews/add/{recipeId}
	@GetMapping("/add/{recipeId}")
	public String addReviewForm(@PathVariable("recipeId") long recipeId, Model model,
	                            @AuthenticationPrincipal UserDetails userDetails,
	                            RedirectAttributes redirectAttributes) {
		if (!recipeRepository.existsById(recipeId)) {
			throw new NoSuchElementException("Invalid recipe ID: " + recipeId);
		}
		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new NoSuchElementException("Recipe not found: " + recipeId));

		AppUser user = userRepository.findByUsername(userDetails.getUsername())
				.orElseThrow(() -> new NoSuchElementException("User not found"));

		// Check if the user has already reviewed this recipe
		if (reviewRepository.findByUserAndRecipe(user, recipe).isPresent()) {
			redirectAttributes.addFlashAttribute("error", "Reseptin voi arvostella vain kerran");
			return "redirect:/recipe/" + recipeId;
		}

		Review review = new Review();
		review.setRecipe(recipe);
		review.setUser(user);
		model.addAttribute("review", review);
		model.addAttribute("recipe", recipe);
		return "addreview";
	}

	// Save a new review: POST /reviews/save/{recipeId}
	@PostMapping("/save/{recipeId}")
	public String saveReview(@PathVariable long recipeId,
	                         @Valid @ModelAttribute("review") Review review,
	                         BindingResult bindingResult,
	                         Model model,
	                         @AuthenticationPrincipal UserDetails userDetails,
	                         RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			Recipe recipe = recipeRepository.findById(recipeId)
					.orElseThrow(() -> new NoSuchElementException("Invalid recipe ID: " + recipeId));
			model.addAttribute("recipe", recipe);
			return "addreview";
		}
		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new NoSuchElementException("Invalid recipe ID: " + recipeId));
		review.setRecipe(recipe);

		AppUser user = userRepository.findByUsername(userDetails.getUsername())
				.orElseThrow(() -> new NoSuchElementException("User not found"));

		// Check if user has already reviewed this recipe
		if (reviewRepository.findByUserAndRecipe(user, review.getRecipe()).isPresent()) {
			redirectAttributes.addFlashAttribute("error", "Olet jo arvostellut tämän reseptin!");
			return "redirect:/recipe/" + review.getRecipe().getRecipeId();
		}

		review.setUser(user);
		reviewRepository.save(review);
		redirectAttributes.addFlashAttribute("success", "Arvostelu tallennettu onnistuneesti!");
		return "redirect:/recipe/" + review.getRecipe().getRecipeId();
	}

	// Save a new review: POST /reviews/save (resolve recipeId from form)
	@PostMapping("/save")
	public String saveReviewNoPathId(@Valid @ModelAttribute("review") Review review,
	                                 BindingResult bindingResult,
	                                 Model model,
	                                 @AuthenticationPrincipal UserDetails userDetails,
	                                 @RequestParam(value = "recipeId", required = false) Long recipeIdParam,
	                                 RedirectAttributes redirectAttributes) {

		// Resolve recipeId from request param or nested review.recipe.recipeId
		Long computedRecipeId = recipeIdParam;
		if (computedRecipeId == null && review.getRecipe() != null) {
			computedRecipeId = review.getRecipe().getRecipeId();
		}
		if (computedRecipeId == null) {
			throw new NoSuchElementException("Recipe id missing for review save");
		}
		// Make it effectively final for lambda usage
		final long recipeId = computedRecipeId;

		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new NoSuchElementException("Invalid recipe ID: " + recipeId));

		if (bindingResult.hasErrors()) {
			model.addAttribute("recipe", recipe);
			return "addreview";
		}

		// Attach recipe and current user
		review.setRecipe(recipe);
		AppUser user = userRepository.findByUsername(userDetails.getUsername())
				.orElseThrow(() -> new NoSuchElementException("User not found"));

		// Allow only one review per user per recipe
		if (reviewRepository.findByUserAndRecipe(user, recipe).isPresent()) {
			redirectAttributes.addFlashAttribute("error", "Olet jo arvostellut tämän reseptin!");
			return "redirect:/recipe/" + recipeId;
		}

		review.setUser(user);
		reviewRepository.save(review);
		redirectAttributes.addFlashAttribute("success", "Arvostelu tallennettu onnistuneesti!");
		return "redirect:/recipe/" + recipeId;
	}

	// Delete review: GET /reviews/delete/{id}
	@GetMapping("/delete/{id}")
	public String deleteReview(@PathVariable long id) {
		if (!reviewRepository.existsById(id)) {
			throw new NoSuchElementException("Invalid review ID: " + id);
		}
		Long recipeId = reviewRepository.findById(id)
				.map(r -> r.getRecipe() != null ? r.getRecipe().getRecipeId() : null)
				.orElse(null);
		reviewRepository.deleteById(id);
		return recipeId != null ? "redirect:/recipe/" + recipeId : "redirect:/";
	}
}
