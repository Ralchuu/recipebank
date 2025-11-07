package hh.recipebank.recipebank;

import hh.recipebank.recipebank.domain.*;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RecipebankApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipebankApplication.class, args);
    }

    @Bean
public CommandLineRunner loadTestData(
        AppUserRepository userRepository,
        ReviewRepository reviewRepository,
        PasswordEncoder passwordEncoder,
        RecipeRepository recipeRepository,
        IngredientRepository ingredientRepository
) {
    return args -> {

        // USERS
        if (!userRepository.existsByUsername("admin")) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("user")) {
            AppUser user = new AppUser();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setEmail("user@example.com");
            user.setRole("ROLE_USER");
            userRepository.save(user);
        }

        // RECIPES + INGREDIENTS
        if (recipeRepository.count() == 0) {

            Recipe r1 = new Recipe();
            r1.setTitle("Makaronilaatikko");
            r1.setDescription("Uunissa kypsytettävä suomalainen klassikko");
            r1.setInstruction("Keitä makaroni, ruskista jauheliha, sekoita maito ja munat, paista uunissa");
            r1.setIngredients(new java.util.ArrayList<>());
            recipeRepository.save(r1);

            ingredientRepository.save(new Ingredient("Makaroni", "400", "g", r1));
            ingredientRepository.save(new Ingredient("Jauheliha", "400", "g", r1));
            ingredientRepository.save(new Ingredient("Maito", "5", "dl", r1));
            ingredientRepository.save(new Ingredient("Kananmuna", "2", "kpl", r1));

            Recipe r2 = new Recipe();
            r2.setTitle("Pannukakku");
            r2.setDescription("Perinteinen suomalainen uunipannari");
            r2.setInstruction("Sekoita taikina, kaada pellille, paista uunissa");
            r2.setIngredients(new java.util.ArrayList<>());
            recipeRepository.save(r2);

            ingredientRepository.save(new Ingredient("Maito", "1", "l", r2));
            ingredientRepository.save(new Ingredient("Kananmuna", "4", "kpl", r2));
            ingredientRepository.save(new Ingredient("Vehnäjauhot", "4", "dl", r2));

                        Recipe r3 = new Recipe();
            r3.setTitle("Lohikeitto");
            r3.setDescription("Kermainen perinteinen suomalainen lohikeitto");
            r3.setInstruction("Kuori ja keitä perunat, lisää lohi ja kerma, mausta tillillä");
            r3.setIngredients(new java.util.ArrayList<>());
            recipeRepository.save(r3);

            ingredientRepository.save(new Ingredient("Peruna", "600", "g", r3));
            ingredientRepository.save(new Ingredient("Lohifile", "400", "g", r3));
            ingredientRepository.save(new Ingredient("Kerma", "2", "dl", r3));
            ingredientRepository.save(new Ingredient("Vesi", "1", "l", r3));
            ingredientRepository.save(new Ingredient("Tilli", "1", "nippu", r3));

            Recipe r4 = new Recipe();
            r4.setTitle("Kanakastike ja riisi");
            r4.setDescription("Helppo ja maukas arkiruoka");
            r4.setInstruction("Paista kana, lisää kerma ja mausteet, tarjoa riisin kanssa");
            r4.setIngredients(new java.util.ArrayList<>());
            recipeRepository.save(r4);

            ingredientRepository.save(new Ingredient("Kananrintafile", "400", "g", r4));
            ingredientRepository.save(new Ingredient("Kerma", "2", "dl", r4));
            ingredientRepository.save(new Ingredient("Riisi", "3", "dl", r4));
            ingredientRepository.save(new Ingredient("Sipuli", "1", "kpl", r4));

            Recipe r5 = new Recipe();
            r5.setTitle("Kasviswokki");
            r5.setDescription("Nopea ja terveellinen kasvisruoka pannulla");
            r5.setInstruction("Wokkaa vihannekset, lisää soijakastike ja tarjoile");
            r5.setIngredients(new java.util.ArrayList<>());
            recipeRepository.save(r5);

            ingredientRepository.save(new Ingredient("Paprika", "2", "kpl", r5));
            ingredientRepository.save(new Ingredient("Porkkana", "3", "kpl", r5));
            ingredientRepository.save(new Ingredient("Sipuli", "1", "kpl", r5));
            ingredientRepository.save(new Ingredient("Soijakastike", "3", "rkl", r5));
            ingredientRepository.save(new Ingredient("Nuudelit", "200", "g", r5));

            // Lisätään arvostelut vain jos niitä ei vielä ole
            if (reviewRepository.count() == 0) {
                AppUser user = userRepository.findByUsername("user").get();
                AppUser admin = userRepository.findByUsername("admin").get();

            // Resepti 1: Makaronilaatikko
            Review r1review1 = new Review();
            r1review1.setRating(4);
            r1review1.setComment("Perinteinen ja hyvä!");
            r1review1.setUser(user);
            r1review1.setRecipe(r1);
            reviewRepository.save(r1review1);

            Review r1review2 = new Review();
            r1review2.setRating(5);
            r1review2.setComment("Loistava perusruoka");
            r1review2.setUser(admin);
            r1review2.setRecipe(r1);
            reviewRepository.save(r1review2);

            // Resepti 2: Pannukakku
            Review r2review1 = new Review();
            r2review1.setRating(5);
            r2review1.setComment("Helppo ja maukas jälkiruoka");
            r2review1.setUser(user);
            r2review1.setRecipe(r2);
            reviewRepository.save(r2review1);

            Review r2review2 = new Review();
            r2review2.setRating(4);
            r2review2.setComment("Toimiva perusresepti");
            r2review2.setUser(admin);
            r2review2.setRecipe(r2);
            reviewRepository.save(r2review2);

            // Resepti 3: Lohikeitto
            Review r3review1 = new Review();
            r3review1.setRating(5);
            r3review1.setComment("Todella maukasta!");
            r3review1.setUser(user);
            r3review1.setRecipe(r3);
            reviewRepository.save(r3review1);

            Review r3review2 = new Review();
            r3review2.setRating(5);
            r3review2.setComment("Täydellinen talviruoka");
            r3review2.setUser(admin);
            r3review2.setRecipe(r3);
            reviewRepository.save(r3review2);

            // Resepti 4: Kanakastike ja riisi
            Review r4review1 = new Review();
            r4review1.setRating(3);
            r4review1.setComment("Ihan ok perusruoka");
            r4review1.setUser(user);
            r4review1.setRecipe(r4);
            reviewRepository.save(r4review1);

            Review r4review2 = new Review();
            r4review2.setRating(4);
            r4review2.setComment("Nopea tehdä ja maistuu");
            r4review2.setUser(admin);
            r4review2.setRecipe(r4);
            reviewRepository.save(r4review2);

            // Resepti 5: Kasviswokki
            Review r5review1 = new Review();
            r5review1.setRating(4);
            r5review1.setComment("Raikas ja terveellinen!");
            r5review1.setUser(user);
            r5review1.setRecipe(r5);
            reviewRepository.save(r5review1);

            Review r5review2 = new Review();
            r5review2.setRating(3);
            r5review2.setComment("Hyvä kasvisvaihtoehto");
            r5review2.setUser(admin);
            r5review2.setRecipe(r5);
            reviewRepository.save(r5review2);
            } // suljetaan if(reviewRepository.count() == 0)
        } // suljetaan if(recipeRepository.count() == 0)
    };
}
}
