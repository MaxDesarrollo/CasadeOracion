package com.breakstudio.casadeoracion;

/**
 * Created by Marcelo on 26/04/2017.
 */
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PostServiceArray {

    @GET("/wp/v2/posts")
    Call<List<Post>> getPost();
}

//http://hashtag.breakstudio.co/wp-json/wp/v2/posts?app=true