package com.daimler.foodaggregator.controller;

import com.daimler.foodaggregator.model.FoodItem;
import com.daimler.foodaggregator.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/v1.0")
public class AggregatorController {

    @Autowired
    private PurchaseService service;

    @GetMapping("/buy-item")
    public FoodItem getItemByName(@RequestParam String name) {
        try {
            return service.purchaseItemByName(name);
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buy-item-qty")
    public FoodItem getItemByNameAndQuantity(@RequestParam String name, @RequestParam Integer quantity) {
        try {
            return service.purchaseItemByNameAndQuantity(name, quantity);
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buy-item-qty-price")
    public FoodItem getItemByNameQtyAndPrice(@RequestParam String name, @RequestParam Integer quantity, @RequestParam Integer price) {
        try {
            return service.getItemByNameQtyAndPrice(name, quantity, price);
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/show-summary")
    public List<FoodItem> getInventoryStats() {
        return service.getInventory();
    }

    @GetMapping("/fast-buy-item")
    public FoodItem fastBuyItem(@RequestParam String name) {
        try {
            return service.fastBuy(name);
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
