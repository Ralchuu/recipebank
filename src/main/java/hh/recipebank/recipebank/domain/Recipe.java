package hh.recipebank.recipebank.domain;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @NotBlank(message = "Otsikko on pakollinen")
    @Size(max = 150, message = "Otsikon pituus saa olla enintään 150 merkkiä")
    private String title;

    @NotBlank(message = "Kuvaus on pakollinen")
    @Size(max = 150, message = "Kuvauksen pituus saa olla enintään 150 merkkiä")
    private String description;

    @NotBlank(message = "Ohje on pakollinen")
    private String instruction;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Valid
    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;


    public Recipe() {}

    public Recipe(String title, String description, String instruction, List<Ingredient> ingredients) {
        this.title = title;
        this.description = description;
        this.instruction = instruction;
        this.ingredients = ingredients;
        this.createdAt = LocalDateTime.now();
    }

    public Long getRecipeId() { return recipeId; }
    public void setRecipeId(Long recipeId) { this.recipeId = recipeId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstruction() { return instruction; }
    public void setInstruction(String instruction) { this.instruction = instruction; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        if (ingredients != null) {
            ingredients.forEach(i -> i.setRecipe(this));
        }
    }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) {
        // If the incoming value is null (common when the edit form doesn't include reviews),
        // do not overwrite the existing reviews list — preserves existing review entities.
        if (reviews == null) {
            return;
        }
        this.reviews = reviews;
        reviews.forEach(r -> r.setRecipe(this));
    }
}
