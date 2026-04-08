package tacos.data;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.asm.Type;

import tacos.Taco;
import tacos.TacoOrder;
import tacos.Ingredient;
import tacos.Ingredient.Kind;
import tacos.IngredientRef;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private JdbcOperations jdbcOperations;

    @Autowired
    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        // define the query. TODO: talk about the TYPES
        var preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into TacoOrder (deliveryName, deliveryStreet, deliveryCity, deliveryState, deliveryZip, creditCardNumber, creditCardExpiration, creditCardValidationValue, placedAt) values (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, // deliveryName
                Types.VARCHAR, // deliveryStreet
                Types.VARCHAR, // deliveryCity
                Types.VARCHAR, // deliveryState
                Types.VARCHAR, // deliveryZip
                Types.VARCHAR, // creditCardNumber
                Types.VARCHAR, // creditCardExpiration
                Types.VARCHAR, // creditCardValidationValue
                Types.TIMESTAMP); // placedAt
        // we'll need the id column which is a primary key made by the db. setting this
        // true gives us this
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());
        // fill in the values for the query
        var preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCreditCardNumber(),
                        order.getCreditCardExpiration(),
                        order.getCreditCardValidationValue(),
                        order.getPlacedAt()));

        // execute the query and get the key out
        var keyHolder = new GeneratedKeyHolder();
        this.jdbcOperations.update(preparedStatementCreator, keyHolder);
        var orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        // save all the tacos
        var tacos = order.getTacos();
        var i = 0;
        for (var taco : tacos) {
            this.saveTaco(orderId, i, taco);
            i += 1;
        }

        return order;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        var preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt, tacoOrder, tacoOrderKey) values (?, ?, ?, ?)",
                Types.VARCHAR,
                Types.TIMESTAMP,
                Types.BIGINT,
                Types.BIGINT);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        var preparedStatementCreator = preparedStatementCreatorFactory
                .newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                taco.getCreatedAt(),
                                orderId,
                                orderKey));

        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(preparedStatementCreator, keyHolder);
        var tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        this.saveIngredientRefs(tacoId, taco.getIngredients());

        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) {
        var key = 0;
        for (var ingredientRef : ingredientRefs) {
            this.jdbcOperations.update(
                    "insert into IngredientRef (ingredient, taco, tacoKey) values (?, ?, ?)",
                    ingredientRef.getIngredient(),
                    tacoId,
                    key);
            key += 1;
        }
    }
}
