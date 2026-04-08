package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Ingredient rowToIngredient(ResultSet row, int _rowIndex) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Kind.valueOf(row.getString("kind")));
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return this.jdbcTemplate.query(
                "select id, name, kind from Ingredient",
                JdbcIngredientRepository::rowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        var results = this.jdbcTemplate.query(
                "select id, name, kind from Ingredient where id = ?",
                JdbcIngredientRepository::rowToIngredient,
                id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        this.jdbcTemplate.update(
                "insert into Ingredient (id, name, kind) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getKind());
        return ingredient;
    }

}
