package com.example.facu.nextdotstestandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facu.models.Response;
import com.example.facu.models.ResultCharacters;
import com.example.facu.models.ResultCreators;
import com.example.facu.models.ResultDetail;
import com.example.facu.models.ResultSeries;
import com.example.facu.realmModels.SavedComics;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ComicDetailActivity extends AppCompatActivity {
    Response<ResultDetail> comicDetailResponse;
    ComicEndPointInterface apiService;
    private SavedComics savedComics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        Intent intent = getIntent();

        if(intent != null) {

            if(intent.getBooleanExtra("noNetwork",false)){
                withoutConnection(intent.getStringExtra("comicId"));

                return;
            } else {

            }
        } else {

        }


        final Realm comicsRealm = Realm.getDefaultInstance();

        final String comicId = intent.getStringExtra("comicId");
        final TextView title = (TextView) findViewById(R.id.comicTitle);
        final ImageView imageView = (ImageView) findViewById(R.id.comicImage);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.imageProgressBar);
        final TextView price = (TextView) findViewById(R.id.comicPrice);
        final TextView dateView = (TextView) findViewById(R.id.comicDate);
        final TextView pageNumber = (TextView) findViewById(R.id.comicPageNumber);
        final TextView seriesList = (TextView) findViewById(R.id.comicSeriesList);
        final TextView creatorsList = (TextView) findViewById(R.id.comicCreatorsList);
        final TextView charactersList = (TextView) findViewById(R.id.comicCharactersList);
        final TextView description = (TextView) findViewById(R.id.comicDescription);
        final CheckBox favoriteChk = (CheckBox) findViewById(R.id.favoriteChk);


        savedComics = new SavedComics();

        SavedComics saveComic = comicsRealm.where(SavedComics.class).equalTo("id", comicId).findFirst();
        if (saveComic == null) {
            favoriteChk.setChecked(false);
        } else {
            favoriteChk.setChecked(true);
        }

        favoriteChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {

                    comicsRealm.beginTransaction();
                    SavedComics saveComic = comicsRealm.copyToRealm(savedComics);
                    comicsRealm.commitTransaction();

                } else {
                    SavedComics saveComic = comicsRealm.where(SavedComics.class).equalTo("id", comicId).findFirst();
                    if (saveComic == null) {
                        return;
                    }
                    comicsRealm.beginTransaction();
                    saveComic.deleteFromRealm();
                    comicsRealm.commitTransaction();
                }
            }
        });


        savedComics.setId(comicId);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ComicEndPointInterface.class);

        Call<Response<ResultDetail>> call = apiService.getComicDetail(comicId,ApplicationConstant.TS,ApplicationConstant.APIKEY,ApplicationConstant.HASH);

        call.enqueue(new Callback<Response<ResultDetail>>() {

            @Override
            public void onResponse(Call<Response<ResultDetail>> call, retrofit2.Response<Response<ResultDetail>> response) {

                comicDetailResponse = response.body();

                ResultDetail resultDetail = (ResultDetail) comicDetailResponse.getData().getResults().get(0);

                //Titulo
                title.setText(resultDetail.getTitle());
                savedComics.setTitle(resultDetail.getTitle());

                //Precio
                price.setText("Precio: $" + Double.toString(resultDetail.getPrices().get(0).getPrice()));
                savedComics.setPrice("Precio: $" + Double.toString(resultDetail.getPrices().get(0).getPrice()));

                //Date
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-SSSS");
                String dateInString = resultDetail.getDates().get(0).getDate();
                Date date = null;
                try {
                    date = formatter.parse(dateInString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                dateView.setText("Publicado: " + Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(cal.get(Calendar.MONTH)) + "/" + Integer.toString(cal.get(Calendar.YEAR)));
                savedComics.setDate("Publicado: " + Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(cal.get(Calendar.MONTH)) + "/" + Integer.toString(cal.get(Calendar.YEAR)));

                //Imagen
                String imagePath = resultDetail.getThumbnail().getPath() + "." + resultDetail.getThumbnail().getExtension();
                ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
                imageLoader.displayImage(imagePath, imageView, null, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        imageView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        imageView.setImageBitmap(loadedImage);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        loadedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        savedComics.setImage(byteArray);

                        favoriteChk.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

                // Numero de paginas
                pageNumber.setText("Paginas: " + Integer.toString(resultDetail.getPageCount()));
                savedComics.setPageNumber("Paginas: " + Integer.toString(resultDetail.getPageCount()));

                //Series, Personajes y Creadores
                String[] parts = resultDetail.getSeries().getResourceURI().split("/");
                String seriesId = parts[parts.length-1];

                Call<Response<ResultCreators>> callCreators = apiService.getCreatorsList(comicId,ApplicationConstant.TS,ApplicationConstant.APIKEY,ApplicationConstant.HASH);
                Call<Response<ResultCharacters>> callCharacters = apiService.getCharactersList(comicId,ApplicationConstant.TS,ApplicationConstant.APIKEY,ApplicationConstant.HASH);
                Call<Response<ResultSeries>> callSeries = apiService.getSeriesList(seriesId,ApplicationConstant.TS,ApplicationConstant.APIKEY,ApplicationConstant.HASH);

                loadCreators(callCreators,creatorsList);
                loadCharacters(callCharacters,charactersList);
                loadSeries(callSeries,seriesList);


                description.setText(resultDetail.getDescription());
                savedComics.setDescription(resultDetail.getDescription());
            }

            @Override
            public void onFailure(Call<Response<ResultDetail>> call, Throwable t) {
                Toast.makeText(ComicDetailActivity.this, "Ha ocurrido un error al cargar el comic seleccionado.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void withoutConnection(final String comicId) {

        final Realm comicsRealm = Realm.getDefaultInstance();


        final TextView title = (TextView) findViewById(R.id.comicTitle);
        final ImageView imageView = (ImageView) findViewById(R.id.comicImage);
        final TextView price = (TextView) findViewById(R.id.comicPrice);
        final TextView dateView = (TextView) findViewById(R.id.comicDate);
        final TextView pageNumber = (TextView) findViewById(R.id.comicPageNumber);
        final TextView seriesList = (TextView) findViewById(R.id.comicSeriesList);
        final TextView creatorsList = (TextView) findViewById(R.id.comicCreatorsList);
        final TextView charactersList = (TextView) findViewById(R.id.comicCharactersList);
        final TextView description = (TextView) findViewById(R.id.comicDescription);
        final CheckBox favoriteChk = (CheckBox) findViewById(R.id.favoriteChk);

        SavedComics saveComic = comicsRealm.where(SavedComics.class).equalTo("id", comicId).findFirst();

        title.setText(saveComic.getTitle());
        if(saveComic.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(saveComic.getImage() , 0, saveComic.getImage().length);
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
        }
        price.setText(saveComic.getPrice());
        dateView.setText(saveComic.getDate());
        pageNumber.setText(saveComic.getPageNumber());
        seriesList.setText(saveComic.getSeriesList());
        creatorsList.setText(saveComic.getCreatorsList());
        charactersList.setText(saveComic.getCharactersList());
        description.setText(saveComic.getDescription());
        favoriteChk.setChecked(true);
        favoriteChk.setVisibility(View.VISIBLE);

        favoriteChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {

                    comicsRealm.beginTransaction();
                    SavedComics saveComic = comicsRealm.copyToRealm(savedComics);
                    comicsRealm.commitTransaction();

                } else {
                    SavedComics saveComic = comicsRealm.where(SavedComics.class).equalTo("id", comicId).findFirst();
                    if (saveComic == null) {
                        return;
                    }
                    comicsRealm.beginTransaction();
                    saveComic.deleteFromRealm();
                    comicsRealm.commitTransaction();
                }
            }
        });



    }

    private void loadSeries(Call<Response<ResultSeries>> callSeries, final TextView seriesList) {
        callSeries.enqueue(new Callback<Response<ResultSeries>>() {
            @Override
            public void onResponse(Call<Response<ResultSeries>> call, retrofit2.Response<Response<ResultSeries>> response) {
                List<ResultSeries> list = response.body().getData().getResults();
                Boolean first = true;
                if(list.size() == 0) {
                    seriesList.setText("No se han encontrado series.");
                    savedComics.setSeriesList("No se han encontrado series.");
                    return;
                }
                for (ResultSeries serie:list) {
                    if(first){
                        seriesList.setText("Series: ");
                        seriesList.append(serie.getTitle());
                        first = false;
                    } else {
                        seriesList.append(", " + serie.getTitle());
                    }
                }
                savedComics.setSeriesList(seriesList.getText().toString());
            }

            @Override
            public void onFailure(Call<Response<ResultSeries>> call, Throwable t) {

            }
        });
    }

    private void loadCharacters(Call<Response<ResultCharacters>> callCharacters, final TextView charactersList) {
        callCharacters.enqueue(new Callback<Response<ResultCharacters>>() {
            @Override
            public void onResponse(Call<Response<ResultCharacters>> call, retrofit2.Response<Response<ResultCharacters>> response) {


                List<ResultCharacters> list = response.body().getData().getResults();
                Boolean first = true;
                if(list.size() == 0) {
                    charactersList.setText("No se han encontrado personajes.");
                    savedComics.setSeriesList("No se han encontrado personajes.");
                    return;
                }
                for (ResultCharacters character:list) {
                    if(first){
                        charactersList.setText("Personajes: ");
                        charactersList.append(character.getName());
                        first = false;
                    } else {
                        charactersList.append(", " + character.getName());
                    }
                }
                savedComics.setCharactersList(charactersList.getText().toString());
            }

            @Override
            public void onFailure(Call<Response<ResultCharacters>> call, Throwable t) {

            }
        });
    }


    private void loadCreators(Call<Response<ResultCreators>> callCreators, final TextView creatorsList) {
        callCreators.enqueue(new Callback<Response<ResultCreators>>() {
            @Override
            public void onResponse(Call<Response<ResultCreators>> call, retrofit2.Response<Response<ResultCreators>> response) {
                List<ResultCreators> list = response.body().getData().getResults();
                Boolean first = true;
                if(list.size() == 0) {
                    creatorsList.setText("No se han encontrado creadores.");
                    savedComics.setSeriesList("No se han encontrado creadores.");
                    return;
                }
                for (ResultCreators creator:list) {
                    if(first){
                        creatorsList.setText("Creadores: ");
                        creatorsList.append(creator.getFullName());
                        first = false;
                    } else {
                        creatorsList.append(", " + creator.getFullName());
                    }
                }
                savedComics.setCreatorsList(creatorsList.getText().toString());
            }

            @Override
            public void onFailure(Call<Response<ResultCreators>> call, Throwable t) {

            }
        });
    }

}
