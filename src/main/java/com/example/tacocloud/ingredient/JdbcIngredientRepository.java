package com.example.tacocloud.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll(){
        return this.jdbcTemplate.query(
                "select id, name, type from Ingredient",
                this::mapRowToIngredient
        );
    }

    @Override
    public Optional<Ingredient> findById(String id){
        List<Ingredient> results = this.jdbcTemplate.query(
                "select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient,
                id);
        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient){
        this.jdbcTemplate.update(
                "insert into Ingredient (id, name, type) values(?,?,?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
        );
        return ingredient;
    }


    // helper

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type"))
        );
    }

}
