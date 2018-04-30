package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Product;

import javax.ejb.Local;
import javax.transaction.SystemException;
import java.util.List;

@Local
public interface ProductService {

    public List<Product> getListProductByCategoryId(String categoryId);

    public List<Product> getProductList();

    public void persist(Product product);

    public Product getProductById(String id);

    public Product getProductByName(String name);

    public void merge(Product product);

    public void removeProduct(String productId);
}
