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

	// Endpoints
	@GetMapping("/api/ingredients")
	public List<Ingredient> getAllIngredients() {
		return ingredientRepository.findAll();
	}

	@GetMapping("/api/ingredients/{id}")
	public Optional<Ingredient> getIngredient(@PathVariable long id) {
		return ingredientRepository.findById(id);
	}

	@PostMapping("/api/ingredients")
	public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
		Recipe r = ingredient.getRecipe();
		if (r != null && r.getRecipeId() != null) {
			recipeRepository.findById(r.getRecipeId()).ifPresent(ingredient::setRecipe);
		}
		return ingredientRepository.save(ingredient);
	}

	@DeleteMapping("/api/ingredients/{id}")
	public void deleteIngredient(@PathVariable long id) {
		ingredientRepository.deleteById(id);
	}
}
