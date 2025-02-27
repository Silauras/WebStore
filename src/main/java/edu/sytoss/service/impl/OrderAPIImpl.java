package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Kit;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.KitRepository;
import edu.sytoss.repository.OrderRepository;
import edu.sytoss.repository.ProductCardRepository;
import edu.sytoss.service.OrderAPI;
import edu.sytoss.service.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderAPIImpl implements OrderAPI {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductCardRepository productCardRepository;
    @Autowired
    KitRepository kitRepository;
    @Autowired
    ProductApi productApi;

    /*---------------------------------Order-------------------------------*/

    @Transactional
    @Override
    public List<Product> findAllProductInOrderById(Long id) {
        Order order = orderRepository.findOrderWithProductsById(id);
        return order.getProducts();
    }

    @Transactional
    @Override
    public List<ProductCard> findAllProductCartsInOrderById(Long id) {
        List<Product> products = orderRepository.findOrderWithProductCartsById(id).getProducts();
        List<ProductCard> productCards = new ArrayList<>();
        for (Product product : products) {
            productCards.add(product.getProductCard());
        }
        return productCards;
    }

    @Transactional
    @Override
    public boolean createOrder(Order order) {
        try {
            order.setState("NEW");
            order.setLastChangeDate(new Date());
            orderRepository.saveAndFlush(order);
            return true;
        } catch (NullPointerException e) {
            return false;
        }

    }
    /*--------------------------ShoppingCart-----------------------------*/
    @Transactional
    @Override
    public Map<ProductCard, Integer> createShoppingCartWithCard(UserAccount userAccount) {
        List<Order> shoppingCartWithCard = new ArrayList<>();
        List<ProductCard> productCards = new ArrayList<>();
        for (Order order : shoppingCartWithCard) {
            productCards.addAll(findAllProductCartsInOrderById(order.getId()));
        }
        /*Create Template for  Shopping Cart*/
        Map<ProductCard, Integer> cardsMap = new HashMap<>();
        for (ProductCard productCard : productCards) {
            cardsMap.put(productCard, 1);
        }
        /*Count items in Shopping Cart*/
        for (ProductCard productCard : cardsMap.keySet()) {
            int count = 1;
            for (ProductCard productCardOrder : productCards) {
                if (productCard.equals(productCardOrder)) {
                    cardsMap.put(productCard, count++);
                }
            }
        }
        return cardsMap;
    }

    @Transactional
    @Override
    public Map<Kit, Integer> createShoppingCartWithKit(UserAccount userAccount) {
        Map<Kit, Integer> cardsMap = new HashMap<>();
        return cardsMap;
    }

    @Transactional
    @Override
    public Map<ProductCard, Integer> updateShoppingCart(Map<ProductCard, Integer> shoppingCartWithCard, long productCardId, int quantity, String actionType) {
        ProductCard productCard = productCardRepository.findProductCarByIdAndProductStatusWithProducts(productCardId, "AVAILABLE");
        switch (actionType) {
            case "add":
                if (productCard == null) {
                    System.out.println("Невожно добавить продукт в корзину. Он отсутствует.");
                    return shoppingCartWithCard;
                }
                if (productCard.getProducts().size() < quantity) {
                    System.out.println("Невожно добавить такое количесво продуктов в корзину");
                    System.out.println("Доступно для заказа: " + productCard.getProducts().size());
                    return shoppingCartWithCard;
                }
                if (shoppingCartWithCard.containsKey(productCard)) {
                    shoppingCartWithCard.put(productCard, shoppingCartWithCard.get(productCard) + quantity);
                    if (shoppingCartWithCard.get(productCard) >= productCard.getProducts().size()) {
                        shoppingCartWithCard.put(productCard, productCard.getProducts().size());
                    }
                } else {
                    shoppingCartWithCard.put(productCard, quantity);
                }
                break;
            case "delete":
                if (shoppingCartWithCard.containsKey(productCard)) {
                    shoppingCartWithCard.put(productCard, shoppingCartWithCard.get(productCard) - quantity);
                } else {
                    System.out.println("Вы пытаетесь удалить обьект которого нету в заказе");
                }
                break;
        }
        return shoppingCartWithCard;
    }

    @Transactional
    @Override
    public Map<Kit, Integer> updateShoppingCartKit(Map<Kit, Integer> shoppingCartWithKit, long kitId, int quantity, String actionType) {
        Kit kit = kitRepository.findKitByIdAndProductStatusWithProducts(kitId, "AVAILABLE");
        Set<ProductCard> productCards = kit.getProductCards();
        int maxCountKit = 0;
        switch (actionType) {
            case "add":
                for (ProductCard productCard : productCards) {
                    if (productCard == null) {
                        System.out.println("Невожно добавить продукт в корзину. Он отсутствует.");
                        return shoppingCartWithKit;
                    }
                    if (productCard.getProducts().size() < quantity) {
                        System.out.println("Невожно добавить такое количесво продуктов в корзину");
                        System.out.println("Доступно для заказа: " + productCard.getProducts().size());
                        return shoppingCartWithKit;
                    }
                    maxCountKit = productCard.getProducts().size();
                }
                if (shoppingCartWithKit.containsKey(kit)) {
                    shoppingCartWithKit.put(kit, shoppingCartWithKit.get(kit) + quantity);
                    if (shoppingCartWithKit.get(kit) >= maxCountKit) {
                        shoppingCartWithKit.put(kit, maxCountKit);
                    }
                } else {
                    shoppingCartWithKit.put(kit, quantity);
                }
                break;
            case "delete":
                if (shoppingCartWithKit.containsKey(kit)) {
                    shoppingCartWithKit.put(kit, shoppingCartWithKit.get(kit) - quantity);
                } else {
                    System.out.println("Вы пытаетесь удалить комлпект которого нету в заказе");
                }
                break;
        }
        return shoppingCartWithKit;
    }
}

