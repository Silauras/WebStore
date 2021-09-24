package edu.sytoss.UI;

import edu.sytoss.model.communication.Reaction;
import edu.sytoss.model.order.Order;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.service.OrderAPI;
import edu.sytoss.service.UserAccountAPI;
import edu.sytoss.service.impl.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.sytoss.UI.MenuUtils.printMenu;
import static edu.sytoss.UI.MenuUtils.scanInt;

@Service
public class OrderMenu {
    @Autowired
    OrderAPI orderAPI;
    @Autowired
    PriceCalculator priceCalculator;

    public void start() {
        printMenu(
                "-1. Quit",
                "1. Show UserAccount",
                "2. Create UserAccount",
                "3. Update UserAccount",
                "4. Calculate price by Order"
        );
        switch (scanInt("Your choice")) {
            case -1:
                return;
            case 1:
                findOrder();
                break;
            case 2:

                break;
            case 3:
                break;
            case 4:
                MenuUtils.printField("price",
                        priceCalculator.calculatePriceForOrder((long) scanInt("Write order id")).toString());
                break;
        }
    }

    private void findOrder() {
        printMenu(
                "-1.Quit",
                "1. Find User by id"
        );

        switch (scanInt("")) {
            case -1:
                return;
            case 1:
                findOrderById();
                break;
            case 2:
                break;

        }
    }
    private void findOrderById() {
        printMenu(
                "What you want to see?",
                "1. Show Oder with main info"

        );
        long oderId;
        switch (scanInt("")) {
            case 1:
                oderId = scanInt("Write Order id(0 for all): ");
                if (oderId == 0) {
                    //new OrderPrinter();
                } else {
                    new OrderPrinter(oderId);
                    break;
                }
                break;
            case 2:

                break;

        }
    }

    private class OrderPrinter {
        private OrderPrinter(Long orderId) {
            printOrderById(orderId);
        }

        private void printOrderById(Long orderId) {
           /* Order order = orderAPI.findOrderById(orderId);
            System.out.println(reactions.toString());*/
        }
    }
}
