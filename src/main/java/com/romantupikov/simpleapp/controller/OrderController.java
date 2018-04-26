package com.romantupikov.simpleapp.controller;

import com.romantupikov.simpleapp.service.OrderService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class OrderController extends AbstractController {

    @EJB
    private OrderService orderService;
}
