package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.OrderRepository;
import edu.sytoss.repository.ProductCardRepository;
import edu.sytoss.repository.ProductRepository;
import edu.sytoss.service.OrderAPI;
import edu.sytoss.service.ProductApi;
import edu.sytoss.service.UserAccountAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Autowired
    UserAccountAPI userAccountAPI;

    /*---------------------------------Order-------------------------------*/
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
    }

    @Override
    public void updateOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.setState("finished_accepted");
        List<Product> products = productRepository.findProductByOrder(order);
        for (Product product : products) {
            productApi.updateProductStatus(product, orderId, "SOLD");
        }
        orderRepository.save(order);
    }

    @Override
    public boolean createOrder(Order order, List<Product> products) {
        try {
            for (Product product : products) {
                if (!product.getStatus().equals("AVAILABLE")) {
                    System.out.println("Для офрмления заказа недостаточно продуктов");
                    return false;
                }
            }
            order.setState("NEW");
            order.setLastChangeDate(new Date());
            orderRepository.saveAndFlush(order);
            for (Product product : products) {
                productApi.updateProductStatus(product, order.getId(), "BLOCKED");
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }

    }

    /*--------------------------ShoppingCart-----------------------------*/
    @Override
    public Map<ProductCard, Integer> createShoppingCart(UserAccount userAccount) {
        List<Order> shoppingCart = new ArrayList<>();
        List<ProductCard> productCards = new ArrayList<>();
        for (Order order : shoppingCart) {
            productCards.addAll(findAllProductCartsInOrderById(order.getId()));
        }
        /*Create Template for  Shopping Cart*/
        Map<ProductCard, Integer> cardsMap = new HashMap<>();
        int count = 1;
        for (ProductCard productCard : productCards) {
            cardsMap.put(productCard, count);
        }
        /*Count items in Shopping Cart*/
        for (ProductCard productCard : cardsMap.keySet()) {
            for (ProductCard productCardOrder : productCards) {
                if (productCard.equals(productCardOrder)) {
                    cardsMap.put(productCard, count++);
                }
            }
            count = 1;
        }
        return cardsMap;
    }

    @Override
    public Map<ProductCard, Integer> updateShoppingCart(Map<ProductCard, Integer> shoppingCart, long productCardId, int quantity, String actionType) {
        ProductCard productCard = productCardRepository.findProductCarByIdAndProductStatusWithProducts(productCardId, "AVAILABLE");
        switch (actionType) {
            case "add":
                if (productCard == null) {
                    System.out.println("Невожно добавить продукт в корзину. Он отсутствует.");
                    return shoppingCart;
                }
                if (productCard.getProducts().size() < quantity) {
                    System.out.println("Невожно добавить такое количесво продуктов в корзину");
                    System.out.println("Доступно для заказа: " + productCard.getProducts().size());
                    return shoppingCart;
                }
                if (shoppingCart.containsKey(productCard)) {
                    shoppingCart.put(productCard, shoppingCart.get(productCard) + quantity);
                    if (shoppingCart.get(productCard) >= productCard.getProducts().size()){
                        shoppingCart.put(productCard, productCard.getProducts().size());
                    }
                } else {
                    shoppingCart.put(productCard, quantity);
                }
                break;
            case "delete":
                if (shoppingCart.containsKey(productCard)) {
                    shoppingCart.put(productCard, shoppingCart.get(productCard) - quantity);
                } else {
                    System.out.println("Вы пытаетесь удалить обьект которого нету в заказе");
                }
                break;
        }
        return shoppingCart;
    }
}

