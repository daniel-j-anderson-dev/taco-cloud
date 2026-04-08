package tacos.data;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import tacos.Ingredient;

public class MockFixedIngredientRepository implements IngredientRepository {
        public static final List<Ingredient> INGREDIENTS = List.of(
                        new Ingredient("FLTO", "Flour Tortilla", Ingredient.Kind.WRAP),
                        new Ingredient("COTO", "Corn Tortilla", Ingredient.Kind.WRAP),
                        new Ingredient("GRBF", "Ground Beef", Ingredient.Kind.PROTEIN),
                        new Ingredient("CARN", "Carnitas", Ingredient.Kind.PROTEIN),
                        new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Kind.VEGGIES),
                        new Ingredient("LETC", "Lettuce", Ingredient.Kind.VEGGIES),
                        new Ingredient("CHED", "Cheddar", Ingredient.Kind.CHEESE),
                        new Ingredient("JACK", "Monterrey Jack", Ingredient.Kind.CHEESE),
                        new Ingredient("SLSA", "Salsa", Ingredient.Kind.SAUCE),
                        new Ingredient("SRCR", "Sour Cream", Ingredient.Kind.SAUCE));

        public static final Map<String, Ingredient> INGREDIENT_BY_ID = INGREDIENTS
                        .stream()
                        .collect(Collectors.toMap(
                                        Ingredient::getId,
                                        Function.identity()));

        public static final Map<String, List<Ingredient>> INGREDIENTS_BY_KIND = Ingredient.Kind
                        .valuesStream()
                        .collect(Collectors.toMap(
                                        // key producer
                                        kind -> kind.toString().toLowerCase(),
                                        // value producer
                                        kind -> INGREDIENTS
                                                        .stream()
                                                        .filter(ingredient -> ingredient.getKind().equals(kind))
                                                        .collect(Collectors.toList())));

        @Override
        public List<Ingredient> findAll() {
                return INGREDIENTS;
        }

        @Override
        public Optional<Ingredient> findById(String id) {
                try {
                        return Optional.of(INGREDIENT_BY_ID.get(id));
                } catch (Exception _) {
                        return Optional.empty();
                }
        }

        @Override
        public Ingredient save(Ingredient ingredient) {
                return ingredient;
        }
}
