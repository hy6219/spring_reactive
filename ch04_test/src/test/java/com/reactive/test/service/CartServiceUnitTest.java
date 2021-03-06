package com.reactive.test.service;

import com.reactive.test.domain.Cart;
import com.reactive.test.domain.CartItem;
import com.reactive.test.domain.Item;
import com.reactive.test.repository.CartRepository;
import com.reactive.test.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
class CartServiceUnitTest {
    /*
    CUT = Class Under Test
    = 테스트의 대상이 되는 클래스
    ->CartService
     */
    CartService cartService;

    //가짜 협력체(mock)
    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CartRepository cartRepository;


    /*
    @MockBean
    <=>
    @BeforeEach
    void setUp(){
        itemRepository = mock(ItemRepository.class);
        cartRepository = mock(CartRepository.class);
        fluentMongoOperations = mock(ReactiveFluentMongoOperations.class);
    }
     */

    @BeforeEach
    void setUp() {
        //테스트 데이터 정의
        Item sampleItem = new Item("item1", "TV tray", "Alf TV tray", 19.99);
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

        //협력자와의 상호작용 정의
        when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

        cartService = new CartService(itemRepository, cartRepository);
    }

    @Test
    void addItemToEmptyCartShouldProduceOneCartItem(){
        cartService.addToCart("My Cart","item1")
                .as(StepVerifier::create)
                .expectNextMatches(cart->{
                    assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                            .containsExactlyInAnyOrder(1);

                    assertThat(cart.getCartItems()).extracting(CartItem::getItem)
                            .containsExactly(new Item("item1","TV tray","Alf TV tray",19.99));

                    return true;
                })
                .verifyComplete();
    }
}