package com.romantupikov.simpleapp.repository;

import com.romantupikov.simpleapp.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductRepositoryImpl implements ProductRepository {

    private List<Product> productList;

    public ProductRepositoryImpl() {
        productList = new ArrayList<>(9);

        for (int i = 1; i < 10; i++) {
            Product product = new Product();
            product.setId(UUID.randomUUID().toString());
            String productName = "Product " + i;
            product.setName(productName);
            product.setDescription("This is the " + productName);
            productList.add(product);
        }
    }

    @Override
    public List<Product> getAllProducts() {

        return productList;
    }

}
