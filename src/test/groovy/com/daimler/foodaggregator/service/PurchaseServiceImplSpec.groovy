package com.daimler.foodaggregator.service


import com.daimler.foodaggregator.datastore.FoodInventory
import com.daimler.foodaggregator.exception.ItemNotFoundException
import com.daimler.foodaggregator.model.FoodItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PurchaseServiceImplSpec extends Specification {

    @Autowired
    PurchaseServiceImpl service

    @Autowired
    FoodInventory inventory

    void setItem() {
        inventory.addItem(new FoodItem("1", "testFood", 15, "410"))
    }

    def "test purchaseItemByName for exception"() {
        when:
        service.purchaseItemByName("funny")

        then:
        thrown(ItemNotFoundException.class)
    }

    def "test purchaseItemByName for success"() {
        when:
        FoodItem res = service.purchaseItemByName("Apple")

        then:
        noExceptionThrown()
        res
        res.getQuantity() == 30
    }

    def "test purchaseItemByNameAndQuantity for exception"() {
        when:
        service.purchaseItemByNameAndQuantity("funny", 15)

        then:
        thrown(ItemNotFoundException.class)
    }

    def "test purchaseItemByNameAndQuantity for success"() {
        when:
        FoodItem res = service.purchaseItemByNameAndQuantity("carrot", 10)

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
        service.getItemByNameQtyAndPrice("testFood", 11, 440)

        then:
        thrown(ItemNotFoundException.class)
    }

    def "Test inventoryStats"() {
        when:
        List<FoodItem> inventoryList = service.getInventory()

        then:
        inventoryList
        inventoryList.size() == 9
    }

    def "Test fastBuy for exception"() {
        when:
        service.fastBuy("dud")

        then:
        thrown(ItemNotFoundException.class)
    }

    def "Test fastBuy for success"() {
        when:
        FoodItem item = service.fastBuy("Carrot")

        then:
        noExceptionThrown()
        item
        item.name == "Carrot"
    }

}
