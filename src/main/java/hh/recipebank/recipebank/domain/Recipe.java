package hh.recipebank.recipebank.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Recipe {

	// Fields
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

	// Relationships
	@Valid
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("recipe-ingredients")
	private List<Ingredient> ingredients = new ArrayList<>();
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("recipe-reviews")
	private List<Review> reviews = new ArrayList<>();

	// Constructors
	public Recipe() {}
	public Recipe(String title, String description, String instruction, List<Ingredient> ingredients) {
		this.title = title;
		this.description = description;
		this.instruction = instruction;
		this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
	}

	// Getters and setters
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
	public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

	public List<Review> getReviews() { return reviews; }
	public void setReviews(List<Review> reviews) { this.reviews = reviews; }

	// toString
	@Override
	public String toString() {
		return "Recipe [recipeId=" + recipeId + ", title=" + title + ", description=" + description + "]";
	}
}
