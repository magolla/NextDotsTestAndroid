package com.example.facu.models;

/**
 * Created by facu on 04/02/17.
 */

import android.media.Image;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("prices")
    @Expose
    private List<Price> prices = null;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;

    //Agregados para el detalle



//    @SerializedName("dates")
//    @Expose
//    private List<Date> dates = null;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

}