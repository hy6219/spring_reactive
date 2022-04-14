package com.example.spring_reactive.flux.service;

public class PoliteRestaurant {
    public static void main(String[] args) {
        PoliteServer server = new PoliteServer(new KitchenService());

        server.doingMyJob().subscribe(
                dish -> System.out.println("Consuming "+dish.getFood()),
                throwable -> System.out.println(throwable.getMessage()));
    }
}
