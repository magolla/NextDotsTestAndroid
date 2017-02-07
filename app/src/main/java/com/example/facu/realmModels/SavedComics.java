package com.example.facu.realmModels;

import io.realm.RealmObject;

/**
 * Created by facu on 06/02/17.
 */

public class SavedComics extends RealmObject {

    private String id;
    private String title;
    private String description;
    private byte[] image;
    private String price;
    private String date;
    private String pageNumber;
    private String seriesList;
    private String charactersList;
    private String creatorsList;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(String seriesList) {
        this.seriesList = seriesList;
    }

    public String getCharactersList() {
        return charactersList;
    }

    public void setCharactersList(String charactersList) {
        this.charactersList = charactersList;
    }

    public String getCreatorsList() {
        return creatorsList;
    }

    public void setCreatorsList(String creatorsList) {
        this.creatorsList = creatorsList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
