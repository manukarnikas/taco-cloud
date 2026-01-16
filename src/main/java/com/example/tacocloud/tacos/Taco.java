package com.example.tacocloud.tacos;

import java.util.List;

import com.example.tacocloud.ingredient.Ingredient;
import lombok.Data;

@Data
public class Taco {

    private String name;

    private List<Ingredient> ingredients;

}
