package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.OrderRepository;
import edu.sytoss.service.OrderAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderAPIImpl implements OrderAPI {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
