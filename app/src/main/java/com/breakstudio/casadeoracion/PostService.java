package com.breakstudio.casadeoracion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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



    @GET("wp/v2/posts")
    Call<List<Post>> getAllPost( @Query("app") String app, @Query("page") int page);

    @GET("wp/v2/posts?app=true&per_page=100")
    Call<List<Post>> obtenerListadePosts();
    @GET("get_category_posts")
    Call<PostRespuesta> obtenerDestacados();

    @GET("wp/v2/posts?app=true")
    Call<List<Post>> obtenerPredicas();

    @GET("wp/v2/posts?app=true")
    Call<List<Post>> obtenerCalendario();

    @GET("get_posts")
    Call<ImagenesRespuesta> obtenerImagenes();

}
