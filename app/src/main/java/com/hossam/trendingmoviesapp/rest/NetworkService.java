package com.hossam.trendingmoviesapp.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hossam on 27/09/17.
 */

public class NetworkService {

    //TODO Use Your API key
    //---------------------------------------------------------------
//    public static String api_key = "Put Your API key Here";
    public static String api_key = "92268add4b3a98b593e13325b061f67b";

    //---------------------------------------------------------------
    public static String baseUrl = "https://api.themoviedb.org/";
    private NetworkAPI networkAPI;

    public NetworkService() {
        this(baseUrl);
    }


    private NetworkService(String baseUrl) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient();
        client = client.newBuilder()
                .addInterceptor(logging).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        networkAPI = retrofit.create(NetworkAPI.class);
    }

    /**
     * Method to return the API interface.
     *
     * @return
     */
    public NetworkAPI getAPI() {
        return networkAPI;
    }


}
