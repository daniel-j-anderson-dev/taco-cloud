package tacos;

import java.util.Arrays;
import java.util.stream.Stream;

import lombok.Data;

@Data
public class Ingredient {
    private final String id;
    private final String name;
    private final Kind kind;

    public enum Kind {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE;

        public static Stream<Kind> valuesStream() {
            return Arrays.stream(Kind.values());
        }
    }
}
