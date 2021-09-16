package edu.sytoss;

import edu.sytoss.UI.Menu;
import edu.sytoss.config.ApplicationConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Level;

public class Application {


    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        System.out.println("Application started!");
        Menu menu = applicationContext.getBean(Menu.class);
        menu.start();
    }
}
