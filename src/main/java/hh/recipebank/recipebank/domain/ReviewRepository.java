package hh.recipebank.recipebank.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserAndRecipe(AppUser user, Recipe recipe);
}