package com.example.facu.nextdotstestandroid;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.facu.models.Response;
import com.example.facu.models.Result;
import com.example.facu.realmModels.SavedComics;

import java.util.Collections;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
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

        Realm.init(getApplicationContext());

        final Realm comicsRealm = Realm.getDefaultInstance();

        RealmResults<SavedComics> realmResults = comicsRealm.where(SavedComics.class).findAll();
        listview = (ListView) findViewById(R.id.comicList);

        if(isNetworkAvailable()) {
            listview.setVisibility(View.VISIBLE);
            internetAvaible();
        } else {
            internetNotAvaible(realmResults);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        if(!isNetworkAvailable()) {
            final Realm comicsRealm = Realm.getDefaultInstance();
            RealmResults<SavedComics> realmResults = comicsRealm.where(SavedComics.class).findAll();
            internetNotAvaible(realmResults);
        }
    }

    private void internetNotAvaible(RealmResults<SavedComics> realmResults) {

        ImageButton btnRandom = (ImageButton) findViewById(R.id.shuffleButton);
        ImageButton btnProfile = (ImageButton) findViewById(R.id.profileButton);
        ImageButton btnFilter = (ImageButton) findViewById(R.id.filterButton);

        btnRandom.setVisibility(View.GONE);
        btnFilter.setVisibility(View.GONE);
        btnProfile.setVisibility(View.GONE);

        if(realmResults == null || realmResults.size() == 0) {
            Toast.makeText(this, "No posees comics guardados en favoritos.", Toast.LENGTH_SHORT).show();
            listview.setVisibility(View.GONE);
            return;
        } else {
            listview.setVisibility(View.VISIBLE);
        }


        listview = (ListView) findViewById(R.id.comicList);

        listview.setAdapter(new ComicListAdapterNotNewtwork(ComisListActivity.this,realmResults));

    }

    private void internetAvaible() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ComicEndPointInterface apiService = retrofit.create(ComicEndPointInterface.class);

        Call<Response<Result>> call = apiService.getComicList(REGISTER_NUMBER,ApplicationConstant.TS,ApplicationConstant.APIKEY,ApplicationConstant.HASH);

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
        ImageButton btnProfile = (ImageButton) findViewById(R.id.profileButton);
        ImageButton btnFilter = (ImageButton) findViewById(R.id.filterButton);

        btnRandom.setVisibility(View.VISIBLE);
        btnFilter.setVisibility(View.VISIBLE);
        btnProfile.setVisibility(View.VISIBLE);

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

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComisListActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        // custom dialog
        final Dialog dialog = new Dialog(ComisListActivity.this);
        dialog.setContentView(R.layout.filter_dialog);
        dialog.setTitle("Aplicar Filtros");


        Button applyFilter = (Button) dialog.findViewById(R.id.applyFilter);
        Button cancelFilter = (Button) dialog.findViewById(R.id.cancelFilter);







        cancelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = null;
                String titleBegin = null;
                String year = null;


                final EditText completeTitle = (EditText) dialog.findViewById(R.id.completeTitleEdit);

                if(!(completeTitle.getText().toString().equals("") || completeTitle.getText().toString().equals(null))) {
                    title = completeTitle.getText().toString();
                }

                final EditText launchYear = (EditText) dialog.findViewById(R.id.launchYearEdit);

                if(!(launchYear.getText().toString().equals("") && !launchYear.getText().toString().equals(null))) {
                    year = launchYear.getText().toString();
                }

                final EditText titleBeginWith = (EditText) dialog.findViewById(R.id.titleBeginWithEdit);

                if(!(titleBeginWith.getText().toString().equals("") && !titleBeginWith.getText().toString().equals(null))) {
                    titleBegin = titleBeginWith.getText().toString();
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApplicationConstant.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ComicEndPointInterface apiService = retrofit.create(ComicEndPointInterface.class);


                Call<Response<Result>> call = apiService.getComicFilter(year,titleBegin,title,ApplicationConstant.TS,ApplicationConstant.APIKEY,ApplicationConstant.HASH);

                call.enqueue(new Callback<Response<Result>>() {


                    @Override
                    public void onResponse(Call<Response<Result>> call, final retrofit2.Response<Response<Result>> response) {
                        ComisListActivity.this.response = response.body();
                        if(response.body() == null) {
                            Toast.makeText(ComisListActivity.this, "No se encontro ningun comic con los criterios ingresados", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        listview.setAdapter(new ComicListAdapter(ComisListActivity.this,ComisListActivity.this.response));

                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Response<Result>> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(ComisListActivity.this, "Fallo al cargar la lista", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
