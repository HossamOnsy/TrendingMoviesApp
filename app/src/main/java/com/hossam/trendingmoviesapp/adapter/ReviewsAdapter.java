package com.hossam.trendingmoviesapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hossam.trendingmoviesapp.R;
import com.hossam.trendingmoviesapp.model.MovieReviewModel;

import java.util.List;

/**
 * Created by hossam on 30/09/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    List<MovieReviewModel> movieReviewModelList;
    Activity activity;

    public ReviewsAdapter(List<MovieReviewModel> movieReviewModelList, Activity activity) {
        this.movieReviewModelList = movieReviewModelList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.review_list_item, parent, false);

        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieReviewModel movieReviewModel = movieReviewModelList.get(position);

        holder.author_tv.setText(movieReviewModel.getAuthor());
        holder.content_tv.setText(movieReviewModel.getContent().trim());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
//                Fragment fragment = new MovieDetailsFragment();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("movieModel", movieModel);
//                fragment.setArguments(bundle);
//                fragmentTransaction.add(R.id.fragment_container, fragment, "MOVIE_DETAIL_FRAGMENT");
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

//            }
//        });
//        String picassoURL = "https://image.tmdb.org/t/p/w185" + movieModel.getPosterPath();
//        //TODO URL of That Movie
//        Picasso.with(activity).load(picassoURL).placeholder(R.drawable.loading)
//                .error(R.drawable.error).fit().into(holder.movie_iv);

    }

    @Override
    public int getItemCount() {
        return movieReviewModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView author_tv,content_tv;
        public ViewHolder(View itemView) {
            super(itemView);

            author_tv = itemView.findViewById(R.id.author_tv);
            content_tv = itemView.findViewById(R.id.content_tv);
        }
    }
}
