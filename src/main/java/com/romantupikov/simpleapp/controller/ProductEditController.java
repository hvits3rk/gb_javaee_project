package com.romantupikov.simpleapp.controller;

import com.romantupikov.simpleapp.dao.ProductDAO;
import com.romantupikov.simpleapp.model.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ProductEditController extends AbstractController {

    private final String id = getParamString("id");

    @Inject
    private ProductDAO productDAO;

    private Product product;

    @PostConstruct
    private void init() {
        product = productDAO.getProductById(id);
        System.out.println(product);
    }

    public String save() {
        System.out.println("Save:\n" + product);
        productDAO.merge(product);
        return "catalog";
    }

    public Product getProduct() {
        System.out.println("getProduct:\n" + product);
        return product;
    }

    public void setProduct(Product product) {
        System.out.println("setProduct:\n" + product);
        this.product = product;
    }
}
