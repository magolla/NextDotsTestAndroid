package com.example.facu.nextdotstestandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.facu.models.Response;
import com.example.facu.models.Result;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by facu on 03/02/17.
 */

public class ComicListAdapter extends BaseAdapter {

    Context context;
    Response<Result> response;
    private static LayoutInflater inflater = null;

    public ComicListAdapter(Context context, Response<Result> response) {
        this.context = context;
        this.response = response;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return response.getData().getResults().size();
    }

    @Override
    public Object getItem(int position) {
        return response.getData().getResults().get(position);
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
            Result result = (Result) response.getData().getResults().get(position);
            String imagePath = result.getThumbnail().getPath() + "." + result.getThumbnail().getExtension();
            if(result.getPrices().get(0).getPrice() == 0) {
                comicPrice.setText("Agotado.");
            } else {
                comicPrice.setText(Double.toString(result.getPrices().get(0).getPrice()));
            }

            comicName.setText(result.getTitle());
            ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
//            imageLoader.displayImage(imagePath, comicImageUrl);
            final View finalVi = vi;
            imageLoader.displayImage(imagePath, comicImageUrl, null, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                    ProgressBar progressBar = (ProgressBar) finalVi.findViewById(R.id.imageProgressBar);
                    ImageView imageView = (ImageView) view.findViewById(R.id.comicImage);
                    imageView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    ProgressBar progressBar = (ProgressBar) finalVi.findViewById(R.id.imageProgressBar);
                    ImageView imageView = (ImageView) view.findViewById(R.id.comicImage);
                    imageView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    imageView.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });

            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Result result = (Result) getItem(position);
                    Intent intent = new Intent(context, ComicDetailActivity.class);
                    intent.putExtra("comicId",result.getId().toString());
                    context.startActivity(intent);
                }
            });
        }


        return vi;
    }
}
