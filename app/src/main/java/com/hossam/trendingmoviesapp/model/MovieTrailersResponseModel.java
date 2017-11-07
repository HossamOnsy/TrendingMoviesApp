package com.hossam.trendingmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hossam on 02/11/17.
 */

public class MovieTrailersResponseModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieTrailerModel> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieTrailerModel> getResults() {
        return results;
    }

    public void setResults(List<MovieTrailerModel> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeList(this.results);
    }

    public MovieTrailersResponseModel() {
    }

    protected MovieTrailersResponseModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = new ArrayList<MovieTrailerModel>();
        in.readList(this.results, MovieTrailerModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieTrailersResponseModel> CREATOR = new Parcelable.Creator<MovieTrailersResponseModel>() {
        @Override
        public MovieTrailersResponseModel createFromParcel(Parcel source) {
            return new MovieTrailersResponseModel(source);
        }

        @Override
        public MovieTrailersResponseModel[] newArray(int size) {
            return new MovieTrailersResponseModel[size];
        }
    };
}
