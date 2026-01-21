package com.example.tacocloud.tacos;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TacoRepository extends MongoRepository<Taco, Long> {
}


