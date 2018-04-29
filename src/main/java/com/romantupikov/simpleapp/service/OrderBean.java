package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Order;
import com.romantupikov.simpleapp.interceptor.Logger;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateful
public class OrderBean implements OrderService {

    @PersistenceContext(unitName = "webapp-persistence-unit")
    private EntityManager em;

    @Override
    @Interceptors({Logger.class})
    public List<Order> getListOrder() {
        return em.createQuery("SELECT e FROM Order e", Order.class).getResultList();
    }

    @Override
    @Interceptors({Logger.class})
    public void persist(Order order) {
        if (order == null) return;
        em.persist(order);
    }

    @Override
    @Interceptors({Logger.class})
    public Order getOrderById(String id) {
        if (id == null) return null;
        return em.find(Order.class, id);
    }

    @Override
    @Interceptors({Logger.class})
    public void merge(Order order) {
        if (order == null) return;
        em.merge(order);
    }

    @Override
    @Interceptors({Logger.class})
    public void removeOrder(Order order) {
        if (order == null) return;
        em.remove(order);
    }

    @Override
    @Interceptors({Logger.class})
    public void removeOrder(String orderId) {
        if (orderId == null || orderId.isEmpty()) return;
        Order product = em.find(Order.class, orderId);
        em.remove(product);
    }
}
