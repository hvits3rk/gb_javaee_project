package com.romantupikov.simpleapp.controller;

import com.romantupikov.simpleapp.dao.ProductDAO;
import com.romantupikov.simpleapp.model.Product;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class CatalogController implements Serializable {

    @Inject
    private ProductDAO productDAO;

    public List<Product> getProducts() {
        return new ArrayList<>(productDAO.getAllProducts());
    }
}
