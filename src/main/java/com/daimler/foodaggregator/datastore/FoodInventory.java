package com.daimler.foodaggregator.datastore;

import com.daimler.foodaggregator.model.FoodItem;

import java.util.HashMap;

public class FoodInventory {
    private static FoodInventory singletonInstance;
    private HashMap<String, FoodItem> store;

    private FoodInventory() {
        store = new HashMap<>();
    }

    synchronized public static FoodInventory getInstance() {
        if (null == singletonInstance) {
            singletonInstance = new FoodInventory();
        }
        return singletonInstance;
    }

    synchronized public HashMap<String, FoodItem> getStore() {
        return store;
    }

    public void addItem(FoodItem item) {
        HashMap<String, FoodItem> inventory = getStore();
        if (inventory.containsKey(item.getName())) {
            FoodItem storeItem = inventory.get(item.getName());
            storeItem.setPrice(item.getPrice());
            storeItem.setQuantity(storeItem.getQuantity() + item.getQuantity());
        } else {
            inventory.put(item.getName(), item);
        }
    }

    public void updateItem(String name, Integer qty) {
        HashMap<String, FoodItem> inventory = getStore();
        FoodItem storeItem = inventory.get(name);
        storeItem.setQuantity(storeItem.getQuantity() - qty);
        if (storeItem.getQuantity() == 0) {
            inventory.remove(name);
        }
    }

    public void addAllItem(Iterable<FoodItem> itemList) {
        for (FoodItem item : itemList) {
            addItem(item);
        }
    }
}
