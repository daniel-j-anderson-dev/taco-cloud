package tacos;

import java.util.List;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import tacos.data.IngredientRepository;
import tacos.model.Ingredient;

@Slf4j
@SpringBootApplication
public class TacoCloudApplication {
	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	@Bean
	public ApplicationRunner dataLoader(IngredientRepository repo) {
		return _ -> {
			log.info("populating default ingredients");
			var ingredients = repo.saveAll(List.of(
					new Ingredient("FLTO", "Flour Tortilla", Ingredient.Kind.WRAP, true),
					new Ingredient("COTO", "Corn Tortilla", Ingredient.Kind.WRAP, true),
					new Ingredient("GRBF", "Ground Beef", Ingredient.Kind.PROTEIN, true),
					new Ingredient("CARN", "Carnitas", Ingredient.Kind.PROTEIN, true),
					new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Kind.VEGGIES, true),
					new Ingredient("LETC", "Lettuce", Ingredient.Kind.VEGGIES, true),
					new Ingredient("CHED", "Cheddar", Ingredient.Kind.CHEESE, true),
					new Ingredient("JACK", "Monterrey Jack", Ingredient.Kind.CHEESE, true),
					new Ingredient("SLSA", "Salsa", Ingredient.Kind.SAUCE, true),
					new Ingredient("SRCR", "Sour Cream", Ingredient.Kind.SAUCE, true))
				);
			log.info("default ingredients populated: {}", ingredients);
		};
	}
}
