package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PriceCalculator {
    @Autowired
    OrderRepository orderRepository;
    public BigDecimal calculatePriceForOrder(Long orderId) {
        BigDecimal resultPrice = new BigDecimal(0);
        Order order = orderRepository.findByIdWithAllPrices(orderId);
        Map<ProductCard, Integer> productCardCount = new HashMap<>();
        for (Product product : order.getProducts()) {
            ProductCard productCard = product.getProductCard();
            if (!productCardCount.containsKey(productCard)) {
                productCardCount.put(productCard, 1);
            }
            productCardCount.put(productCard, productCardCount.get(productCard) + 1);
        }
        for (ProductCard productCard : productCardCount.keySet()) {
            resultPrice = resultPrice.add(
                    productCard.getPrice().
                    multiply(BigDecimal.valueOf(productCardCount.get(productCard))));
        }
        return resultPrice;
    }


}


