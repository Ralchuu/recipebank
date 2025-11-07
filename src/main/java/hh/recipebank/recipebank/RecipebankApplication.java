package hh.recipebank.recipebank;

import hh.recipebank.recipebank.domain.AppUser;
import hh.recipebank.recipebank.domain.AppUserRepository;
import hh.recipebank.recipebank.domain.Ingredient;
import hh.recipebank.recipebank.domain.IngredientRepository;
import hh.recipebank.recipebank.domain.Recipe;
import hh.recipebank.recipebank.domain.RecipeRepository;

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

        }
    };
}

}
