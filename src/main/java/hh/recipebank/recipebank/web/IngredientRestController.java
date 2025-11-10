package hh.recipebank.recipebank.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize; 
import hh.recipebank.recipebank.domain.Ingredient;
import hh.recipebank.recipebank.domain.IngredientRepository;
import hh.recipebank.recipebank.domain.Recipe;
import hh.recipebank.recipebank.domain.RecipeRepository;

@CrossOrigin
@RestController
@SuppressWarnings("null")
public class IngredientRestController {

	// Fields
	private final IngredientRepository ingredientRepository;
	private final RecipeRepository recipeRepository;

	// Constructors
	public IngredientRestController(IngredientRepository ingredientRepository,
		RecipeRepository recipeRepository) {
		this.ingredientRepository = ingredientRepository;
		this.recipeRepository = recipeRepository;
	}

	// GET: public list of ingredients
	@GetMapping("/api/ingredients")
	public List<Ingredient> getAllIngredients() {
		return ingredientRepository.findAll();
	}

	// GET: public single ingredient
	@GetMapping("/api/ingredients/{id}")
	public Optional<Ingredient> getIngredient(@PathVariable long id) {
		return ingredientRepository.findById(id);
	}

	// POST: create ingredient (ADMIN only)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/ingredients")
	public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
		Recipe r = ingredient.getRecipe();
		if (r != null && r.getRecipeId() != null) {
			recipeRepository.findById(r.getRecipeId()).ifPresent(ingredient::setRecipe);
		}
		return ingredientRepository.save(ingredient);
	}

	// DELETE: remove ingredient (ADMIN only)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/ingredients/{id}")
	public void deleteIngredient(@PathVariable long id) {
		ingredientRepository.deleteById(id);
	}
}
