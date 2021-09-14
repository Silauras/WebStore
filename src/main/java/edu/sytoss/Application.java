package edu.sytoss;

import edu.sytoss.config.ApplicationConfiguration;
import edu.sytoss.service.UserAccountAPI;
import edu.sytoss.service.impl.UserAccountAPIImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
       /* ProductService productService = appContext.getBean(ProductService.class);
        System.out.println(productService.findById(2L).toString());*/
        UserAccountAPI userAccountAPI = appContext.getBean(UserAccountAPI.class);
        System.out.println(userAccountAPI.showCommunication(1L));
        System.out.println(userAccountAPI.showSubscription(1L));
    }


}
