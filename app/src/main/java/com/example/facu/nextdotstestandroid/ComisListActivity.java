package com.example.facu.nextdotstestandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.facu.models.Response;
import com.example.facu.models.Result;

import java.util.Collections;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComisListActivity extends AppCompatActivity {


    ListView listview;


    public static final String REGISTER_NUMBER = "30";
    Response<Result> response = null;

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

        Call<Response<Result>> call = apiService.getComicList(REGISTER_NUMBER,ApplicationConstant.RS,ApplicationConstant.APIKEY,ApplicationConstant.HASH);

        call.enqueue(new Callback<Response<Result>>() {


            @Override
            public void onResponse(Call<Response<Result>> call, final retrofit2.Response<Response<Result>> response) {
                ComisListActivity.this.response = response.body();
                listview.setAdapter(new ComicListAdapter(ComisListActivity.this,ComisListActivity.this.response));


            }

            @Override
            public void onFailure(Call<Response<Result>> call, Throwable t) {
                Toast.makeText(ComisListActivity.this, "Fallo al cargar la lista", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton btnRandom = (ImageButton) findViewById(R.id.shuffleButton);

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(response == null) {
                    Toast.makeText(ComisListActivity.this, "Todavia esta cargando", Toast.LENGTH_SHORT).show();
                } else {
                    long seed = System.nanoTime();
                    Collections.shuffle(response.getData().getResults(),new Random(seed));
                    listview.setAdapter(new ComicListAdapter(ComisListActivity.this,response));

                }

            }
        });


    }
}
