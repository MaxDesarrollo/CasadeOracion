package com.breakstudio.casadeoracion;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jaime on 10/3/2017.
 */

public class Post implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("featured_image_url")
    private String thumbnail;//url
    @SerializedName("comment_count")
    private Integer cant_comentarios;
    @SerializedName("date")
    private String fecha;
    @SerializedName("link")
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getCant_comentarios() {return cant_comentarios;}

    public void setCant_comentarios(Integer cant_comentarios) { this.cant_comentarios = cant_comentarios;}


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getFecha() {return fecha;}

    public void setFecha(String fecha) {this.fecha = fecha;}
}
