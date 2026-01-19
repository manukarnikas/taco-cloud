package com.example.tacocloud.ingredient;

import java.util.List;

public interface IngredientRefRepository {

    public void saveIngredientRefs(long tacoId, List<Ingredient> ingredients);
}
