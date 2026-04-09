package tacos.controllers;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import tacos.data.IngredientRepository;
import tacos.model.Ingredient;
import tacos.model.Taco;
import tacos.model.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        var ingredients = this.ingredientRepository.findAll();
        var ingredients_by_kind = Ingredient.Kind
                .valuesStream()
                .collect(Collectors.toMap(
                        // key producer
                        kind -> kind.toString().toLowerCase(),
                        // value producer
                        kind -> StreamSupport
                                .stream(ingredients.spliterator(), false)
                                .filter(ingredient -> ingredient.getKind().equals(kind))
                                .collect(Collectors.toList())));
        model.addAllAttributes(ingredients_by_kind);
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco,
            Errors validationErrors,
            @ModelAttribute TacoOrder tacoOrder) {
        if (validationErrors.hasErrors()) {
            return "design";
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }
}
