package edu.sytoss.UI;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.shop.Shop;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.service.OrderAPI;
import edu.sytoss.service.ProductApi;
import edu.sytoss.service.UserAccountAPI;
import edu.sytoss.service.impl.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.sytoss.UI.MenuUtils.*;

@Service
public class OrderMenu {
    @Autowired
    OrderAPI orderAPI;
    @Autowired
    PriceCalculator priceCalculator;
    @Autowired
    UserAccountAPI userAccountAPI;
    @Autowired
    ProductApi productApi;

    public void start() {
        long userAccountId = scanInt("Введите UserAccount Id");
        UserAccount userAccount = userAccountAPI.findUserAccount(new UserAccount(userAccountId)).get(0);
        Map<ProductCard, Integer> shoppingCart = orderAPI.createShoppingCart(userAccount);
        new ShoppingCartPrinter(shoppingCart);
        while (true) {
            printMenu(
                    "-1. Log Out",
                    "1. Show Shopping Cart",
                    "2. Update Shopping Cart",
                    "3. Calculate price by Order"
            );
            switch (scanInt("Your choice")) {
                case -1:
                    return;
                case 1:
                    findShoppingCart(userAccount, shoppingCart);
                    break;
                case 2:
                    updateShoppingCart(userAccount, shoppingCart);
                    break;
                case 3:
                    long orderId = scanInt("Write order id: ");
                    printField("price without sale", priceCalculator.calculatePriceForOrderWithoutSale(orderId).toString());
                    printField("price", priceCalculator.calculatePriceForOrderWithSale(orderId).toString());
                    break;
            }
        }
    }

    private void updateShoppingCart(UserAccount userAccount, Map<ProductCard, Integer> shoppingCart) {
        new ShoppingCartPrinter(shoppingCart);
        printMenu(
                "-1.Quit",
                "1. Add product",
                "2. Delete product",
                "3. Pay"
        );
        switch (scanInt("Your choice")) {
            case -1:
                return;
            case 1:
                updatedProductInShoppingCart(userAccount, shoppingCart, "add");
                break;
            case 2:
                updatedProductInShoppingCart(userAccount, shoppingCart, "delete");
                break;
            case 3:
                payShoppingCart(userAccount, shoppingCart);
                break;
            case 4:

                break;
        }
    }

    private void findShoppingCart(UserAccount userAccount, Map<ProductCard, Integer> shoppingCart) {
        if (userAccountAPI.findUserAccountWithStateOrder(userAccount, "NEW") == null) {
            System.out.println("Ваша корзина пуста");
        } else {
            new ShoppingCartPrinter(shoppingCart);
        }
    }

    private void updatedProductInShoppingCart(UserAccount userAccount, Map<ProductCard, Integer> shoppingCart, String actionType) {
        long productCardId = MenuUtils.scanInt("Write id ProductCard");
        int quantity = MenuUtils.scanInt("Write Quantity");
        Map<ProductCard, Integer> newShoppingCart = orderAPI.updateShoppingCart(shoppingCart, productCardId, quantity, actionType);
        new ShoppingCartPrinter(newShoppingCart);
    }

    /**
     * <p>
     * Class that Print Shopping Cart
     * </p>
     *
     * @author Andrey Kolesnyk
     */
    private class ShoppingCartPrinter {
        public ShoppingCartPrinter(Map<ProductCard, Integer> shoppingCart) {
            printProductByUserAccountId(shoppingCart);
        }

        private void printProductByUserAccountId(Map<ProductCard, Integer> shoppingCart) {
            System.out.println("В вашей корзине:");
            for (ProductCard productCard : shoppingCart.keySet()) {
                if (shoppingCart.get(productCard) == 0) {
                    shoppingCart.remove(productCard);
                } else {
                    System.out.println(productCard.getName() + "=" + shoppingCart.get(productCard));
                }
            }
        }
    }

