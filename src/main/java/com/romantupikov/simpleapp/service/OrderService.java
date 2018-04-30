package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Order;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderService {

    public List<Order> getOrderList();

    public void persist(Order order);

    public Order getOrderById(String id);

    public void merge(Order order);

    public void removeOrder(Order order);

    public void removeOrder(String orderId);
}
