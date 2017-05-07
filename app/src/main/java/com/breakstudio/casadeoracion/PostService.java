package com.breakstudio.casadeoracion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Jaime on 13/3/2017.
 */

public interface PostService {
    @GET("wp/v2/posts?app=true")
    Call<List<Post>> getPost();

    @GET("wp/v2/posts/{post_id}?app=true")
    Call<Contenido> getContenido(@Path("post_id") String post_id);

    @GET("wp/v2/posts/{post_id}?app=true")
    Call<Comment> getComentario(@Path("post_id") String post_id);


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
