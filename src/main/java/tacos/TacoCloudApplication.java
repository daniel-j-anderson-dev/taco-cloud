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
					new Ingredient("FLTO", "Flour Tortilla", Ingredient.Kind.WRAP),
					new Ingredient("COTO", "Corn Tortilla", Ingredient.Kind.WRAP),
					new Ingredient("GRBF", "Ground Beef", Ingredient.Kind.PROTEIN),
					new Ingredient("CARN", "Carnitas", Ingredient.Kind.PROTEIN),
					new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Kind.VEGGIES),
					new Ingredient("LETC", "Lettuce", Ingredient.Kind.VEGGIES),
					new Ingredient("CHED", "Cheddar", Ingredient.Kind.CHEESE),
					new Ingredient("JACK", "Monterrey Jack", Ingredient.Kind.CHEESE),
					new Ingredient("SLSA", "Salsa", Ingredient.Kind.SAUCE),
					new Ingredient("SRCR", "Sour Cream", Ingredient.Kind.SAUCE))
				);
			log.info("default ingredients populated: {}", ingredients);
		};
	}
}
