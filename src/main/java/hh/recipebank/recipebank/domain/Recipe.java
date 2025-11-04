package hh.recipebank.recipebank.domain;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private String title;
    private String description;
    private String instruction;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
private List<Ingredient> ingredients;


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
}
