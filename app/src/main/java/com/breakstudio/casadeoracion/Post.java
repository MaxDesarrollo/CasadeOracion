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


}
