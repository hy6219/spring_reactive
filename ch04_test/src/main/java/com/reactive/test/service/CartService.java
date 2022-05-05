package com.reactive.test.service;

import com.reactive.test.domain.Cart;
import com.reactive.test.domain.CartItem;
import com.reactive.test.domain.Item;
import com.reactive.test.repository.CartRepository;
import com.reactive.test.repository.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.byExample;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class CartService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    //평문형 연산 지원
/*    private final ReactiveFluentMongoOperations fluentMongoOperations;*/

    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Mono<Cart> addToCart(String cartId, String id) {
        return this.cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem()
                                .getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(() ->
                                this.itemRepository.findById(id)
                                        .map(CartItem::new)
                                        .doOnNext(cartItem -> cart.getCartItems().add(cartItem))
                                        .map(cartItem -> cart)))
                .flatMap(this.cartRepository::save);
    }

   /* public Flux<Item> searchByExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);

        //ctrl + alt + v
        ExampleMatcher matcher = (useAnd ?
                ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price");
        //null 이나 empty가 아닐 수 있음
        //useAnd == true 면 and로 이어지고, ==false면 or로 이어질 수 있는데
        //name, description 조건으로 일치하는 데이터 조회

        Example<Item> probe = Example.of(item, matcher);

        return itemRepository.findAll(probe);
    }

    //평문형 연산
    Flux<Item> searchByFluentExample(String name, String description) {
        return fluentMongoOperations.query(Item.class)
                .matching(Query.query(where("TV tray").is(name).and("Smurf").is(description)))
                .all();
        *//*
        <=>
        (RDBMS) SELECT * FROM ITEM
        WHERE NAME = 'TV tray' and DESCRIPTION = 'Smurf';
        <=>
        (MONGO DB)
        {$and: [ {name:'TV tray'}, {description:'Smurf'}]}
         *//*
    }

    Flux<Item> searchByFluentExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd ?
                ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price");

        return fluentMongoOperations.query(Item.class)
                .matching(Query.query(byExample(Example.of(item, matcher))))
                .all();
    }*/
}
