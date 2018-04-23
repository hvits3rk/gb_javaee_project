package com.romantupikov.simpleapp.dao;

import com.romantupikov.simpleapp.entity.Product;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ProductDAO extends AbstractDAO {

    public List<Product> getListProductByCategoryId(String categoryId) {
        if (categoryId == null || categoryId.isEmpty()) return getListProduct();
        return em.createQuery("SELECT e FROM Product e WHERE e.category.id = :categoryId", Product.class)
                .setParameter("categoryId", categoryId).getResultList();
    }

    public List<Product> getListProduct() {
        return em.createQuery("SELECT e FROM Product e", Product.class).getResultList();
    }

    public void persist(Product product) {
        if (product == null) return;
        em.persist(product);
    }

    public Product getProductById(String id) {
        if (id == null) return null;
        return em.find(Product.class, id);
    }

    public void merge(Product product) {
        if (product == null) return;
        em.merge(product);
    }

    public void removeProduct(Product product) {
        if (product == null) return;
        em.remove(product);
    }

    public void removeProduct(String productId) {
        if (productId == null || productId.isEmpty()) return;
        Product product = em.find(Product.class, productId);
        em.remove(product);
    }
}
