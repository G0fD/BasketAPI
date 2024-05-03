package com.ocado.basket;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketSplitterTest {
    private final BasketSplitter basketSplitter = new BasketSplitter("src/test/resources/configCorrect.json");

    @Test
    void splitInputListCannotBeNull() {
        //given
        List<String> items = null;
        //when
        //then
        assertThrows(RuntimeException.class, () -> basketSplitter.split(items));
    }

    @Test
    void splitInputListMustHaveValidItems() {
        //given
        List<String> items = Arrays.asList("a", "b", "c");
        //when
        //then
        assertThrows(RuntimeException.class, () -> basketSplitter.split(items));
    }

    @Test
    void splitMustReturnEmptyMapIfInputListIsEmpty() {
        //given
        List<String> items = new LinkedList<>();
        //when
        //then
        assertTrue(basketSplitter.split(items).isEmpty());
    }

    @Test
    void splitResultMapCannotBeEmptyIfInputListIsValid() {
        //given
        List<String> items = Arrays.asList("Puree - Strawberry", "Onions - White", "Sauce - Salsa");
        //when
        //then
        assertFalse(basketSplitter.split(items).isEmpty());
    }

    @Test
    void splitResultMapCannotBeTheSameSizeAsInputListIfBasketItemsHaveCommonDeliveryMethod() {
        //given
        List<String> items = Arrays.asList("Puree - Strawberry", "Onions - White", "Sauce - Salsa");
        //when
        int basketItemsCount = items.size();
        //then
        assertTrue(basketSplitter.split(items).size() < basketItemsCount);
    }

    @Test
    void splitResultMapMustHaveSameSizeAsInputListIfBasketItemsDontHaveCommonDeliveryMethod() {
        //given
        List<String> items = Arrays.asList("Garlic - Peeled", "Sauce - Salsa", "Spinach - Frozen");
        //when
        int basketItemsCount = items.size();
        //then
        assertEquals(basketSplitter.split(items).size(), basketItemsCount);
    }
}