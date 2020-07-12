package com.daimler.foodaggregator.controller;

import com.daimler.foodaggregator.model.FoodItem;
import com.daimler.foodaggregator.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/v1.0")
public class AggregatorController {

    @Autowired
    private PurchaseService service;

    @GetMapping("/buy-item")
    public FoodItem getItemByName(@RequestParam String name) {
        return service.purchaseItemByName(name);
    }

    @GetMapping("/buy-item-qty")
    public FoodItem getItemByNameAndQuantity(@RequestParam String name, @RequestParam Integer quantity) {
        return service.purchaseItemByNameAndQuantity(name, quantity);
    }

    @GetMapping("/buy-item-qty-price")
    public FoodItem getItemByNameQtyAndPrice(@RequestParam String name, @RequestParam Integer quantity, @RequestParam Integer price) {
        return service.getItemByNameQtyAndPrice(name, quantity, price);
    }

    @GetMapping("/show-summary")
    public List<FoodItem> getInventoryStats() {
        return service.getInventory();
    }

    @GetMapping("/fast-buy-item")
    public FoodItem fastBuyItem(@RequestParam String name) {
        return service.fastBuy(name);
    }
}
