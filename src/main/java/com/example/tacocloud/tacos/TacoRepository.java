package com.example.tacocloud.tacos;

public interface TacoRepository {

    public long saveTaco(Long orderId, int orderKey, Taco taco);
}
