package hh.recipebank;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.recipebank.recipebank.domain.Ingredient;
import hh.recipebank.recipebank.domain.IngredientRepository;
import hh.recipebank.recipebank.domain.Recipe;
import hh.recipebank.recipebank.domain.RecipeRepository;

@DataJpaTest
public class IngredientRepositoryTest {

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	// Create ingredient linked to a recipe
	@Test
	public void createIngredientLinkedToRecipe() {
		Recipe r = new Recipe("ParentRecipe", "Desc", "Inst", null);
		recipeRepository.save(r);

		Ingredient ing = new Ingredient();
		ing.setName("Carrot");
		ing.setAmount("2");
		ing.setUnit("pcs");
		ing.setRecipe(r);
		ingredientRepository.save(ing);

		List<Ingredient> list = ingredientRepository.findAll();
		boolean found = false;
		for (Ingredient i : list) {
			if ("Carrot".equals(i.getName())) {
				found = true;
				break;
			}
		}
		assertThat(found).isTrue();
	}
}
