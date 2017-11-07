package com.hossam.trendingmoviesapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hossam.trendingmoviesapp.MovieController;
import com.hossam.trendingmoviesapp.R;
import com.hossam.trendingmoviesapp.adapter.ReviewsAdapter;
import com.hossam.trendingmoviesapp.model.MovieModel;
import com.hossam.trendingmoviesapp.model.MovieReviewListModel;
import com.hossam.trendingmoviesapp.model.MovieReviewModel;
import com.hossam.trendingmoviesapp.model.MovieReviewResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieReviewsActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    List<MovieReviewModel> movieReviewModels;
    MovieReviewResponseModel movieReviewResponseModel;
    MovieModel movieModel;
    MovieReviewListModel movieReviewListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reviews);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MovieReviewsActivity.this);
        recycler_view.setLayoutManager(linearLayoutManager);
        movieReviewModels = new ArrayList<>();
        if (savedInstanceState == null) {
            if (getIntent().hasExtra(getString(R.string.MovieModel_intent_Extra))) {
                movieModel = getIntent().getParcelableExtra(getString(R.string.MovieModel_intent_Extra));
//                Log.v("MovieModel", "MovieModel ------------>  " + movieModel.getId().toString());

//            movieReviewModels = movieReviewResponseModel.getResults();
                getMovieReviews(String.valueOf(movieModel.getId()));
            }
        } else {
//            movieReviewModels = savedInstanceState.getParcelable("movieTrailerModels");
            movieReviewListModel = savedInstanceState.getParcelable(getString(R.string.movie_review_list_extra));
            if (movieReviewListModel != null) {
                movieReviewModels = movieReviewListModel.getMovieReviewModels();
            }

            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(movieReviewModels, MovieReviewsActivity.this);
            recycler_view.setAdapter(reviewsAdapter);

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        movieReviewListModel = new MovieReviewListModel();
        movieReviewListModel.setMovieReviewModels(movieReviewModels);
        outState.putParcelable(getString(R.string.movie_review_list_extra), movieReviewListModel);

    }

    private void getMovieReviews(String movieId) {
        MovieController.getMovieReviews(movieId, new Callback<MovieReviewResponseModel>() {
            @Override
            public void onResponse(Call<MovieReviewResponseModel> call, Response<MovieReviewResponseModel> response) {
                if (response.code() >= 200 && response.code() < 300)
                    if (response.isSuccessful()) {
                        movieReviewResponseModel = response.body();
                        movieReviewModels = movieReviewResponseModel.getResults();
//                        for (MovieReviewModel movieReviewModel : movieReviewResponseModel.getResults())
//                            Log.v("MovieModel", "MovieModel ------------>  " + movieReviewModel.getId());
                        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(movieReviewModels, MovieReviewsActivity.this);
                        recycler_view.setAdapter(reviewsAdapter);
                    }else
                        Toast.makeText(MovieReviewsActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<MovieReviewResponseModel> call, Throwable t) {
                Toast.makeText(MovieReviewsActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
