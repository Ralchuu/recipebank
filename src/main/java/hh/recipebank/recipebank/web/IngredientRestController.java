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

	private final IngredientRepository ingredientRepository;
	private final RecipeRepository recipeRepository;

	public IngredientRestController(IngredientRepository ingredientRepository,
		RecipeRepository recipeRepository) {
		this.ingredientRepository = ingredientRepository;
		this.recipeRepository = recipeRepository;
	}

	// list all ingredients
	@GetMapping("/api/ingredients")
	public List<Ingredient> getAllIngredients() {
		return ingredientRepository.findAll();
	}

	// get ingredient by id
	@GetMapping("/api/ingredients/{id}")
	public Optional<Ingredient> getIngredient(@PathVariable long id) {
		return ingredientRepository.findById(id);
	}

	// create ingredient (optionally with recipeId)
	@PostMapping("/api/ingredients")
	public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
		// if JSON contains recipeId: { "name":"X","amount":"1","unit":"pcs","recipe":{"recipeId":5}}
		Recipe r = ingredient.getRecipe();
		if (r != null && r.getRecipeId() != null) {
			recipeRepository.findById(r.getRecipeId()).ifPresent(ingredient::setRecipe);
		}
		return ingredientRepository.save(ingredient);
	}

	// delete ingredient
	@DeleteMapping("/api/ingredients/{id}")
	public void deleteIngredient(@PathVariable long id) {
		ingredientRepository.deleteById(id);
	}
}
