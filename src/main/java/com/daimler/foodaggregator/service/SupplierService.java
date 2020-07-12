package com.daimler.foodaggregator.service;

import com.daimler.foodaggregator.model.FoodItem;
import reactor.core.publisher.Flux;

public interface SupplierService {
    Flux<FoodItem> getFoodFromVendor(String vendorId);
}
