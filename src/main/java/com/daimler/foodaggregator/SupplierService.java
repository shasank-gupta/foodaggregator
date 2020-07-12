package com.daimler.foodaggregator;

import com.daimler.foodaggregator.model.FoodItem;
import reactor.core.publisher.Flux;

public interface SupplierService {
    Flux<FoodItem> getFoodFromVendor(String vendorId);
}
