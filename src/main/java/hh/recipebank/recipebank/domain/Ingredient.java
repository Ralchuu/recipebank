package hh.recipebank.recipebank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @NotBlank(message = "Ainesosan nimi on pakollinen")
    private String name;
    private String amount;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String name, String amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public Ingredient(String name, String amount, String unit, Recipe recipe) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.recipe = recipe;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
