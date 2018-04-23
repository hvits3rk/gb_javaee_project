package com.romantupikov.simpleapp.dao;

import com.romantupikov.simpleapp.entity.Order;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class OrderDAO extends AbstractDAO {

    public List<Order> getListOrder() {
        return em.createQuery("SELECT e FROM Order e", Order.class).getResultList();
    }

    public void persist(Order order) {
        if (order == null) return;
        em.persist(order);
    }

    public Order getOrderById(String id) {
        if (id == null) return null;
        return em.find(Order.class, id);
    }

    public void merge(Order order) {
        if (order == null) return;
        em.merge(order);
    }

    public void removeOrder(Order order) {
        if (order == null) return;
        em.remove(order);
    }

    public void removeOrder(String orderId) {
        if (orderId == null || orderId.isEmpty()) return;
        Order product = em.find(Order.class, orderId);
        em.remove(product);
    }
}
