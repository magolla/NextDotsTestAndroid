package com.example.facu.nextdotstestandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.facu.models.Response;
import com.example.facu.models.Result;
import com.example.facu.realmModels.SavedComics;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import io.realm.RealmResults;

/**
 * Created by facu on 03/02/17.
 */

public class ComicListAdapterNotNewtwork extends BaseAdapter {

    Context context;
    RealmResults<SavedComics> results;
    private static LayoutInflater inflater = null;

    public ComicListAdapterNotNewtwork(Context context, RealmResults<SavedComics> results) {
        this.context = context;
        this.results = results;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.comic_list_cell, null);
            TextView comicPrice = (TextView) vi.findViewById(R.id.comicPrice);
            TextView comicName = (TextView) vi.findViewById(R.id.comicName);
            ImageView comicImageUrl = (ImageView) vi.findViewById(R.id.comicImage);
            final SavedComics savedComics = results.get(position);
//            Result result = (Result) response.getData().getResults().get(position);
//            String imagePath = result.getThumbnail().getPath() + "." + result.getThumbnail().getExtension();
            if(savedComics.getPrice() == "0") {
                comicPrice.setText("Agotado.");
            } else {
                comicPrice.setText(savedComics.getPrice());
            }

            comicName.setText(savedComics.getTitle());

            if(savedComics.getImage() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(savedComics.getImage() , 0, savedComics.getImage().length);
                comicImageUrl.setImageBitmap(bitmap);
            }


            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ComicDetailActivity.class);
                    intent.putExtra("comicId",savedComics.getId());
                    intent.putExtra("noNetwork",true);
                    context.startActivity(intent);
                }
            });
        }


        return vi;
    }
}
