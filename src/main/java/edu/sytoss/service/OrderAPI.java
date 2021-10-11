package edu.sytoss.service;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Kit;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OrderAPI {
    /*---------------------------------Order-------------------------------*/

    List<Product> findAllProductInOrderById(Long id);

    List<ProductCard> findAllProductCartsInOrderById(Long id);

     boolean createOrder(Order order,List<Product> products);
     /*--------------------------ShoppingCart-----------------------------*/
     Map<ProductCard, Integer> createShoppingCartWithCard(UserAccount userAccount);

    Map<Kit, Integer> createShoppingCartWithKit(UserAccount userAccount);

    Map<ProductCard, Integer> updateShoppingCart(Map<ProductCard, Integer> shoppingCart, long productCardId, int quantity, String actionType);

    Map<Kit, Integer> updateShoppingCartKit(Map<Kit, Integer> shoppingCart, long kitId, int quantity, String actionType);


}
