package com.example.tacocloud.tacos;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import com.example.tacocloud.ingredient.IngredientRefRepository;
import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcOperations jdbcOperations;
    private final IngredientRefRepository ingredientRefRepository;

    public JdbcTacoRepository(JdbcOperations jdbcOperations, IngredientRefRepository ingredientRefRepository) {
        this.jdbcOperations = jdbcOperations;
        this.ingredientRefRepository = ingredientRefRepository;
    }

    @Override
    public long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Taco "
                                + "(name, created_at, taco_order, taco_order_key) "
                                + "values (?, ?, ?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
                );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                taco.getCreatedAt(),
                                orderId,
                                orderKey));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);
        this.ingredientRefRepository.saveIngredientRefs(tacoId, taco.getIngredients());
        return tacoId;
    }
}
