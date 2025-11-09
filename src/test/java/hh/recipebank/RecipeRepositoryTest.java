package hh.recipebank;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.recipebank.recipebank.domain.Recipe;
import hh.recipebank.recipebank.domain.RecipeRepository;

@DataJpaTest
public class RecipeRepositoryTest {

	@Autowired
	private RecipeRepository repository;

	// Create recipe and assert it's persisted (no lambdas)
	@Test
	public void createNewRecipe() {
		Recipe r = new Recipe("Test Book", "Desc", "Do this", null);
		repository.save(r);

		List<Recipe> all = repository.findAll();
		boolean exists = false;
		for (Recipe rec : all) {
			if ("Test Book".equals(rec.getTitle())) {
				exists = true;
				break;
			}
		}
		assertThat(exists).isTrue();
	}

	// Save and delete recipe using repository.delete(object)
	@Test
	public void deleteRecipeShouldRemoveIt() {
		Recipe r = new Recipe("To Delete", "Desc", "Inst", null);
		repository.save(r);

		repository.delete(r);

		List<Recipe> all = repository.findAll();
		boolean exists = false;
		for (Recipe rec : all) {
			if ("To Delete".equals(rec.getTitle())) {
				exists = true;
				break;
			}
		}
		assertThat(exists).isFalse();
	}

	// Find by title via iteration rather than lambdas
	@Test
	public void findByIdShouldReturnRecipe() {
		Recipe r = new Recipe("Unique Title", "Desc", "Inst", null);
		repository.save(r);

		Recipe found = null;
		List<Recipe> all = repository.findAll();
		for (Recipe rec : all) {
			if ("Unique Title".equals(rec.getTitle())) {
				found = rec;
				break;
			}
		}
		assertThat(found).isNotNull();
		// Explicit null-check to satisfy analyzer
		if (found != null) {
			assertThat(found.getTitle()).isEqualTo("Unique Title");
		}
	}
}
