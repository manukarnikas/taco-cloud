package com.example.tacocloud.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<TacoOrder, Long> {
}

