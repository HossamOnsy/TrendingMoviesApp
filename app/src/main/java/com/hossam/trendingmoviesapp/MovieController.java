package com.hossam.trendingmoviesapp;

import com.hossam.trendingmoviesapp.model.MovieList;
import com.hossam.trendingmoviesapp.model.MovieReviewResponseModel;
import com.hossam.trendingmoviesapp.model.MovieTrailersResponseModel;
import com.hossam.trendingmoviesapp.rest.NetworkAPI;
import com.hossam.trendingmoviesapp.rest.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;

import static com.hossam.trendingmoviesapp.rest.NetworkService.api_key;

/**
 * Created by hossam on 30/09/17.
 */

public class MovieController {


    public static void getPopularMoviesList(String path, Callback<MovieList> callback) {

        NetworkService networkService = new NetworkService();
        NetworkAPI networkAPI = networkService.getAPI();
        Call<MovieList> call = networkAPI.getPopularMovieList(path, api_key);
        call.enqueue(callback);
    }

    public static void getMovieVideos(String id, Callback<MovieTrailersResponseModel> callback) {

        NetworkService networkService = new NetworkService();
        NetworkAPI networkAPI = networkService.getAPI();
        Call<MovieTrailersResponseModel> call = networkAPI.getMovieVideos(id, api_key);
        call.enqueue(callback);
    }

    public static void getMovieReviews(String id, Callback<MovieReviewResponseModel> callback) {

        NetworkService networkService = new NetworkService();
        NetworkAPI networkAPI = networkService.getAPI();
        Call<MovieReviewResponseModel> call = networkAPI.getMovieReviews(id, api_key);
        call.enqueue(callback);
    }


}
