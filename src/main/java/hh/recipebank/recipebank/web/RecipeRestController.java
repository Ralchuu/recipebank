package hh.recipebank.recipebank.web;

import hh.recipebank.recipebank.domain.Recipe;
import hh.recipebank.recipebank.domain.RecipeRepository;
import hh.recipebank.recipebank.domain.Ingredient;
import hh.recipebank.recipebank.domain.Review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/recipes")
@SuppressWarnings("null")
public class RecipeRestController {

	private final RecipeRepository recipeRepository;

	public RecipeRestController(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	// Palauta kaikki reseptit
	@GetMapping
	public ResponseEntity<List<Recipe>> getAllRecipes() {
		return ResponseEntity.ok(recipeRepository.findAll());
	}

	// Palauta yksittäinen resepti ID:n perusteella
	@GetMapping("/{id}")
	public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
		return recipeRepository.findById(id)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	// Palauta reseptin ainesosat
	@GetMapping("/{id}/ingredients")
	public ResponseEntity<List<Ingredient>> getRecipeIngredients(@PathVariable long id) {
		return recipeRepository.findById(id)
			.map(r -> ResponseEntity.ok(r.getIngredients()))
			.orElse(ResponseEntity.notFound().build());
	}

	// Palauta reseptin arvostelut
	@GetMapping("/{id}/reviews")
	public ResponseEntity<List<Review>> getRecipeReviews(@PathVariable long id) {
		return recipeRepository.findById(id)
			.map(r -> ResponseEntity.ok(r.getReviews()))
			.orElse(ResponseEntity.notFound().build());
	}

	// Lisää uusi resepti
	@PostMapping
	public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
		if (recipe == null) {
			return ResponseEntity.badRequest().build();
		}
		// Alustaa ainesosaluettelon, jos se on tyhj
		if (recipe.getIngredients() != null) {
			recipe.getIngredients().forEach(i -> i.setRecipe(recipe));
		}
		Recipe saved = recipeRepository.save(recipe);
		return ResponseEntity.created(URI.create("/api/recipes/" + saved.getRecipeId())).body(saved);
	}

	// Päivitä olemassa oleva resepti
	@PutMapping("/{id}")
	public ResponseEntity<Recipe> updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe updated) {
		return recipeRepository.findById(id).map(existing -> {
			existing.setTitle(updated.getTitle());
			existing.setDescription(updated.getDescription());
			existing.setInstruction(updated.getInstruction());

			// Korvataan ainesosat, jos ne on annettu
			if (updated.getIngredients() != null) {
				existing.getIngredients().clear();
				updated.getIngredients().forEach(i -> i.setRecipe(existing));
				existing.getIngredients().addAll(updated.getIngredients());
			}
			Recipe saved = recipeRepository.save(existing);
			return ResponseEntity.ok(saved);
		}).orElse(ResponseEntity.notFound().build());
	}

	// Poista resepti
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
		if (!recipeRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		recipeRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// Valinnainen yksinkertainen ping-testi (poista kommentointi tarvittaessa)
	// @GetMapping("/ping")
	// public String ping() { return "recipes-api-ok"; }
}
