package com.example.ch02_mongotemplate.repository;

import com.example.ch02_mongotemplate.domain.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartRepository extends ReactiveCrudRepository<Cart,String> {
}
