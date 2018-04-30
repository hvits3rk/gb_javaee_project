package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Category;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CategoryService {

    public List<Category> getCategoryList();

    public void persist(Category category);

    public Category getCategoryById(String id);

    public void merge(Category category);

    public void removeCategory(Category category);

    public void removeCategory(String categoryId);
}
