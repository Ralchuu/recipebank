package hh.recipebank.recipebank.domain;

public class Ingredient {
    
private long ingredientId;
private String name;
private String amount;
private String unit;

// Constructors

public Ingredient(String name, String amount, String unit) {
    this.name = name;
    this.amount = amount;
    this.unit = unit;
}

public Ingredient() {
}  

// Getters and Setters

public long getIngredientId() {
    return ingredientId;
}
public void setIngredientId(long ingredientId) {
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
}

