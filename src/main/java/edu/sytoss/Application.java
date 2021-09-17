package edu.sytoss;

import edu.sytoss.UI.Menu;
import edu.sytoss.config.ApplicationConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
    public static void main(String[] args) {
        System.out.println("Application started!");
        Menu menu = applicationContext.getBean(Menu.class);
        menu.start();
    }
}
