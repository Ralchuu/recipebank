package hh.recipebank;

import static org.assertj.core.api.Assertions.assertThat;

import hh.recipebank.recipebank.web.RecipeController;
import hh.recipebank.recipebank.web.RecipeRestController;
import hh.recipebank.recipebank.web.ReviewController;
import hh.recipebank.recipebank.web.AppUserController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = hh.recipebank.recipebank.RecipebankApplication.class)
public class RecipebankApplicationTests {

	@Autowired private RecipeController recipeController;
	@Autowired private RecipeRestController recipeRestController;
	@Autowired private ReviewController reviewController;
	@Autowired private AppUserController appUserController;

	@Test
	public void contextLoads() {
		assertThat(recipeController).isNotNull();
		assertThat(recipeRestController).isNotNull();
		assertThat(reviewController).isNotNull();
		assertThat(appUserController).isNotNull();
	}
}
