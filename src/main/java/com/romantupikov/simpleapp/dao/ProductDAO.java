package com.romantupikov.simpleapp.dao;

import com.romantupikov.simpleapp.model.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Named
@ApplicationScoped
public class ProductDAO {

    private Map<String, Product> products;

    public ProductDAO() {
        products = new LinkedHashMap<>(9);

        for (int i = 1; i < 10; i++) {
            Product product = new Product();
            String id = UUID.randomUUID().toString();
            product.setId(id);
            String productName = "Product " + i;
            product.setName(productName);
            product.setDescription("This is the " + productName);
            products.put(id, product);
        }
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public void addNewProduct(Product product) {
        String id = UUID.randomUUID().toString();
        product.setId(id);
        products.put(id, product);
    }
}
