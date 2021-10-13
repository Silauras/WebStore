package edu.sytoss.controller;

import edu.sytoss.UI.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    private final Menu menu;

    public MenuController(Menu menu) {
        this.menu = menu;
    }

    @GetMapping("/menu")
    public void startMenu(){
        menu.start();
    }
}
