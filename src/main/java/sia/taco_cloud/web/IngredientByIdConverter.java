package sia.taco_cloud.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sia.taco_cloud.Ingredient;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        this.ingredientMap = MockIngredientDataBase.INGREDIENT_BY_ID;
    }

    @Override
    public Ingredient convert(String id) {
        return this.ingredientMap.get(id);
    }
}
