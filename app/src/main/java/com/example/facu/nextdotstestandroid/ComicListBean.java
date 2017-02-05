package com.example.facu.nextdotstestandroid;

/**
 * Created by facu on 03/02/17.
 */

public class ComicListBean {


    private String id;
    private String nombre;
    private int precio;
    private String imagePath;
    private String imageExtension;


    public ComicListBean(String id, String nombre, int precio, String imagePath, String imageExtension) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagePath = imagePath;
        this.imageExtension = imageExtension;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }
}
