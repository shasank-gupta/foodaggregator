package com.daimler.foodaggregator


import com.daimler.foodaggregator.model.FoodItem
import com.daimler.foodaggregator.service.FoodSupplier
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import spock.lang.Specification

@Slf4j
@SpringBootTest
class RestCallSpec extends Specification {

    @Autowired
    FoodSupplier supplier;
    
    def "test the rest call"() {
        when:
        Flux<FoodItem> itemFlux = supplier.getFoodFromVendor("testfood")

        then:
        noExceptionThrown()
        List<FoodItem> result = itemFlux.collectList().block()
        result.size() == 3;

    }

}
