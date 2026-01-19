package com.example.tacocloud.ingredient;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcIngredientRefRepository implements IngredientRefRepository{

    private final JdbcOperations jdbcOperations;

    public JdbcIngredientRefRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void saveIngredientRefs(long tacoId, List<Ingredient> ingredients) {
            int key = 0;
            for (Ingredient ingredient : ingredients) {
                this.jdbcOperations.update(
                        "insert into Ingredient_Ref (ingredient, taco, taco_key) "
                                + "values (?, ?, ?)",
                        ingredient.getId(), tacoId, key++);
            }
    }
}
