package hh.recipebank.recipebank.web;

import hh.recipebank.recipebank.domain.*;
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
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private AppUserRepository userRepository;

    // Lisää review tietylle reseptille
    @GetMapping("/add/{recipeId}")
    public String addReviewForm(@PathVariable("recipeId") Long recipeId, Model model, 
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        if (recipeId == null || !recipeRepository.existsById(recipeId)) {
            throw new NoSuchElementException("Invalid recipe ID: " + recipeId);
        }
        
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found: " + recipeId));
                
        AppUser user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Tarkista onko käyttäjä jo arvostellut reseptin
        if (reviewRepository.findByUserAndRecipe(user, recipe).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Reseptin voi arvostella vain kerran");
            return "redirect:/recipe/" + recipeId;
        }

        Review review = new Review();
        review.setRecipe(recipe);
        review.setUser(user);
        model.addAttribute("review", review);
        return "addreview";
    }

    @PostMapping("/save")
    public String saveReview(@Valid @ModelAttribute Review review, BindingResult result, 
            @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        if (review == null || review.getRecipe() == null || review.getRecipe().getRecipeId() == null) {
            throw new IllegalArgumentException("Review or recipe cannot be null");
        }

        if (result.hasErrors()) {
            return "addreview";
        }

        AppUser user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Tarkista uudelleen onko käyttäjä jo arvostellut reseptin
        if (reviewRepository.findByUserAndRecipe(user, review.getRecipe()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Olet jo arvostellut tämän reseptin!");
            return "redirect:/recipe/" + review.getRecipe().getRecipeId();
        }

        review.setUser(user);
        reviewRepository.save(review);
        redirectAttributes.addFlashAttribute("success", "Arvostelu tallennettu onnistuneesti!");
        return "redirect:/recipe/" + review.getRecipe().getRecipeId();
    }

    // Poista review
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        System.out.println("Deleting review with ID: " + id);
        
        if (id == null || !reviewRepository.existsById(id)) {
            System.out.println("Review not found with ID: " + id);
            throw new NoSuchElementException("Invalid review ID: " + id);
        }
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Review not found: " + id));
                
        System.out.println("Found review for recipe: " + review.getRecipe().getTitle());
        Long recipeId = review.getRecipe().getRecipeId();
        
        // Irrotetaan arvostelu reseptistä ennen poistoa
        Recipe recipe = review.getRecipe();
        if (recipe.getReviews() != null) {
            recipe.getReviews().remove(review);
        }
        review.setRecipe(null);
        review.setUser(null);
        
        reviewRepository.delete(review);
        System.out.println("Review deleted successfully");
        
        return "redirect:/recipe/" + recipeId;
    }
}
