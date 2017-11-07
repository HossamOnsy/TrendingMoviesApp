package com.hossam.trendingmoviesapp.view;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hossam.trendingmoviesapp.R;
import com.hossam.trendingmoviesapp.adapter.MoviesAdapter;
import com.hossam.trendingmoviesapp.model.MovieModel;

import java.util.ArrayList;

import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.CONTENT_URI;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.KEY_VOTE_AVERAGE;


public class FavoritesFragment extends Fragment {


    RecyclerView recycler_view;
    ArrayList<MovieModel> movieModelArrayList;
    MoviesAdapter moviesAdapter;
    GridLayoutManager gridLayoutManager;

    public FavoritesFragment() {
        // Required empty public constructor
    }
    Cursor cursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recycler_view =  view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns());
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(gridLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        movieModelArrayList = new ArrayList<>();
        cursor = getActivity().getContentResolver().query(CONTENT_URI, null, null, null, KEY_VOTE_AVERAGE);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int i = 0;
                Log.v("setAdaptersetAdapter", cursor.getColumnName(0) + ": findEntry.getColumnCount();");
                MovieModel movieModel = new MovieModel();
                movieModel.setVoteCount(Integer.parseInt(cursor.getString(i++)));
                movieModel.setId(Integer.parseInt(cursor.getString(i++)));
                i++;
//                movieModel.setVideo(Double.parseDouble(cursor.getString(i++)));
                movieModel.setVoteAverage(Double.parseDouble(cursor.getString(i++)));
                movieModel.setTitle(cursor.getString(i++));
                movieModel.setPopularity(Double.parseDouble(cursor.getString(i++)));
                movieModel.setPosterPath(cursor.getString(i++));
                movieModel.setOriginalLanguage(cursor.getString(i++));
                movieModel.setOriginalTitle(cursor.getString(i++));
                movieModel.setBackdropPath(cursor.getString(i++));
                i++;
//                movieModel.setAdult(cursor.get(i++));
                movieModel.setOverview(cursor.getString(i++));
                movieModel.setReleaseDate(cursor.getString(i));

                movieModelArrayList.add(movieModel);
            } while (cursor.moveToNext());
            cursor.close();

            moviesAdapter = new MoviesAdapter(movieModelArrayList, getActivity());

            recycler_view.setAdapter(moviesAdapter);

        }else
        {
            moviesAdapter = new MoviesAdapter(movieModelArrayList, getActivity());



            recycler_view.setAdapter(moviesAdapter);
        }

    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
}

