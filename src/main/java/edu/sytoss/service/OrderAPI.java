package edu.sytoss.service;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface OrderAPI {

     Order findOrderById(Long id);
}
