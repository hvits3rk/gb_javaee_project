package com.romantupikov.simpleapp.controller;

import com.romantupikov.simpleapp.entity.Product;
import com.romantupikov.simpleapp.service.ProductService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class ProductEditController extends AbstractController {

    private final String id = getParamString("id");

    @EJB
    private ProductService productService;

    private Product product;

    @PostConstruct
    private void init() {
        product = productService.getProductById(id);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String save() {
        productService.merge(product);
        return "catalog";
    }
}
