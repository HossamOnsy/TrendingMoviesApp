package com.hossam.trendingmoviesapp.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hossam.trendingmoviesapp.MovieController;
import com.hossam.trendingmoviesapp.R;
import com.hossam.trendingmoviesapp.adapter.MoviesAdapter;
import com.hossam.trendingmoviesapp.model.MovieList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hossam.trendingmoviesapp.R.id.progressbar;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    MovieList movieList = new MovieList();
    MainActivity mainActivity;

    GridLayoutManager gridLayoutManager;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @Nullable
    @BindView(progressbar)
    public ProgressBar progressBar;

    Unbinder unbinder;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        unbinder = ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns());
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);


        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelable("movieList");
            MoviesAdapter moviesAdapter = new MoviesAdapter(movieList.getResults(), (MainActivity) getActivity());
            recyclerView.setAdapter(moviesAdapter);

        } else
            getMoviesList("popular");


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

    @Override
    public void onResume() {
        super.onResume();


        progressBar = getActivity().findViewById(progressbar);
        mainActivity = (MainActivity) getActivity();
        mainActivity.getToolbar().setTitle(getString(R.string.app_name));
        mainActivity.getToolbar().setOverflowIcon(mainActivity.getDrawable());
        mainActivity = (MainActivity) getActivity();
        mainActivity.invalidateOptionsMenu();
        setHasOptionsMenu(true);
        progressBar.setVisibility(View.GONE);

        //TODO Observable Pattern Tryout ... It Worked but i prefered the simple way
        /*movieList.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                movieList = (MovieList) o;

            }
        });*/

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movieList", movieList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.most_popular) {
            getMoviesList("popular");
            return true;
        } else if (id == R.id.top_rated) {
            getMoviesList("top_rated");
            return true;
        } else if (id == R.id.favorite) {
            startActivity(new Intent(getActivity(), FavoritesActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getMoviesList(String path) {
        getActivity().findViewById(progressbar).setVisibility(View.VISIBLE);
        MovieController.getPopularMoviesList(path, new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.code() >= 200 && response.code() < 400) {
                    movieList = response.body();
                    MoviesAdapter moviesAdapter = new MoviesAdapter(movieList.getResults(), (MainActivity) getActivity());
                    recyclerView.setAdapter(moviesAdapter);

                } else
                    Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                getActivity().findViewById(progressbar).setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
//                Log.v("Entered", "t  ---  >  " + t.getMessage());
                Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                getActivity().findViewById(progressbar).setVisibility(View.GONE);

            }
        });
    }

}
