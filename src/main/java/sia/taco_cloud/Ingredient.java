package sia.taco_cloud;

import lombok.Data;

@Data
public class Ingredient {
    private String id;
    private String name;
    private Kind kind;

    public enum Kind {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
