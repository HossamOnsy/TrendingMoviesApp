package com.hossam.trendingmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hossam on 30/09/17.
 */

public class MovieReviewListModel implements Parcelable {


    List<MovieReviewModel> movieReviewModels;

    public MovieReviewListModel(List<MovieReviewModel> movieReviewModels) {
        this.movieReviewModels = movieReviewModels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.movieReviewModels);
    }

    public MovieReviewListModel() {
    }

    protected MovieReviewListModel(Parcel in) {
        this.movieReviewModels = in.createTypedArrayList(MovieReviewModel.CREATOR);
    }

    public static final Creator<MovieReviewListModel> CREATOR = new Creator<MovieReviewListModel>() {
        @Override
        public MovieReviewListModel createFromParcel(Parcel source) {
            return new MovieReviewListModel(source);
        }

        @Override
        public MovieReviewListModel[] newArray(int size) {
            return new MovieReviewListModel[size];
        }
    };

    public List<MovieReviewModel> getMovieReviewModels() {
        return movieReviewModels;
    }

    public void setMovieReviewModels(List<MovieReviewModel> movieReviewModels) {
        this.movieReviewModels = movieReviewModels;
    }
}
