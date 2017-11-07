package com.hossam.trendingmoviesapp.rest;

import com.hossam.trendingmoviesapp.model.MovieList;
import com.hossam.trendingmoviesapp.model.MovieReviewResponseModel;
import com.hossam.trendingmoviesapp.model.MovieTrailersResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hossam on 30/09/17.
 */

public interface NetworkAPI {

    @GET("3/movie/{popular}")
    Call<MovieList> getPopularMovieList(@Path("popular") String path, @Query("api_key") String api_key);

    @GET("3/movie/{id}/videos")
    Call<MovieTrailersResponseModel> getMovieVideos(@Path("id") String id, @Query("api_key") String api_key);

    @GET("3/movie/{id}/reviews")
    Call<MovieReviewResponseModel> getMovieReviews(@Path("id") String id, @Query("api_key") String api_key);

}
