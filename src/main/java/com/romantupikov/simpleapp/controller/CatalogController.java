package com.romantupikov.simpleapp.controller;

import com.romantupikov.simpleapp.dao.ProductDAO;
import com.romantupikov.simpleapp.model.Product;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ConversationScoped
public class CatalogController implements Serializable {

    @Inject
    private ProductDAO productDAO;

    private Product product;

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
