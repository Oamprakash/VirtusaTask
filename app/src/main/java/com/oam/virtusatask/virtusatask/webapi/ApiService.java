package com.oam.virtusatask.virtusatask.webapi;

import com.oam.virtusatask.virtusatask.model.Album;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {
    // Get album list
    @GET("albums")
    Single<List<Album>> getAlbumList();

}
