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

    public List<Product> getProducts() {
        return new ArrayList<>(productDAO.getAllProducts());
    }
}