    private void payShoppingCart(UserAccount userAccount, Map<ProductCard, Integer> shoppingCart) {
        Map<Shop, List<Product>> productByShop = productApi.dividingProductsIntoOrders(shoppingCart);
        System.out.println("---Магазины в которых вы сделалил покупку---");
        for (Shop shop : productByShop.keySet()) {
            System.out.println(shop.getName());
        }
        System.out.println("---Ваши заказы---");
        Map<ProductCard, Integer> productCardIntegerMap = new HashMap<>();
        /*Вывод карточек заказа по продуктам в заказе*/
        int count = 1;
        for (Shop shop : productByShop.keySet()) {
            for (Product product : productByShop.get(shop)) {
                if (!productCardIntegerMap.containsKey(product.getProductCard())) {
                    productCardIntegerMap.put(product.getProductCard(), count);
                } else {
                    productCardIntegerMap.put(product.getProductCard(), ++count);
                }
            }
            System.out.println(shop.getName());
            System.out.println(productCardIntegerMap);
            count = 1;
        }

        for (Shop shop : productByShop.keySet()) {
            Order order = new Order(userAccount, shop);
            orderAPI.createOrder(order, productByShop.get(shop));
        }
    }

    /**************************Старое**********************************************/

    private void findOrdersByUserId() {
        long orderId = scanInt("Write Order id(0 for all): ");
        printMenu(
                "What you want to see?",
                "1. Show Oder with main info",
                "2. Show Oder with Products",
                "3. Show Oder with ProductCarts"
        );

        switch (scanInt("")) {
            case 1:
                if (orderId == 0) {
                    List<Order> orders = orderAPI.findAllOrder();
                    for (Order order : orders) {
                        new OrderPrinter(order.getId());
                    }
                } else {
                    new OrderPrinter(orderId);
                    break;
                }
                break;
            case 2:
                if (orderId == 0) {
                    List<Order> orders = orderAPI.findAllOrder();
                    for (Order order : orders) {
                        new OrderPrinter(order.getId());
                        new ProductPrinter(order.getId());
                    }
                } else {
                    new OrderPrinter(orderId);
                    new ProductPrinter(orderId);
                    break;
                }
                break;
            case 3:
                if (orderId == 0) {
                    List<Order> orders = orderAPI.findAllOrder();
                    for (Order order : orders) {
                        new OrderPrinter(order.getId());
                        new CartsPrinter(order.getId());
                    }
                } else {
                    new OrderPrinter(orderId);
                    new CartsPrinter(orderId);
                    break;
                }
                break;
        }
        new PricePrinter(orderId);
    }

    private class OrderPrinter {
        private OrderPrinter(Long orderId) {
            printOrderById(orderId);
        }

        private void printOrderById(Long orderId) {
            Order order = orderAPI.findOrderById(orderId);
            System.out.println(order.toString());
        }
    }

    private class ProductPrinter {
        private ProductPrinter(Long orderId) {
            printProductById(orderId);
        }

        private void printProductById(Long orderId) {
            List<Product> products = orderAPI.findAllProductInOrderById(orderId);
            System.out.println(products.toString());
        }
    }

    private class CartsPrinter {
        public CartsPrinter(Long orderId) {
            printProductById(orderId);
        }

        private void printProductById(Long orderId) {
            List<ProductCard> productCards = orderAPI.findAllProductCartsInOrderById(orderId);
            Map<String, Integer> cardsMap = new HashMap<>();
            int count = 1;
            for (ProductCard productCard : productCards) {
                cardsMap.put(productCard.getName(), count);
            }
            for (String nameProduct : cardsMap.keySet()) {
                for (ProductCard productCard : productCards) {
                    if (nameProduct.equals(productCard.getName())) {
                        cardsMap.put(nameProduct, count++);
                    }
                }
                count = 1;
            }
            System.out.println(cardsMap);
        }
    }

    private class PricePrinter {
        public PricePrinter(long orderId) {
            printOrderPriceById(orderId);
        }

        private void printOrderPriceById(Long orderId) {
            priceCalculator.calculatePriceForOrderWithSale(orderId);
        }
    }


}

