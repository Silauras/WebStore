package edu.sytoss.UI;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.service.OrderAPI;
import edu.sytoss.service.UserAccountAPI;
import edu.sytoss.service.impl.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static edu.sytoss.UI.MenuUtils.printMenu;
import static edu.sytoss.UI.MenuUtils.scanInt;

import java.util.Map;


@Service
public class OrderMenu {
    @Autowired
    OrderAPI orderAPI;
    @Autowired
    PriceCalculator priceCalculator;

    public void start() {
        printMenu(
                "-1. Quit",
                "1. Show Order",
                "2. Update Order",
                "3. Calculate price by Order"
        );
        switch (scanInt("Your choice")) {
            case -1:
                return;
            case 1:
                findOrder();
                break;
            case 2:
                updateOrder();
                break;
            case 3:
                MenuUtils.printField("price",
                        priceCalculator.calculatePriceForOrder((long) scanInt("Write order id")).toString());
                break;
        }
    }

    private void findOrder() {
        printMenu(
                "-1.Quit",
                "1. Find Order by id"
        );

        switch (scanInt()) {
            case -1:
                return;
            case 1:
                findOrderById();
                break;
        }
    }

    private void updateOrder() {
        long oderId = MenuUtils.scanInt("Write id Order");
        new OrderPrinter(oderId);
        new CartsPrinter(oderId);
        long productCardId = MenuUtils.scanInt("Write id ProductCard");
        ProductCard productCard = orderAPI.findProductCardById(productCardId);
    }

    private void findOrderById() {
        printMenu(
                "What you want to see?",
                "1. Show Oder with main info",
                "2. Show Oder with Products",
                "3. Show Oder with ProductCarts"
        );
        long oderId;
        switch (scanInt("")) {
            case 1:
                oderId = scanInt("Write Order id(0 for all): ");
                if (oderId == 0) {
                    List<Order> orders = orderAPI.findAllOrder();
                    for (Order order : orders) {
                        new OrderPrinter(order.getId());
                    }
                } else {
                    new OrderPrinter(oderId);
                    break;
                }
                break;
            case 2:
                oderId = MenuUtils.scanInt("Write Order id(0 for all): ");
                if (oderId == 0) {
                    List<Order> orders = orderAPI.findAllOrder();
                    for (Order order : orders) {
                        new OrderPrinter(order.getId());
                        new ProductPrinter(order.getId());
                    }
                } else {
                    new OrderPrinter(oderId);
                    new ProductPrinter(oderId);
                    break;
                }
                break;
            case 3:
                oderId = MenuUtils.scanInt("Write Order id(0 for all): ");
                if (oderId == 0) {
                    List<Order> orders = orderAPI.findAllOrder();
                    for (Order order : orders) {
                        new OrderPrinter(order.getId());
                        new CartsPrinter(order.getId());
                    }
                } else {
                    new OrderPrinter(oderId);
                    new CartsPrinter(oderId);
                    break;
                }
                break;
        }
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
            int count =1;
            for (ProductCard productCard : productCards) {
                cardsMap.put(productCard.getName(), count);
            }
            for (String nameProduct : cardsMap.keySet()) {
                for (ProductCard productCard : productCards) {
                    if (nameProduct.equals(productCard.getName()))
                    {
                        cardsMap.put(nameProduct,count++);
                    }
                }
                count=1;
            }
            System.out.println(cardsMap);
        }
    }
}

