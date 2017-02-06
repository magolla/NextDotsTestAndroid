package com.example.facu.models;

/**
 * Created by facu on 04/02/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultCreators {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("fullName")
    @Expose
    private String fullName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}