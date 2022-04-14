package com.example.spring_reactive.flux.service;

import com.example.spring_reactive.flux.domain.entity.Dish;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class KitchenService {

    public Flux<Dish> getDishes() {
        return Flux.just(
                new Dish("Sesame chicken"),
                new Dish("Lo mein noodles, plain"),
                new Dish("Sweet & sour beef")
        );
    }
}
