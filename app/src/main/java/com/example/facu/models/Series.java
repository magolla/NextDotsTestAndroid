package com.example.facu.models;

/**
 * Created by facu on 06/02/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Series {

    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("name")
    @Expose
    private String name;

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}