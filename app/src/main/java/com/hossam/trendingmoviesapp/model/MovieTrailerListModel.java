package com.hossam.trendingmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hossam on 30/09/17.
 */

public class MovieTrailerListModel implements Parcelable {
    public MovieTrailerListModel(List<MovieTrailerModel> movieTrailerModels) {
        this.movieTrailerModels = movieTrailerModels;
    }

    public List<MovieTrailerModel> getMovieTrailerModels() {
        return movieTrailerModels;
    }

    public void setMovieTrailerModels(List<MovieTrailerModel> movieTrailerModels) {
        this.movieTrailerModels = movieTrailerModels;
    }

    List<MovieTrailerModel> movieTrailerModels;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.movieTrailerModels);
    }

    public MovieTrailerListModel() {
    }

    protected MovieTrailerListModel(Parcel in) {
        this.movieTrailerModels = in.createTypedArrayList(MovieTrailerModel.CREATOR);
    }

    public static final Parcelable.Creator<MovieTrailerListModel> CREATOR = new Parcelable.Creator<MovieTrailerListModel>() {
        @Override
        public MovieTrailerListModel createFromParcel(Parcel source) {
            return new MovieTrailerListModel(source);
        }

        @Override
        public MovieTrailerListModel[] newArray(int size) {
            return new MovieTrailerListModel[size];
        }
    };
}
