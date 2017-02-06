package com.example.facu.nextdotstestandroid;

import com.example.facu.models.ComicListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by facu on 04/02/17.
 */

public interface ComicEndPointInterface {


    // Request method and URL specified in the annotation

    // Callback for the parsed response is the last parameter



//    @GET("users/{username}")
//
//    Call<User> getUser(@Path("username") String username);

//    @GET("group/{id}/users")
//
//    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);


//
//    @POST("https://gateway.marvel.com/v1/public/comics?apikey=2359abf629c50fbfd70cf59e778dabed")
//
//    Call<User> createUser(@Body User user);


    @GET("v1/public/comics")
    Call<ComicListResponse> getComicList(@Query("limit") String limit,@Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);

    @GET("v1/public/comics/{comicId}")
    Call<ComicListResponse> getComicDetail(@Path("comicId") String comicId, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);



}
