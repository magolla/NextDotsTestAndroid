package com.example.facu.nextdotstestandroid;

import com.example.facu.models.Response;
import com.example.facu.models.Result;
import com.example.facu.models.ResultCharacters;
import com.example.facu.models.ResultCreators;
import com.example.facu.models.ResultDetail;
import com.example.facu.models.ResultSeries;

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
    Call<Response<Result>> getComicList(@Query("limit") String limit, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);

    @GET("v1/public/comics")
    Call<Response<Result>> getComicFilter(@Query("startYear") String startYear,@Query("titleStartsWith") String titleStartsWith,@Query("title") String title, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);

    @GET("v1/public/comics/{comicId}")
    Call<Response<ResultDetail>> getComicDetail(@Path("comicId") String comicId, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);

    @GET("v1/public/series/{seriesId}")
    Call<Response<ResultSeries>> getSeriesList(@Path("seriesId") String seriesId, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);

    @GET("v1/public/comics/{comicId}/creators")
    Call<Response<ResultCreators>> getCreatorsList(@Path("comicId") String comicId, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);

    @GET("v1/public/comics/{comicId}/characters")
    Call<Response<ResultCharacters>> getCharactersList(@Path("comicId") String comicId, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);




}
