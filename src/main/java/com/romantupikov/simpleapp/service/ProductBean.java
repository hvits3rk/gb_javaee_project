package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Product;
import com.romantupikov.simpleapp.interceptor.Logger;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductBean implements ProductService {

    @PersistenceContext(unitName = "persistence-unit")
    private EntityManager em;

    @Override
    @Interceptors({Logger.class})
    public List<Product> getListProductByCategoryId(String categoryId) {
        if (categoryId == null || categoryId.isEmpty()) return getListProduct();
        return em.createQuery("SELECT e FROM Product e WHERE e.category.id = :categoryId", Product.class)
                .setParameter("categoryId", categoryId).getResultList();
    }

    @Override
    @Interceptors({Logger.class})
    public List<Product> getListProduct() {
        return em.createQuery("SELECT e FROM Product e", Product.class).getResultList();
    }

    @Override
    @Interceptors({Logger.class})
    public void persist(Product product) {
        if (product == null) return;
        em.persist(product);
    }

    @Override
    @Interceptors({Logger.class})
    public Product getProductById(String id) {
        if (id == null) return null;
        return em.find(Product.class, id);
    }

    @Override
    @Interceptors({Logger.class})
    public void merge(Product product) {
        if (product == null) return;
        em.merge(product);
    }

    @Override
    @Interceptors({Logger.class})
    public void removeProduct(Product product) {
        if (product == null) return;
        em.remove(product);
    }

    @Override
    @Interceptors({Logger.class})
    public void removeProduct(String productId) {
        if (productId == null || productId.isEmpty()) return;
        Product product = em.find(Product.class, productId);
        em.remove(product);
    }
}
