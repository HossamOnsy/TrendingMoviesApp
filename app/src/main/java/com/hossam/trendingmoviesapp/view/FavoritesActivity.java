package com.hossam.trendingmoviesapp.view;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hossam.trendingmoviesapp.R;

public class FavoritesActivity extends AppCompatActivity {

    FavoritesFragment fragment;
    static Boolean rotatedScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        Bundle bundle = new Bundle();


        if (!rotatedScreen) {
            fragment = new FavoritesFragment();
            FragmentTransaction(fragment);
        }
//         fragment = new FavoritesFragment();
//        fragment.setArguments(bundle);
//        fragmentTransaction.add(R.id.fragment_container, fragment, "MainActivityFragment");
//        fragmentTransaction.commit();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        rotatedScreen = true;
    }

    public void FragmentTransaction(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();

        int count = getFragmentManager().getBackStackEntryCount();
        fragment.setArguments(bundle);
        if (count == 0) {
            fragmentTransaction.add(R.id.fragment_container, fragment, getString(R.string.favoritefragment_tag));
        } else
            fragmentTransaction.replace(R.id.fragment_container, fragment, getString(R.string.favoritefragment_tag));

        fragmentTransaction.commit();
    }

}
