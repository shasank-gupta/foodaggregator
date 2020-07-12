package com.daimler.foodaggregator.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodItem {
    @JsonProperty("id")
    @JsonAlias({"productId", "itemId"})
    private String id;

    @JsonProperty("name")
    @JsonAlias({"itemName", "productName"})
    private String name;

    private int quantity;

    private String price;
}
