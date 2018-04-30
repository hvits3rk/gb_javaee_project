package com.romantupikov.simpleapp.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.romantupikov.simpleapp.bean.GsonBean;
import com.romantupikov.simpleapp.entity.Category;
import com.romantupikov.simpleapp.entity.Product;
import com.romantupikov.simpleapp.enums.ProductType;
import com.romantupikov.simpleapp.service.CategoryService;
import com.romantupikov.simpleapp.service.ProductService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Date;

//      +++  a. Добавлять товар в БД;
//      +++  b. Удалять товар из БД;
//      +++  c. Добавлять категорию товара;
//      +++  d. Получить товар по Id;
//      +++  e. Получить товар по имени;
//      +++  f. Получить все товары;
//      +++  g. Получить товары по Id категории.

@Path("product")
public class ProductController {

    @EJB
    private ProductService productService;

    @EJB
    private CategoryService categoryService;

    @Inject
    private GsonBean gsonBean;

    @GET
    @Produces("application/json")
    @Path("/all")
    public String getAllProducts() {
        return gsonBean.getGson().toJson(productService.getProductList());
    }

    @GET
    @Produces("application/json")
    @Path("/category/{categoryId}")
    public String getProductsByCategoryId(@PathParam("categoryId") String categoryId) {
        return gsonBean.getGson().toJson(productService.getListProductByCategoryId(categoryId));
    }

    @GET
    @Produces("application/json")
    @Path("/{productId}")
    public String getProductById(@PathParam("productId") String productId) {
        return gsonBean.getGson().toJson(productService.getProductById(productId));
    }

    @GET
    @Produces("application/json")
    @Path("/{productName}")
    public String getProductByName(@PathParam("productName") String productName) {
        String name = productName.replace("_", " ");
        return gsonBean.getGson().toJson(productService.getProductByName(name));
    }

    @DELETE
    @Produces("application/json")
    @Path("/delete/{productId}")
    public String deleteProductById(@PathParam("productId") String productId) {
        Product product = productService.getProductById(productId);
        productService.removeProduct(productId);

        return gsonBean.getGson().toJson(product);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/add")
    public String addNewProduct(String data) {

        //json product structure example
        //{
        //  "name": "PRODUCT NAME",
        //  "description": "PRODUCT DESCRIPTION",
        //  "publish": true,
        //  "productType": "COMPUTER",
        //  "categoryId": "6425a970-eacf-48b3-95b0-e66f0818ea35"
        //}

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(data);

        JsonObject rootNode = element.getAsJsonObject();
        String productName = rootNode.get("name").getAsString();
        String productDescription = rootNode.get("description").getAsString();
        boolean productPublish = rootNode.get("publish").getAsBoolean();
        ProductType productType = gsonBean.getGson().fromJson(rootNode.get("productType"), ProductType.class);
        String categoryId = rootNode.get("categoryId").getAsString();

        Product product = new Product();
        product.setName(productName);
        product.setDescription(productDescription);
        product.setPublish(productPublish);
        product.setProductType(productType);
        product.setDate(new Date());

        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);

        productService.merge(product);

        return gsonBean.getGson().toJson(product);
    }
}
