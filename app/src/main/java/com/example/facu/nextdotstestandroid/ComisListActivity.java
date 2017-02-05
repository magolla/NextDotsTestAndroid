package com.example.facu.nextdotstestandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.facu.models.ComicListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComisListActivity extends AppCompatActivity {


    ListView listview;

    public static final String BASE_URL = "https://gateway.marvel.com/";
    public static final String RS = "1";
    public static final String APIKEY = "172ee7e3d542f2bcb772955e1f271c53";
    public static final String HASH = "4e1dea59256a0f4247c463d54918d027";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comis_list);

        listview = (ListView) findViewById(R.id.comicList);


//        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ComicEndPointInterface apiService = retrofit.create(ComicEndPointInterface.class);

        Call<ComicListResponse> call = apiService.getComicList(RS,APIKEY,HASH);

        call.enqueue(new Callback<ComicListResponse>() {
            @Override
            public void onResponse(Call<ComicListResponse> call, Response<ComicListResponse> response) {
                Log.d("Quiero llorar","MUCHO");
                listview.setAdapter(new ComicListAdapter(ComisListActivity.this,response.body()));
            }

            @Override
            public void onFailure(Call<ComicListResponse> call, Throwable t) {
                Log.d("LLORO POSTA","");
            }
        });








    }
}
