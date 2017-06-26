package com.breakstudio.casadeoracion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jaime on 26/6/2017.
 */

public interface AlbumArtService {
    @GET("search")
    Call<AlbumArtRespuesta> getAlbumArt(@Query("term") String term);
}
