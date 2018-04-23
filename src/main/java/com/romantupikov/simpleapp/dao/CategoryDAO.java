package com.romantupikov.simpleapp.dao;

import com.romantupikov.simpleapp.entity.Category;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class CategoryDAO extends AbstractDAO {

    public List<Category> getListCategory() {
        return em.createQuery("SELECT e FROM Category e", Category.class).getResultList();
    }

    public void persist(Category category) {
        if (category == null) return;
        em.persist(category);
    }

    public Category getCategoryById(String id) {
        if (id == null) return null;
        return em.find(Category.class, id);
    }

    public void merge(Category category) {
        if (category == null) return;
        em.merge(category);
    }

    public void removeCategory(Category category) {
        if (category == null) return;
        em.remove(category);
    }

    public void removeCategory(String categoryId) {
        if (categoryId == null || categoryId.isEmpty()) return;
        Category product = em.find(Category.class, categoryId);
        em.remove(product);
    }
}
