package com.hossam.trendingmoviesapp.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hossam.trendingmoviesapp.R;
import com.hossam.trendingmoviesapp.model.MovieTrailerModel;

import java.util.List;

/**
 * Created by hossam on 30/09/17.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
    List<MovieTrailerModel> movieTrailerModelList;
    Activity activity;

    public TrailersAdapter(List<MovieTrailerModel> movieTrailerModelList, Activity activity) {
        this.movieTrailerModelList = movieTrailerModelList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.trailer_list_item, parent, false);

        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieTrailerModel movieTrailerModel = movieTrailerModelList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchYoutubeVideo(activity, movieTrailerModel.getKey());
            }
        });

        holder.desc_tv.setText(movieTrailerModel.getName());

    }

    @Override
    public int getItemCount() {
        return movieTrailerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            desc_tv = itemView.findViewById(R.id.desc_tv);
        }
    }

    public static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
