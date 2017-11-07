package com.hossam.trendingmoviesapp.view;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.hossam.trendingmoviesapp.R;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressbar;
    Toolbar toolbar;
    MainActivityFragment mainActivityFragment;

    Drawable drawable;
    static Boolean rotatedScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawable = toolbar.getOverflowIcon();
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        if (!rotatedScreen) {
            mainActivityFragment = new MainActivityFragment();
            mainFragmentTransaction(mainActivityFragment);
        }
    }


    public void mainFragmentTransaction(MainActivityFragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();

        int count = getFragmentManager().getBackStackEntryCount();
        fragment.setArguments(bundle);
        if (count == 0) {
            fragmentTransaction.add(R.id.fragment_container, fragment, "MainActivityFragment");
        } else
            fragmentTransaction.replace(R.id.fragment_container, fragment, "MainActivityFragment");

        fragmentTransaction.commit();
        mainActivityFragment = fragment;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public ProgressBar getProgressbar() {
        return progressbar;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        rotatedScreen = true;
    }

    @Override
    public void onBackPressed() {
        MovieDetailsFragment test = (MovieDetailsFragment) getFragmentManager().findFragmentByTag(getString(R.string.movie_detail_fragment_tag));
        if (!(test != null && test.isVisible())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setMessage(R.string.exit)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        } else {
            getToolbar().setTitle(getString(R.string.app_name));
            getToolbar().setOverflowIcon(getDrawable());
            super.onBackPressed();

        }
    }
}
