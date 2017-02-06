package com.example.facu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by facu on 06/02/17.
 */
public class Date {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("date")
    @Expose
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
