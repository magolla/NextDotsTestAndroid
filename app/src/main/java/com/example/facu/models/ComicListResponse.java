package com.example.facu.models;

/**
 * Created by facu on 04/02/17.
 */

import com.example.facu.models.Data;
import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class ComicListResponse {


    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}