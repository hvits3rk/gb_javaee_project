package com.romantupikov.simpleapp.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.romantupikov.simpleapp.bean.GsonBean;
import com.romantupikov.simpleapp.entity.Category;
import com.romantupikov.simpleapp.service.CategoryService;

import javax.ejb.EJB;
import javax.ws.rs.*;

@Path("category")
@Produces("application/json")
public class CategoryController {

    @EJB
    private CategoryService categoryService;

    @GET
    @Path("/all")
    public String getAllCategories() {
        return GsonBean.getGson().toJson(categoryService.getCategoryList());
    }

    @POST
    @Consumes("application/json")
    @Path("/add")
    public String addNewCategory(String data) {

        //json category structure example
        //{
        //  "name": "CATEGORY NAME",
        //  "description": "CATEGORY DESCRIPTION"
        //}

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(data);

        JsonObject rootNode = element.getAsJsonObject();
        String categoryName = rootNode.get("name").getAsString();
        String categoryDescription = rootNode.get("description").getAsString();

        Category category = new Category();
        category.setName(categoryName);
        category.setDescription(categoryDescription);

        categoryService.persist(category);

        return GsonBean.getGson().toJson(category);
    }
}
