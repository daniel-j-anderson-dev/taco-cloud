package tacos.model;

import java.util.Arrays;
import java.util.stream.Stream;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient {
    @Id
    private String id;
    private String name;
    private Kind kind;

    public enum Kind {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE;

        public static Stream<Kind> valuesStream() {
            return Arrays.stream(Kind.values());
        }
    }
}
