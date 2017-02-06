package com.example.facu.nextdotstestandroid;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.facu.models.ComicListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicDetailActivity extends AppCompatActivity {
    ComicListResponse comicListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        Intent intent = getIntent();
        String comicId = intent.getStringExtra("comicId");



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ComicEndPointInterface apiService = retrofit.create(ComicEndPointInterface.class);

        Call<ComicListResponse> call = apiService.getComicDetail(comicId,ApplicationConstant.RS,ApplicationConstant.APIKEY,ApplicationConstant.HASH);

        call.enqueue(new Callback<ComicListResponse>() {
            @Override
            public void onResponse(Call<ComicListResponse> call, Response<ComicListResponse> response) {
                comicListResponse = response.body();

                Toast.makeText(ComicDetailActivity.this, comicListResponse.getData().getResults().get(0).getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ComicListResponse> call, Throwable t) {
                Toast.makeText(ComicDetailActivity.this, "Se sarpan :(", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
