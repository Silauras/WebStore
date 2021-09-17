package edu.sytoss;

import edu.sytoss.UI.Menu;
import edu.sytoss.config.ApplicationConfiguration;

import edu.sytoss.model.user.UserAccountRole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Application {
    static ApplicationContext applicationContext =new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("Application started!");
        Menu menu = applicationContext.getBean(Menu.class);
        menu.start();
    }
}
