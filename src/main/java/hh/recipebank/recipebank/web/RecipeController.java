package hh.recipebank.recipebank.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import hh.recipebank.recipebank.domain.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Controller
public class RecipeController {

	// Fields
	@Autowired
	private RecipeRepository recipeRepository;

	// Endpoints

	// List all recipes (public)
	@GetMapping("/")
	public String listRecipes(Model model) {
		model.addAttribute("recipes", recipeRepository.findAll());
		return "recipelist";
	}

	// Show form to add a new recipe (USER/ADMIN)
	@GetMapping("/addrecipe")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String addRecipeForm(Model model) {
		Recipe recipe = new Recipe();
		recipe.setIngredients(new ArrayList<>());
		recipe.getIngredients().add(new Ingredient()); // add one empty row by default
		model.addAttribute("recipe", recipe);
		return "addrecipe";
	}

	// Save recipe (create or update)
	@PostMapping("/addrecipe")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String saveRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult, Model model) {
		if (recipe == null) {
			throw new IllegalArgumentException("Recipe cannot be null");
		}

		// Server-side validation: return edit or add form accordingly
		if (bindingResult.hasErrors()) {
			// If editing an existing recipe, return edit view, otherwise add view
			if (recipe.getRecipeId() != null) {
				model.addAttribute("recipe", recipe);
				return "editrecipe";
			}
			model.addAttribute("recipe", recipe);
			return "addrecipe";
		}

		if (recipe.getIngredients() != null) {
			// Remove fully empty ingredient rows
			recipe.getIngredients().removeIf(i ->
				(i.getName() == null || i.getName().isBlank()) &&
				(i.getAmount() == null || i.getAmount().isBlank()) &&
				(i.getUnit() == null || i.getUnit().isBlank())
			);

			// Link each ingredient back to this recipe
			recipe.getIngredients().forEach(i -> i.setRecipe(recipe));
		}

		recipeRepository.save(recipe);
		return "redirect:/recipe/" + recipe.getRecipeId();
	}

	// Edit recipe (ADMIN)
	@GetMapping("/editrecipe/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String editRecipe(@PathVariable("id") Long id, Model model) {
		if (id == null || !recipeRepository.existsById(id)) {
			throw new NoSuchElementException("Invalid recipe ID: " + id);
		}
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Recipe not found: " + id));
		model.addAttribute("recipe", recipe);
		return "editrecipe";
	}

	// Delete recipe (ADMIN)
	@GetMapping("/deleterecipe/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteRecipe(@PathVariable("id") Long id) {
		if (id == null || !recipeRepository.existsById(id)) {
			throw new NoSuchElementException("Invalid recipe ID: " + id);
		}
		recipeRepository.deleteById(id);
		return "redirect:/";
	}

	// View single recipe
	@GetMapping("/recipe/{id}")
	public String viewRecipe(@PathVariable("id") Long id, Model model) {
		if (id == null || !recipeRepository.existsById(id)) {
			throw new NoSuchElementException("Invalid recipe ID: " + id);
		}
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Recipe not found: " + id));
		model.addAttribute("recipe", recipe);
		return "recipesingle";
	}
}
