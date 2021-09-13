package edu.sytoss;

import edu.sytoss.config.ApplicationConfiguration;
import edu.sytoss.service.ProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        ProductService productService = appContext.getBean(ProductService.class);
        System.out.println(productService.findById(2L).toString());
    }



}
