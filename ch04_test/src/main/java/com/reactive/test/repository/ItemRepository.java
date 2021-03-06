package com.reactive.test.repository;

import com.reactive.test.domain.Item;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 ReactiveQueryByExampleExecutor : Example 쿼리 작성을 위함
 */
public interface ItemRepository extends ReactiveCrudRepository<Item, String>
        /*ReactiveQueryByExampleExecutor<Item>*/ {
    Flux<Item> findByNameContaining(String partialName);

    @Query("{'name':?0,'age':?1}")
    Flux<Item> findItemsForCustomerMonthlyReport(String name, int age);

    @Query("{'age':-1}")
    Flux<Item> findSortedStuffForWeeklyReport();
}
