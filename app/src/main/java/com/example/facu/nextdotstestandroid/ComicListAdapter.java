package com.example.facu.nextdotstestandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.facu.models.ComicListResponse;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by facu on 03/02/17.
 */

public class ComicListAdapter extends BaseAdapter {

    Context context;
    ComicListResponse comicListResponse;
    private static LayoutInflater inflater = null;

    public ComicListAdapter(Context context, ComicListResponse comicListResponse) {
        this.context = context;
        this.comicListResponse = comicListResponse;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return comicListResponse.getData().getResults().size();
    }

    @Override
    public Object getItem(int position) {
        return comicListResponse.getData().getResults().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.comic_list_cell, null);
            TextView comicPrice = (TextView) vi.findViewById(R.id.comicPrice);
            TextView comicName = (TextView) vi.findViewById(R.id.comicName);
            ImageView comicImageUrl = (ImageView) vi.findViewById(R.id.comicImage);

            String imagePath = comicListResponse.getData().getResults().get(position).getThumbnail().getPath() + "." + comicListResponse.getData().getResults().get(position).getThumbnail().getExtension();
            comicPrice.setText(Double.toString(comicListResponse.getData().getResults().get(position).getPrices().get(0).getPrice()));
            comicName.setText(comicListResponse.getData().getResults().get(position).getTitle());
            ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
            imageLoader.displayImage(imagePath, comicImageUrl);
        }
        return vi;
    }
}
