package com.example.tacocloud.ingredient;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="ingredient")
public class Ingredient {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    public enum Type {
        WRAP,
        PROTEIN,
        VEGGIES,
        CHEESE,
        SAUCE
    }
}
