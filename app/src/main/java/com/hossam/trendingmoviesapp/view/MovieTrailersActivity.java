package com.hossam.trendingmoviesapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hossam.trendingmoviesapp.MovieController;
import com.hossam.trendingmoviesapp.R;
import com.hossam.trendingmoviesapp.adapter.TrailersAdapter;
import com.hossam.trendingmoviesapp.model.MovieList;
import com.hossam.trendingmoviesapp.model.MovieModel;
import com.hossam.trendingmoviesapp.model.MovieTrailerListModel;
import com.hossam.trendingmoviesapp.model.MovieTrailerModel;
import com.hossam.trendingmoviesapp.model.MovieTrailersResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieTrailersActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    MovieModel movieModel;
    MovieList movieList;
    MovieTrailerListModel movieTrailerListModel;
    List<MovieTrailerModel> movieTrailerModels ;
    MovieTrailersResponseModel movieTrailersResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailers);
        movieTrailerModels = new ArrayList<>();
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MovieTrailersActivity.this);
        recycler_view.setLayoutManager(linearLayoutManager);

        movieTrailerModels = new ArrayList<>();
        if (savedInstanceState == null) {
            if (getIntent().hasExtra(getString(R.string.MovieModel_intent_Extra))) {
                movieModel = getIntent().getParcelableExtra(getString(R.string.MovieModel_intent_Extra));
//            movieReviewModels = movieReviewResponseModel.getResults();

                getMovieTrailers(String.valueOf(movieModel.getId()));
            }
        } else {
            movieTrailerListModel = savedInstanceState.getParcelable(getString(R.string.movie_trailer_models_extra));
            if (movieTrailerListModel != null) {
                movieTrailerModels = movieTrailerListModel.getMovieTrailerModels();
            }
            TrailersAdapter trailersAdapter = new TrailersAdapter(movieTrailerModels, MovieTrailersActivity.this);
            recycler_view.setAdapter(trailersAdapter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        movieTrailerListModel = new MovieTrailerListModel();
        movieTrailerListModel.setMovieTrailerModels(movieTrailerModels);
        outState.putParcelable(getString(R.string.movie_trailer_models_extra), movieTrailerListModel);

    }

    private void getMovieTrailers(String movieId) {
        MovieController.getMovieVideos(movieId, new Callback<MovieTrailersResponseModel>() {
            @Override
            public void onResponse(Call<MovieTrailersResponseModel> call, Response<MovieTrailersResponseModel> response) {
                if (response.code() >= 200 && response.code() < 300)
                    if (response.isSuccessful()) {
                        movieTrailersResponseModel = response.body();
                        movieTrailerModels = movieTrailersResponseModel.getResults();
                        TrailersAdapter trailersAdapter = new TrailersAdapter(movieTrailerModels, MovieTrailersActivity.this);
                        recycler_view.setAdapter(trailersAdapter);
                    }else
                        Toast.makeText(MovieTrailersActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<MovieTrailersResponseModel> call, Throwable t) {
                Toast.makeText(MovieTrailersActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
