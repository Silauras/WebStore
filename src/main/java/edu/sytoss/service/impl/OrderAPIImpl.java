package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.OrderRepository;
import edu.sytoss.repository.ProductCardRepository;
import edu.sytoss.service.OrderAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderAPIImpl implements OrderAPI {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductCardRepository productCardRepository;

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public List<Product> findAllProductInOrderById(Long id) {
        Order order =  orderRepository.findOrderWithProductsById(id);
        return order.getProducts();
    }

    @Override
    public List<ProductCard> findAllProductCartsInOrderById(Long id) {
        List<Product> products = orderRepository.findOrderWithProductCartsById(id).getProducts();
        List<ProductCard> productCards = new ArrayList<>();
        for (Product product:products ) {
            productCards.add(product.getProductCard());
        }
        return productCards;
    }

    @Override
    public ProductCard findProductCardById(Long id) {
        return productCardRepository.findById(id);
    }
}
