package com.breakstudio.casadeoracion;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jaime on 13/3/2017.
 */

public interface PostService {
    @GET("get_posts")
    Call<PostRespuesta> obtenerListadePosts();
    @GET("get_category_posts")
    Call<PostRespuesta> obtenerDestacados();

    @GET("get_tag_posts/?tag_slug=Predicas")
    Call<PredicaRespuesta> obtenerPredicas();

    @GET("get_tag_posts/?tag_slug=Predicas")
    Call<CalendarioRespuesta> obtenerCalenadario();

}
