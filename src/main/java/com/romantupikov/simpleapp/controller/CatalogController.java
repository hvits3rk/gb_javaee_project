package com.romantupikov.simpleapp.controller;

import com.romantupikov.simpleapp.entity.Product;
import com.romantupikov.simpleapp.service.ProductService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ViewScoped
@ManagedBean
public class CatalogController {

    @EJB
    private ProductService productService;

    private Product product;

    public List<Product> getProducts() {
        return productService.getListProduct();
    }

    public void addNewProduct() {
        productService.persist(product);
    }

    public void saveProduct() {
        productService.merge(product);
    }

    public void removeProduct(String productId) {
        productService.removeProduct(productId);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
