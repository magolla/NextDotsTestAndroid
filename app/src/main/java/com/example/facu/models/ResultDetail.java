package com.example.facu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by facu on 06/02/17.
 */

public class ResultDetail {
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
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("pageCount")
    @Expose
    private Integer pageCount;

    @SerializedName("dates")
    @Expose
    private List<Date> dates = null;

    @SerializedName("series")
    @Expose
    private Series series;

    @SerializedName("creators")
    @Expose
    private Creators creators;

    @SerializedName("characters")
    @Expose
    private Characters characters;

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


    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Creators getCreators() {
        return creators;
    }

    public void setCreators(Creators creators) {
        this.creators = creators;
    }

    public Characters getCharacters() {
        return characters;
    }

    public void setCharacters(Characters characters) {
        this.characters = characters;
    }
}
