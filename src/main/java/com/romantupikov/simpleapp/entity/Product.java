package com.romantupikov.simpleapp.entity;

import com.google.gson.annotations.Expose;
import com.romantupikov.simpleapp.enums.ProductType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Product extends AbstractEntity {

    @Expose
    @Column(nullable = false, columnDefinition = "TEXT")
    private String name = "";

    @Expose
    @Column
    private String description = "";

    @Expose
    private Date date;

    @Expose
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

    @Expose
    private Boolean publish;

    @Expose
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", publish=" + publish +
                ", productType=" + productType +
                "} " + super.toString();
    }
}
