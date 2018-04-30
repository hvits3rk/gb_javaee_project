package com.romantupikov.simpleapp.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GsonBean {

    private Gson gson;

    public GsonBean() {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
    }

    public Gson getGson() {
        return this.gson;
    }
}
