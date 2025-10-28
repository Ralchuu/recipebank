package hh.recipebank.recipebank.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Recipe {
    private Long recipeId;
    private String title;
    private String description;
    private String instruction;
    private LocalDateTime createdAt;
    private List<Ingredient> ingredients;

    // Constructors

    public Recipe(String title, String description, String instruction, LocalDateTime createdAt,
            List<Ingredient> ingredients) {
        this.title = title;
        this.description = description;
        this.instruction = instruction;
        this.createdAt = createdAt;
        this.ingredients = ingredients;
    }

    public Recipe() {
    }

    // Getters and Setters

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe [recipeId=" + recipeId + ", title=" + title + ", description=" + description + ", instruction="
                + instruction + ", createdAt=" + createdAt + "]";
    }

}
