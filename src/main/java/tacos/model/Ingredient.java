package tacos.model;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient implements Persistable<String> {
    @Id
    private String id;
    private String name;
    private Kind kind;

    @Transient
    private boolean isNew = false;

    public enum Kind {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE;

        public static Stream<Kind> valuesStream() {
            return Arrays.stream(Kind.values());
        }
    }
}
