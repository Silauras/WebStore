package edu.sytoss;

import edu.sytoss.config.HibernateConfiguration;
import edu.sytoss.model.product.Price;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.UserAccountDao;
import edu.sytoss.repositoryImpl.UserAccountDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(HibernateConfiguration.class);
        UserAccountDaoImpl userAccountDao = appContext.getBean(UserAccountDaoImpl.class);
        for (UserAccount userAccount :
                userAccountDao.findAllUserAccounts()) {
            System.out.println(userAccount.toString());
        }
    }
}
