package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.repository.OrderRepository;
import edu.sytoss.repository.ProductCardRepository;
import edu.sytoss.repository.ProductRepository;
import edu.sytoss.service.OrderAPI;
import edu.sytoss.service.ProductApi;
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
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductApi productApi;

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
        Order order = orderRepository.findOrderWithProductsById(id);
        return order.getProducts();
    }

    @Override
    public List<ProductCard> findAllProductCartsInOrderById(Long id) {
        List<Product> products = orderRepository.findOrderWithProductCartsById(id).getProducts();
        List<ProductCard> productCards = new ArrayList<>();
        for (Product product : products) {
            productCards.add(product.getProductCard());
        }
        return productCards;
    }

    @Override
    public ProductCard findProductCardById(Long id) {
        return productCardRepository.findById(id);
    }

    @Override
    public void updateOrder(Long orderId, Long productCardId, int quantity, String actionType) {
        if (actionType.equals("add")) {
            ProductCard productCard = productCardRepository.findProductCardWithProductsByIdWhereStatus(productCardId, "AVAILABLE");
            List<Product> products = productCard.getProducts();
            for (int i = 0; i < quantity; i++) {
                Product product = products.get(i);
                productApi.updateProductStatus(product, orderId, "BLOCKED");
            }
        } else if (actionType.equals("delete")) {
            Order order = orderRepository.findById(orderId);
            List<Product> products = productRepository.findProductByOrder(order);
            for (int i = 0; i < quantity; i++) {
                Product product = products.get(i);
                productApi.updateProductStatus(product, null, "AVAILABLE");
            }
        }
    }

    @Override
    public void updateOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.setState("finished_accepted");
        List<Product> products = productRepository.findProductByOrder(order);
        for (Product product: products) {
            productApi.updateProductStatus(product, orderId, "SOLD");
        }
        orderRepository.save(order);
    }
}

