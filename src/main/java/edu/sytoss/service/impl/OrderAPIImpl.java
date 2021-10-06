package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Kit;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.KitRepository;
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
    KitRepository kitRepository;
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
    public Map<ProductCard, Integer> createShoppingCartWithCard(UserAccount userAccount) {
        List<Order> shoppingCartWithCard = new ArrayList<>();
        List<ProductCard> productCards = new ArrayList<>();
        for (Order order : shoppingCartWithCard) {
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
    public Map<Kit, Integer> createShoppingCartWithKit(UserAccount userAccount) {
        Map<Kit, Integer> cardsMap = new HashMap<>();
        return cardsMap;
    }

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
                    System.out.println("Вы пытаетесь удалить обьект которого нету в заказе");
                }
                break;
        }
        return shoppingCartWithKit;
    }
}

