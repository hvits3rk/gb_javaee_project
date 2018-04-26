package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Category;
import com.romantupikov.simpleapp.interceptor.Logger;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoryBean implements CategoryService {

    @PersistenceContext(unitName = "persistence-unit")
    private EntityManager em;

    @Override
    @Interceptors({Logger.class})
    public List<Category> getListCategory() {
        return em.createQuery("SELECT e FROM Category e", Category.class).getResultList();
    }

    @Override
    @Interceptors({Logger.class})
    public void persist(Category category) {
        if (category == null) return;
        em.persist(category);
    }

    @Override
    @Interceptors({Logger.class})
    public Category getCategoryById(String id) {
        if (id == null) return null;
        return em.find(Category.class, id);
    }

    @Override
    @Interceptors({Logger.class})
    public void merge(Category category) {
        if (category == null) return;
        em.merge(category);
    }

    @Override
    @Interceptors({Logger.class})
    public void removeCategory(Category category) {
        if (category == null) return;
        em.remove(category);
    }

    @Override
    @Interceptors({Logger.class})
    public void removeCategory(String categoryId) {
        if (categoryId == null || categoryId.isEmpty()) return;
        Category product = em.find(Category.class, categoryId);
        em.remove(product);
    }
}
