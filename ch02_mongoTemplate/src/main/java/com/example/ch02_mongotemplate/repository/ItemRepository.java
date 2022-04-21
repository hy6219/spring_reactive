package com.example.ch02_mongotemplate.repository;

import com.example.ch02_mongotemplate.domain.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item,String> {
}
