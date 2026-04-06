package sia.taco_cloud;

import lombok.Data;

@Data
public class Ingredient {
    private final String id;
    private final String name;
    private final Kind kind;

    public enum Kind {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
