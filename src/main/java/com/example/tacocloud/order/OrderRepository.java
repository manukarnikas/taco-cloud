package com.example.tacocloud.order;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<TacoOrder, Long> {
}

