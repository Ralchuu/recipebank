package hh.recipebank;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Minimal test bootstrap so Spring Test finds a @SpringBootConfiguration when tests run
 * from package hh.recipebank. Placed in src/test/java so it is only used for tests.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
public class TestApplication {
	// empty - only used to bootstrap the test ApplicationContext
}
