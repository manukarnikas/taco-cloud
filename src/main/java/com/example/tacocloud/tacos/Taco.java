package com.example.tacocloud.tacos;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.example.tacocloud.ingredient.Ingredient;
import lombok.Data;

@Data
public class Taco {

    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min=5, message="Name must be atleast 5 characters long")
    private String name;

    @NotNull
    @Size(min=1, message="You must choose atleast 1 ingredient")
    private List<Ingredient> ingredients;


}
