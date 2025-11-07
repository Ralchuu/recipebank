package hh.recipebank.recipebank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String comment;

    @Min(value = 1, message = "Arvosanan on oltava vähintään 1")
    @Max(value = 5, message = "Arvosanan on oltava enintään 5")
    private int rating; // esim. 1–5

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public Review() {}

    public Long getReviewId() { return reviewId; }
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
}
