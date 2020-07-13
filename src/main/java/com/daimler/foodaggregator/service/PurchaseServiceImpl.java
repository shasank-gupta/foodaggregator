package com.daimler.foodaggregator.service;

import com.daimler.foodaggregator.config.ApplicationProperties;
import com.daimler.foodaggregator.datastore.FoodInventory;
import com.daimler.foodaggregator.exception.ItemNotFoundException;
import com.daimler.foodaggregator.model.FoodItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PurchaseServiceImpl {
    @Autowired
    private FoodSupplier supplier;

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private FoodInventory inventory;

    public FoodItem purchaseItemByName(String name) {
        Optional<FoodItem> response = Optional.empty();
        for (String vendor : properties.getVendorIds()) {
            Flux<FoodItem> itemFlux = supplier.getFoodFromVendor(vendor);
            response = Optional.ofNullable(itemFlux
                    .filter(foodItem -> name.equalsIgnoreCase(foodItem.getName()))
                    .blockFirst()
            );
            if (response.isPresent()) {
                break;
            }
        }
        return response.orElseThrow(() -> new ItemNotFoundException("NOT_FOUND"));
    }

    public FoodItem purchaseItemByNameAndQuantity(String name, Integer quantity) {
        Optional<FoodItem> response = Optional.empty();
        for (String vendor : properties.getVendorIds()) {
            Flux<FoodItem> itemFlux = supplier.getFoodFromVendor(vendor);
            response = Optional.ofNullable(itemFlux
                    .filter(foodItem -> name.equalsIgnoreCase(foodItem.getName()))
                    .filter(item -> item.getQuantity() >= quantity)
                    .blockFirst()
            );
            if (response.isPresent()) {
                break;
            }
        }
        response.ifPresent(foodItem -> foodItem.setQuantity(quantity));
        return response.orElseThrow(() -> new ItemNotFoundException("NOT_FOUND"));
    }

    public FoodItem getItemByNameQtyAndPrice(String name, Integer qty, Integer price) {
        AtomicReference<Optional<FoodItem>> response = new AtomicReference<>(Optional.empty());
        Optional<FoodItem> result = Optional.ofNullable(inventory.getStore().get(name))
                .filter(it -> qty <= it.getQuantity())
                .filter(it -> Integer.parseInt(it.getPrice()) <= price);
        result.ifPresent(it -> {
            response.set(Optional.of(new FoodItem(it.getId(), it.getName(), qty, it.getPrice())));
            inventory.updateItem(name, qty);
        });
        return response.get().orElseGet(() -> {
            for (String vendor : properties.getVendorIds()) {
                Flux<FoodItem> itemFlux = supplier.getFoodFromVendor(vendor);
                Optional<FoodItem> matching = (Optional.ofNullable(itemFlux
                        .filter(foodItem -> name.equalsIgnoreCase(foodItem.getName()))
                        .filter(item -> qty <= item.getQuantity())
                        .filter(it -> Integer.parseInt(it.getPrice()) <= price)
                        .blockFirst()
                ));
                inventory.addAllItem(Objects.requireNonNull(itemFlux.collectList().block()));
                matching.ifPresent(it -> {
                    response.set(Optional.of(new FoodItem(it.getId(), it.getName(), qty, it.getPrice())));
                });
                if (matching.isPresent()) {
                    break;
                }
            }
            response.get().ifPresent(foodItem -> inventory.updateItem(name, qty));
            return response.get().orElseThrow(() -> new ItemNotFoundException("NOT_FOUND"));
        });
    }

    public List<FoodItem> getInventory() {
        return new ArrayList<>(inventory
                .getStore()
                .values());
    }

    public FoodItem fastBuy(String name) {
        Flux<FoodItem> itemFlux = Flux.empty();
        for (String vendor : properties.getVendorIds()) {
            itemFlux = Flux.concat(itemFlux, supplier.getFoodFromVendor(vendor));
        }
        return Optional.ofNullable(itemFlux
                .filter(foodItem -> name.equalsIgnoreCase(foodItem.getName()))
                .blockFirst()
        ).orElseThrow(() -> new ItemNotFoundException("NOT_FOUND"));
    }
}
