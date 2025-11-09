package hh.recipebank;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.recipebank.recipebank.domain.Review;
import hh.recipebank.recipebank.domain.ReviewRepository;
import hh.recipebank.recipebank.domain.Recipe;
import hh.recipebank.recipebank.domain.RecipeRepository;

@DataJpaTest
public class ReviewRepositoryTest {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	// Create a review linked to a recipe and verify it is persisted (no lambdas)
	@Test
	public void createReviewLinkedToRecipe() {
		Recipe r = new Recipe("ReviewParent", "Desc", "Inst", null);
		recipeRepository.save(r);

		Review rev = new Review();
		rev.setRating(5);
		rev.setComment("Great");
		rev.setRecipe(r);
		reviewRepository.save(rev);

		List<Review> list = reviewRepository.findAll();
		boolean found = false;
		for (Review rv : list) {
			if (rv.getRecipe() != null && r.getRecipeId().equals(rv.getRecipe().getRecipeId())) {
				found = true;
				break;
			}
		}
		assertThat(found).isTrue();
	}
}
