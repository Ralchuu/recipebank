package hh.recipebank.recipebank.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import hh.recipebank.recipebank.domain.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Controller
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    // Listaa reseptit
    @GetMapping("/")
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeRepository.findAll());
        return "recipelist";
    }

    // Näytä lomake uuden reseptin lisäämiseksi
    @GetMapping("/addrecipe")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String addRecipeForm(Model model) {
        Recipe recipe = new Recipe();
        recipe.setIngredients(new ArrayList<>());
        recipe.getIngredients().add(new Ingredient()); // yksi tyhjä rivi valmiiksi
        model.addAttribute("recipe", recipe);
        return "addrecipe";
    }

    // Tallenna resepti
    @PostMapping("/addrecipe")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String saveRecipe(@ModelAttribute("recipe") Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }

        if (recipe.getIngredients() != null) {
            // Poistetaan tyhjät rivit
            recipe.getIngredients().removeIf(i ->
                (i.getName() == null || i.getName().isBlank()) &&
                (i.getAmount() == null || i.getAmount().isBlank()) &&
                (i.getUnit() == null || i.getUnit().isBlank())
            );

            // Liitetään resepti kaikille ainesosille
            recipe.getIngredients().forEach(i -> i.setRecipe(recipe));
        }

        recipeRepository.save(recipe);
        return "redirect:/recipe/" + recipe.getRecipeId();
    }

    // Muokkaa reseptiä
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

    // Poista resepti
    @GetMapping("/deleterecipe/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRecipe(@PathVariable("id") Long id) {
        if (id == null || !recipeRepository.existsById(id)) {
            throw new NoSuchElementException("Invalid recipe ID: " + id);
        }
        recipeRepository.deleteById(id);
        return "redirect:/";
    }

    // Näytä yksittäinen resepti
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
