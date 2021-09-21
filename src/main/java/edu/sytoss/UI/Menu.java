package edu.sytoss.UI;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static edu.sytoss.UI.MenuUtils.scanInt;

@NoArgsConstructor
@Service
public class Menu {
    @Autowired
    UserAccountMenu userAccountMenu;
    @Autowired
    ProductCardMenu productCardMenu;


    public void start() {
        while (true) {
            MenuUtils.printMenu(
                    "What you want to see?",
                    "-1. Quit",
                    "1. Go to User Account Menu",
                    "2. Go to Product Menu"
            );
            switch (scanInt()) {
                case -1:
                    return;
                case 1:
                    userAccountMenu.start();
                    break;
                case 2:
                    productCardMenu.start();
                    break;
            }
        }
    }
}