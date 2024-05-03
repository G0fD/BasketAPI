package com.ocado.basket;

import java.util.*;


public class BasketSplitter {

    private final Map<String, List<String>> productList;

    public BasketSplitter(String absolutePathToConfigFile) {
        this.productList = new ReadConfig().readConfigFromFile(absolutePathToConfigFile);
    }

    public Map<String, List<String>> split(List<String> items) {
        final Map<String, List<String>> basketMap = extractAndAddDelivery(items);
        final Map<String, List<String>> deliveryAndProductsMap = flipMap(basketMap);

        final Map<String, List<String>> result = new HashMap<>();
        while (!deliveryAndProductsMap.isEmpty()) {
            AbstractMap.SimpleEntry<String, List<String>> entry = findBiggestList(deliveryAndProductsMap);
            result.put(entry.getKey(), entry.getValue());
            removeUsedDeliveryTypes(entry, deliveryAndProductsMap);
        }
        return result;
    }

    private void removeUsedDeliveryTypes(AbstractMap.SimpleEntry<String, List<String>> entry, Map<String, List<String>> deliveryAndProductsMap) {
        deliveryAndProductsMap.remove(entry.getKey());
        deliveryAndProductsMap.forEach((key, value) -> value.removeAll(entry.getValue()));

        List<String> emptyKeys = deliveryAndProductsMap.keySet().stream()
                .filter(key -> deliveryAndProductsMap.get(key).isEmpty())
                .toList();
        emptyKeys.forEach(deliveryAndProductsMap::remove);
    }

    private AbstractMap.SimpleEntry<String, List<String>> findBiggestList(Map<String, List<String>> deliveryAndProductsMap) {
        AbstractMap.SimpleEntry<String, List<String>> entry = null;
        for (String key : deliveryAndProductsMap.keySet()) {
            final int currentSize = entry == null ? 0 : entry.getValue().size();
            final List<String> toCheckList = deliveryAndProductsMap.get(key);
            if (currentSize < toCheckList.size()) {
                entry = new AbstractMap.SimpleEntry<>(key, toCheckList);
            }
        }
        if (entry == null) {
            throw new RuntimeException("Entry is null");
        }
        return entry;
    }

    private Map<String, List<String>> flipMap(Map<String, List<String>> map) {
        final Map<String, List<String>> result = new HashMap<>();
        map.values().forEach(value -> value.forEach(item -> result.putIfAbsent(item, new LinkedList<>())));
        map.forEach((key, value) -> value.forEach(item -> result.get(item).add(key)));
        return result;
    }

    private Map<String, List<String>> extractAndAddDelivery(List<String> items) {
        final Map<String, List<String>> result = new HashMap<>();
        items.forEach(item -> result.put(item, productList.get(item)));
        return result;
    }
}
