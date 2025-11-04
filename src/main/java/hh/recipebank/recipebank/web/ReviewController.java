package hh.recipebank.recipebank.web;

import hh.recipebank.recipebank.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String addReviewForm(@PathVariable("recipeId") Long recipeId, Model model) {
        if (recipeId == null || !recipeRepository.existsById(recipeId)) {
            throw new NoSuchElementException("Invalid recipe ID: " + recipeId);
        }
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found: " + recipeId));
        Review review = new Review();
        review.setRecipe(recipe);
        model.addAttribute("review", review);
        return "addreview";
    }

    @PostMapping("/save")
    public String saveReview(@ModelAttribute Review review) {
        if (review == null || review.getRecipe() == null || review.getRecipe().getRecipeId() == null) {
            throw new IllegalArgumentException("Review or recipe cannot be null");
        }
        reviewRepository.save(review);
        return "redirect:/recipe/" + review.getRecipe().getRecipeId();
    }

    // Poista review
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        if (id == null || !reviewRepository.existsById(id)) {
            throw new NoSuchElementException("Invalid review ID: " + id);
        }
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Review not found: " + id));
        Long recipeId = review.getRecipe().getRecipeId();
        reviewRepository.deleteById(id);
        return "redirect:/recipe/" + recipeId;
    }
}
