package com.romantupikov.simpleapp.controller;

import com.romantupikov.simpleapp.dao.ProductDAO;
import com.romantupikov.simpleapp.entity.Product;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.List;

@ViewScoped
@ManagedBean
public class CatalogController {

    @Inject
    private ProductDAO productDAO;

    private Product product;

    public List<Product> getProducts() {
        return productDAO.getListProduct();
    }

    public void addNewProduct() {
        productDAO.persist(product);
    }

    public void saveProduct() {
        productDAO.merge(product);
    }

    public void removeProduct(String productId) {
        productDAO.removeProduct(productId);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
