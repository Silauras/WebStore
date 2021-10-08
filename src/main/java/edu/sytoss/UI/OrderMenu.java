package edu.sytoss.UI;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Kit;
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
        Map<ProductCard, Integer> shoppingCartWithCard = orderAPI.createShoppingCartWithCard(userAccount);
        Map<Kit, Integer> shoppingCartWithKit = orderAPI.createShoppingCartWithKit(userAccount);
        while (true) {
            new ShoppingCartWithCardPrinter(shoppingCartWithCard);
            new ShoppingCartWithKitPrinter(shoppingCartWithKit);
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
                    findShoppingCart(userAccount, shoppingCartWithCard, shoppingCartWithKit);
                    break;
                case 2:
                    updateShoppingCart(userAccount, shoppingCartWithCard, shoppingCartWithKit);
                    break;
                case 3:
                    long orderId = scanInt("Write order id: ");
                    printField("price without sale", priceCalculator.calculatePriceForOrderWithoutSale(orderId).toString());
                    printField("price", priceCalculator.calculatePriceForOrderWithSale(orderId).toString());
                    break;
            }
        }
    }

    private void updateShoppingCart(UserAccount userAccount, Map<ProductCard, Integer> shoppingCartWithCard,
                                    Map<Kit, Integer> shoppingCartWithKit) {
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
                updatedProductInShoppingCart(userAccount, shoppingCartWithCard, shoppingCartWithKit, "add");
                break;
            case 2:
                updatedProductInShoppingCart(userAccount, shoppingCartWithCard, shoppingCartWithKit, "delete");
                break;
            case 3:
                payShoppingCart(userAccount, shoppingCartWithCard, shoppingCartWithKit);
                break;
        }
    }

    private void findShoppingCart(UserAccount userAccount, Map<ProductCard, Integer> shoppingCartWithCard,
                                  Map<Kit, Integer> shoppingCartWithKit) {
        if (userAccountAPI.findUserAccountWithStateOrder(userAccount, "NEW") == null) {
            System.out.println("Ваша корзина пуста");
        } else {
            new ShoppingCartWithCardPrinter(shoppingCartWithCard);
            new ShoppingCartWithKitPrinter(shoppingCartWithKit);
        }
    }

    private void updatedProductInShoppingCart(UserAccount userAccount, Map<ProductCard, Integer> shoppingCartWithCard,
                                              Map<Kit, Integer> shoppingCartWithKit, String actionType) {
        Map<Kit, Integer> newShoppingCartWithKit = new HashMap<>();
        Map<ProductCard, Integer> newShoppingCartWithCard = new HashMap<>();
        long productCardId = MenuUtils.scanInt("Write id ProductCard");
        if (productApi.findProductCardByIdWhitKits(productCardId).getKits().size() != 0) {
            String answer = scanLine("Do you want buy kit? 1 = Yes, 0 = No");
            if (answer.equals("1")) {
                int count = 1;
                List<Kit> kits = productApi.findKitByProductCard(productCardId);
                for (Kit kit : kits) {
                    System.out.println("№" + count++ + " " + kit.getName());
                }
                int numberKit = MenuUtils.scanInt("Write Number Kit");
                Kit kit = kits.get(numberKit - 1);
                int quantity = MenuUtils.scanInt("Write Quantity");
                newShoppingCartWithKit = orderAPI.updateShoppingCartKit(shoppingCartWithKit, kit.getId(), quantity, actionType);
            } else {
                int quantity = MenuUtils.scanInt("Write Quantity");
                newShoppingCartWithCard = orderAPI.updateShoppingCart(shoppingCartWithCard, productCardId, quantity, actionType);
            }
        } else {
            int quantity = MenuUtils.scanInt("Write Quantity");
            newShoppingCartWithCard = orderAPI.updateShoppingCart(shoppingCartWithCard, productCardId, quantity, actionType);
        }

    }

    private void payShoppingCart(UserAccount userAccount, Map<ProductCard, Integer> shoppingCartWithCard, Map<Kit, Integer> shoppingCartWithKit) {
        Map<Shop, List<Product>> productByShop = productApi.dividingProductsIntoOrders(shoppingCartWithCard,shoppingCartWithKit);
        new OrderPrinter(productByShop);
       /*for (Shop shop : productByShop.keySet()) {
            Order order = new Order(userAccount, shop);
            orderAPI.createOrder(order, productByShop.get(shop));
        }*/
    }

    /**
     * <p>
     * Class that Print Shopping Cart
     * </p>
     *
     * @author Andrey Kolesnyk
     */
    private class ShoppingCartWithCardPrinter {
        public ShoppingCartWithCardPrinter(Map<ProductCard, Integer> shoppingCartWithCard) {
            printProductByShoppingCart(shoppingCartWithCard);
        }

        private void printProductByShoppingCart(Map<ProductCard, Integer> shoppingCartWithCard) {
            System.out.println("В вашей корзине продукты:");
            for (ProductCard productCard : shoppingCartWithCard.keySet()) {
                if (shoppingCartWithCard.get(productCard) == 0) {
                    shoppingCartWithCard.remove(productCard);
                } else {
                    System.out.println(productCard.getName() + "=" + shoppingCartWithCard.get(productCard));
                }
            }
        }
    }

    /**
     * <p>
     * Class that Print Shopping Cart
     * </p>
     *
     * @author Andrey Kolesnyk
     */
    private class ShoppingCartWithKitPrinter {
        public ShoppingCartWithKitPrinter(Map<Kit, Integer> shoppingCartWithKit) {
            printProductByShoppingCart(shoppingCartWithKit);
        }

        private void printProductByShoppingCart(Map<Kit, Integer> shoppingCartWithKit) {
            System.out.println("В вашей корзине наборы:");
            for (Kit kit : shoppingCartWithKit.keySet()) {
                if (shoppingCartWithKit.get(kit) == 0) {
                    shoppingCartWithKit.remove(kit);
                } else {
                    System.out.println(kit.getName() + "=" + shoppingCartWithKit.get(kit));
                }
            }

        }
    }

    /**
     * <p>
     * Class that Print Shopping Order
     * </p>
     *
     * @author Andrey Kolesnyk
     */
    private class OrderPrinter {
        public OrderPrinter(Map<Shop, List<Product>> productByShop) {
            printOrderPrinterByUserAccountId(productByShop);
        }

        private void printOrderPrinterByUserAccountId(Map<Shop, List<Product>> productByShop) {
            System.out.println("---Ваши заказы---");
            for (Shop shop : productByShop.keySet()) {
                Map<ProductCard, Integer> productCardIntegerMap = new HashMap<>();
                for (Product product : productByShop.get(shop)) {
                    if (!productCardIntegerMap.containsKey(product.getProductCard())) {
                        productCardIntegerMap.put(product.getProductCard(), 1);
                    } else {
                        int count = productCardIntegerMap.get(product.getProductCard());
                        productCardIntegerMap.put(product.getProductCard(),++count);
                    }
                }
                System.out.println(shop.getName());
                System.out.println(productCardIntegerMap);
            }
        }
    }
}

