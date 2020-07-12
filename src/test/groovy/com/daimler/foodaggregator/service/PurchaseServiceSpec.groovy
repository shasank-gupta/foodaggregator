package com.daimler.foodaggregator.service


import com.daimler.foodaggregator.datastore.FoodInventory
import com.daimler.foodaggregator.model.FoodItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PurchaseServiceSpec extends Specification {

    @Autowired
    PurchaseService service;

    @Autowired
    FoodInventory inventory;

    void setItem() {
        inventory.addItem(new FoodItem("1", "testFood", 15, "410"))
    }

    def "test purchaseItemByName for exception"() {
        when:
        service.purchaseItemByName("funny")

        then:
        thrown(Exception.class)
    }

    def "test purchaseItemByName for success"() {
        when:
        FoodItem res = service.purchaseItemByName("testFood")

        then:
        noExceptionThrown()
        res
        res.getQuantity() == 10
    }

    def "test purchaseItemByNameAndQuantity for exception"() {
        when:
        service.purchaseItemByNameAndQuantity("funny", 15)

        then:
        thrown(Exception.class)
    }

    def "test purchaseItemByNameAndQuantity for success"() {
        when:
        FoodItem res = service.purchaseItemByNameAndQuantity("testFood", 10)

        then:
        noExceptionThrown()
        res
        res.getQuantity() == 10
    }

    def "test purchaseByItemQtyAndPrice for success"() {
        given:
        setItem()

        when:
        FoodItem res = service.getItemByNameQtyAndPrice("testFood", 10, 440)

        then:
        noExceptionThrown()
        res
        res.getQuantity() == 10
    }

    def "test purchaseByItemQtyAndPrice for Exception"() {
        when:
        FoodItem res = service.getItemByNameQtyAndPrice("testFood", 11, 440)

        then:
        thrown(Exception.class)
    }

    def "Test inventoryStats"() {
        when:
        List<FoodItem> inventoryList = service.getInventory()

        then:
        inventoryList
        inventoryList.size() == 3
    }

}
