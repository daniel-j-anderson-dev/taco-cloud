package sia.taco_cloud.web;

import sia.taco_cloud.Ingredient;
import sia.taco_cloud.Taco;
import sia.taco_cloud.TacoOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private static final List<Ingredient> INGREDIENTS = List.of(
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

    private static final Map<String, List<Ingredient>> INGREDIENTS_BY_KIND = Arrays
            .stream(Ingredient.Kind.values())
            .collect(Collectors.toMap(
                    // key producer
                    kind -> kind.toString().toLowerCase(),
                    // value producer
                    kind -> INGREDIENTS
                            .stream()
                            .filter(ingredient -> ingredient.getKind().equals(kind))
                            .collect(Collectors.toList())));

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        model.addAllAttributes(INGREDIENTS_BY_KIND);
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }
}
