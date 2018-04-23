package com.romantupikov.simpleapp.dto;

import com.romantupikov.simpleapp.entity.Product;

public class ProductRecord {

    private String id;

    private String name;

    private String categoryId;

    public ProductRecord(Product product) {
        id = product.getId();
        name = product.getName();
    }

    public ProductRecord() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
