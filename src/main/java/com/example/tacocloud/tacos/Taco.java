package com.example.tacocloud.tacos;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.example.tacocloud.ingredient.Ingredient;
import lombok.Data;

@Data
@Entity
@Table(name="taco")
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @NotNull
    @Size(min=5, message="Name must be atleast 5 characters long")
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min=1, message="You must choose atleast 1 ingredient")
    @ManyToMany
    @JoinTable(
            name = "Ingredient_Ref",
            joinColumns = @JoinColumn(name = "taco_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;


}
