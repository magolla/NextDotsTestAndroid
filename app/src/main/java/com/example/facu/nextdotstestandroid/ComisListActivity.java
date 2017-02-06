package com.example.facu.nextdotstestandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.facu.models.ComicListResponse;
import com.example.facu.models.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComisListActivity extends AppCompatActivity {


    ListView listview;


    public static final String REGISTER_NUMBER = "30";
    ComicListResponse comicListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comis_list);

        listview = (ListView) findViewById(R.id.comicList);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ComicEndPointInterface apiService = retrofit.create(ComicEndPointInterface.class);

        Call<ComicListResponse> call = apiService.getComicList(REGISTER_NUMBER,ApplicationConstant.RS,ApplicationConstant.APIKEY,ApplicationConstant.HASH);

        call.enqueue(new Callback<ComicListResponse>() {
            @Override
            public void onResponse(Call<ComicListResponse> call, Response<ComicListResponse> response) {
                comicListResponse = response.body();
                listview.setAdapter(new ComicListAdapter(ComisListActivity.this,response.body()));
            }

            @Override
            public void onFailure(Call<ComicListResponse> call, Throwable t) {
            }
        });


//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                }
//            }
//        });

//        vi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });




    }
}
