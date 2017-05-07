package com.breakstudio.casadeoracion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcelo on 06/05/2017.
 */
public class Category {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("cat_ID")
    @Expose
    private Integer catID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getCatID() {
        return catID;
    }

    public void setCatID(Integer catID) {
        this.catID = catID;
    }

}