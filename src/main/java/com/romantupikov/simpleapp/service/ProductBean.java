package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Product;
import com.romantupikov.simpleapp.interceptor.Logger;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Interceptors({Logger.class})
public class ProductBean implements ProductService {

    @PersistenceContext(unitName = "webapp-persistence-unit")
    private EntityManager em;

    @Override
    public List<Product> getListProductByCategoryId(String categoryId) {
        if (categoryId == null || categoryId.isEmpty()) return getProductList();
        return em.createQuery("SELECT e FROM Product e WHERE e.category.id = :categoryId", Product.class)
                .setParameter("categoryId", categoryId).getResultList();
    }

    @Override
    public List<Product> getProductList() {
        return em.createQuery("SELECT e FROM Product e", Product.class).getResultList();
    }

    @Override
    public void persist(Product product) {
        if (product == null) return;
        em.persist(product);
    }

    @Override
    public Product getProductById(String id) {
        if (id == null) return null;
        return em.find(Product.class, id);
    }

    @Override
    public Product getProductByName(String name) {
        if (name == null) return null;
        return em.createQuery("SELECT e from Product e where e.name = :productName", Product.class)
                .setParameter("productName", name).getSingleResult();
    }

    @Override
    public void merge(Product product) {
        if (product == null) return;
        em.merge(product);
    }

    @Override
    public void removeProduct(String productId) {
        if (productId == null || productId.isEmpty()) return;
        Product product = em.find(Product.class, productId);
        em.remove(product);
    }
}
