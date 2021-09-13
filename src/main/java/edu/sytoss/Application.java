package edu.sytoss;

import edu.sytoss.config.HibernateConfiguration;
import edu.sytoss.model.product.Price;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.UserAccountDao;
import edu.sytoss.repositoryImpl.ProductDaoImpl;
import edu.sytoss.repositoryImpl.UserAccountDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(HibernateConfiguration.class);
        ProductDaoImpl productDao = appContext.getBean(ProductDaoImpl.class);
        for (Product product : productDao.findAllProducts()) {
            System.out.println(product.toString());
            System.out.println(product.getProductTemplate().getCategory().getName());
        }
    }
}
