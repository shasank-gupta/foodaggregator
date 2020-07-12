package com.daimler.foodaggregator.service;

import com.daimler.foodaggregator.model.FoodItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FoodSupplier implements SupplierService {

    @Autowired
    private WebClient client;

    @Override
    public Flux<FoodItem> getFoodFromVendor(String vendorId) {
        return client
                .get()
                .uri(vendorId)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        res -> Mono.error(new IllegalStateException("Server Unavailable")))
                .bodyToFlux(FoodItem.class);
    }
}
