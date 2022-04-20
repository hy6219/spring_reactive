package com.greglturnquist.hackingspringboot.reactive.common;


import com.greglturnquist.hackingspringboot.reactive.ReactiveCrudRepository.BlockingItemRepository;
import com.greglturnquist.hackingspringboot.reactive.domain.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 블로킹 API를 사용해서 데이터를 로딩하는 클래스
 */
@Component
public class RepositoryDatabaseLoader {
    @Bean
    CommandLineRunner initialize(BlockingItemRepository repository) {
        return args -> {
            repository.save(new Item("Alf alarm clock", 19.99));
            repository.save(new Item("Smurf TV tray", 24.99));
        };
    }
}
