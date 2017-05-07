package com.breakstudio.casadeoracion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcelo on 06/05/2017.
 */

public class Comment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("approved")
    @Expose
    private Integer approved;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }
}