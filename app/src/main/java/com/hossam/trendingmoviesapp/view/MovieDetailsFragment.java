package com.hossam.trendingmoviesapp.view;


import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hossam.trendingmoviesapp.R;
import com.hossam.trendingmoviesapp.model.MovieModel;
import com.hossam.trendingmoviesapp.utils.MySQLiteHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.COLUMNS;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.CONTENT_URI;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_ADULT;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_BACKDROP_PATH;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_ID;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_ORIGINAL_LANGUAGE;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_ORIGINAL_TITLE;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_OVERVIEW;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_POPULARITY;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_POSTER_PATH;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_RELEASE_DATE;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_TITLE;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_VIDEO;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_VOTE_AVERAGE;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_VOTE_COUNT;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.TABLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {
    @BindView(R.id.movie_date_tv)
    TextView movieDateTv;
    @BindView(R.id.movie_overview_tv)
    TextView movieOverviewTv;
    @BindView(R.id.movie_title_tv)
    TextView movieTitleTv;
    @BindView(R.id.movie_rating)
    TextView movieRatingTv;
    @BindView(R.id.movie_thumbnail_iv)
    ImageView movieThumbnailIv;
    @BindView(R.id.btn_trailers)
    Button btn_trailers;
    @BindView(R.id.btn_reviews)
    Button btn_reviews;
    @BindView(R.id.favorite_btn)
    ImageView favorite_btn;

    MySQLiteHelper db;
    Unbinder unbinder;
    MovieModel movieModel = null;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    MainActivity mainActivity;

    @OnClick({R.id.btn_reviews, R.id.btn_trailers, R.id.favorite_btn})
    void clicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reviews: {
//                Log.v("MovieModel", "MovieModel ------------>  " + movieModel.getId().toString());

                startActivity(new Intent(getActivity(), MovieReviewsActivity.class).putExtra("MovieModel", movieModel));
                break;
            }
            case R.id.btn_trailers: {
                startActivity(new Intent(getActivity(), MovieTrailersActivity.class).putExtra("MovieModel", movieModel));
                break;
            }
            case R.id.favorite_btn: {
                {
                    Cursor findEntry = db.getReadableDatabase().query(TABLE, COLUMNS, "id=?", new String[]{String.valueOf(movieModel.getId())}, null, null, null);

                    if (findEntry != null && findEntry.moveToFirst()) {
//                        db.deleteFavoriteMovie(movieModel.getId());
//                        Toast.makeText(getActivity(), "This Movie is deleted from your favorite list", Toast.LENGTH_SHORT).show();
//                        Log.v("findEntryfindEntry", "findEntry ------------>   " + findEntry.getCount());

                        int number_Of_Deleted_Rows = getActivity().getContentResolver().delete(CONTENT_URI, "id=?", new String[]{String.valueOf(movieModel.getId())});
                        if (number_Of_Deleted_Rows > 0) {
                            Toast.makeText(getActivity(), getString(R.string.toast_delete_success_msg), Toast.LENGTH_SHORT).show();
                        }
                        findEntry.close();
                        favorite_btn.setColorFilter(getResources().getColor(R.color.white));
                    } else {

                        ContentValues values = new ContentValues();
                        values.put(KEY_VOTE_COUNT, movieModel.getVoteCount()); // 0
                        values.put(KEY_ID, movieModel.getId()); // 1
                        values.put(KEY_VIDEO, movieModel.getVideo()); // 2
                        values.put(KEY_VOTE_AVERAGE, movieModel.getVoteAverage()); // 3
                        values.put(KEY_TITLE, movieModel.getTitle()); // 4
                        values.put(KEY_POPULARITY, movieModel.getPopularity()); // 5
                        values.put(KEY_POSTER_PATH, movieModel.getPosterPath()); // 6
                        values.put(KEY_ORIGINAL_LANGUAGE, movieModel.getOriginalLanguage()); // 7
                        values.put(KEY_ORIGINAL_TITLE, movieModel.getOriginalTitle()); // 8
                        values.put(KEY_BACKDROP_PATH, movieModel.getBackdropPath()); // 9
                        values.put(KEY_ADULT, movieModel.getAdult()); // 10
                        values.put(KEY_OVERVIEW, movieModel.getOverview()); // 11
                        values.put(KEY_RELEASE_DATE, movieModel.getReleaseDate()); // 12

                        Uri uri = getActivity().getContentResolver().insert(CONTENT_URI, values);

                        if (uri != null) {
                            Toast.makeText(getActivity(), getString(R.string.toast_insert_succ_mssg), Toast.LENGTH_SHORT).show();
                            favorite_btn.setColorFilter(getResources().getColor(R.color.gold));

//                            Log.v("findEntryfindEntry", "findEntry ------------>   " + "");

                        }
//                        db.addFavoriteMovie(movieModel, "FavoriteMovies");
//                        Log.v("findEntryfindEntry", "findEntry ------------>   " + findEntry.getCount());

                    }

                }
                break;
            }

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.MovieModel_intent_Extra), movieModel);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        db = new MySQLiteHelper(getActivity());


        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Cursor findEntry = db.getReadableDatabase().query(TABLE, COLUMNS, "id=?", new String[]{String.valueOf(movieModel.getId())}, null, null, null);

        if (findEntry != null && findEntry.moveToFirst()) {
            favorite_btn.setColorFilter(getResources().getColor(R.color.gold));
            findEntry.close();
        } else {
            favorite_btn.setColorFilter(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof MainActivity)
            mainActivity = (MainActivity) getActivity();

        if (savedInstanceState != null)
            movieModel = savedInstanceState.getParcelable(getString(R.string.MovieModel_intent_Extra));
        else
            movieModel = getArguments().getParcelable(getString(R.string.MovieModel_intent_Extra));


        String picassoURL = "https://image.tmdb.org/t/p/w500" + movieModel.getBackdropPath();
        movieDateTv.setText(movieModel.getReleaseDate());
        movieOverviewTv.setText(movieModel.getOverview());
        movieTitleTv.setText(movieModel.getOriginalTitle());
        try {
            movieRatingTv.setText(movieModel.getVoteAverage().toString());
        } catch (Exception e) {
            movieRatingTv.setText("");
        }

        Picasso.with(getActivity()).load(picassoURL).placeholder(R.drawable.loading)
                .error(R.drawable.error).fit().into(movieThumbnailIv);

//        getMovieTrailers();
//        getMovieReviews();

    }

//    private void getMovieReviews() {
//        MovieController.getMovieReviews(String.valueOf(movieModel.getId()), new Callback<MovieReviewResponseModel>() {
//            @Override
//            public void onResponse(Call<MovieReviewResponseModel> call, Response<MovieReviewResponseModel> response) {
//                movieReviewResponseModel = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<MovieReviewResponseModel> call, Throwable t) {
//
//            }
//        });
//
//    }

//    private void getMovieTrailers() {
//        MovieController.getMovieVideos(String.valueOf(movieModel.getId()), new Callback<MovieTrailersResponseModel>() {
//            @Override
//            public void onResponse(Call<MovieTrailersResponseModel> call, Response<MovieTrailersResponseModel> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieTrailersResponseModel> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        try {
            if (menu != null) {
                menu.findItem(R.id.top_rated).setVisible(false);
                menu.findItem(R.id.most_popular).setVisible(false);
                menu.findItem(R.id.favorite).setVisible(false);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof MainActivity) {
            mainActivity.getToolbar().setTitle(movieModel.getTitle());
            mainActivity.getToolbar().setOverflowIcon(null);
            mainActivity.getProgressbar().setVisibility(View.GONE);
            setHasOptionsMenu(true);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

}
