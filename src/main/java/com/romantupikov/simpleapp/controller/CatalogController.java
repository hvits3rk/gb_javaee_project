package com.romantupikov.simpleapp.controller;

import com.romantupikov.simpleapp.dao.ProductDAO;
import com.romantupikov.simpleapp.model.Product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class CatalogController {

    @Inject
    private ProductDAO productDAO;

    private Product product = new Product();

    public List<Product> getProducts() {
        return new ArrayList<>(productDAO.getAllProducts());
    }

    public void addNewProduct() {
        productDAO.addNewProduct(product);
    }

    public void saveProduct() {
        productDAO.merge(product);
    }

    public void removeProduct(String productId) {
        productDAO.removeProductById(productId);
    }

    public String test() {
        return "index";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
