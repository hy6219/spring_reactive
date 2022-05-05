package com.reactive.test.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemUnitTest {

    @Test
    @DisplayName("단위 테스트")
    void itemBasicsShouldWork(){
        Item sampleItem = new Item("item1","TV tray", "Alf TV tray", 19.99);

        //Assertj를 사용한 값 일치 테스트
        assertThat(sampleItem.getId()).isEqualTo("item1");
        assertThat(sampleItem.getName()).isEqualTo("TV tray");
        assertThat(sampleItem.getDescription()).isEqualTo("Alf TV tray");
        assertThat(sampleItem.getPrice()).isEqualTo(19.99);

        Item item2 = new Item("item2", "TV tray", "Alf TV tray", 19.99);
        assertFalse(sampleItem.equals(item2));
    }
}