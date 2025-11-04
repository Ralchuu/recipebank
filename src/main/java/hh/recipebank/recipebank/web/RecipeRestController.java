package hh.recipebank.recipebank.web;

import hh.recipebank.recipebank.domain.Recipe;
import hh.recipebank.recipebank.domain.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController {

    @Autowired
    private RecipeRepository recipeRepository;

    // Palauta kaikki reseptit
    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Palauta yksittäinen resepti ID:n perusteella
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable("id") Long id) {
        if (id == null || !recipeRepository.existsById(id)) {
            throw new NoSuchElementException("Invalid recipe ID: " + id);
        }
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found: " + id));
    }

    // Lisää uusi resepti
    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }
        if (recipe.getIngredients() != null) {
            recipe.getIngredients().forEach(i -> i.setRecipe(recipe));
        }
        return recipeRepository.save(recipe);
    }

    // Päivitä olemassa oleva resepti
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable("id") Long id, @RequestBody Recipe updatedRecipe) {
        if (id == null || !recipeRepository.existsById(id)) {
            throw new NoSuchElementException("Invalid recipe ID: " + id);
        }
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found: " + id));

        recipe.setTitle(updatedRecipe.getTitle());
        recipe.setDescription(updatedRecipe.getDescription());
        recipe.setInstruction(updatedRecipe.getInstruction());

        if (updatedRecipe.getIngredients() != null) {
            // Poistetaan vanhat ainesosat ja korvataan uusilla
            recipe.getIngredients().clear();
            updatedRecipe.getIngredients().forEach(i -> i.setRecipe(recipe));
            recipe.getIngredients().addAll(updatedRecipe.getIngredients());
        }

        return recipeRepository.save(recipe);
    }

    // Poista resepti
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable("id") Long id) {
        if (id == null || !recipeRepository.existsById(id)) {
            throw new NoSuchElementException("Invalid recipe ID: " + id);
        }
        recipeRepository.deleteById(id);
    }
}
