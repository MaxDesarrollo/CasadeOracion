package com.breakstudio.casadeoracion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by Jaime on 13/3/2017.
 */

public interface PostService {
    @GET("wp/v2/posts?app=true")
    Call<List<Post>> getPost();
    @GET("get_posts")
    Call<PostRespuesta> obtenerListadePosts();
    @GET("get_category_posts")
    Call<PostRespuesta> obtenerDestacados();

    @GET("get_tag_posts/?tag_slug=Predicas")
    Call<PredicaRespuesta> obtenerPredicas();

    @GET("get_tag_posts/?tag_slug=Predicas")
    Call<CalendarioRespuesta> obtenerCalendario();

    @GET("get_posts")
    Call<ImagenesRespuesta> obtenerImagenes();

}
