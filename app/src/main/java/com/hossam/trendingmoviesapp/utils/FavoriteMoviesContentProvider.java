package com.hossam.trendingmoviesapp.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.R.attr.id;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.CONTENT_URI;
import static com.hossam.trendingmoviesapp.utils.MySQLiteHelper.TABLE;

/**
 * Created by hossam on 06/11/17.
 */

public class FavoriteMoviesContentProvider extends ContentProvider {

    MySQLiteHelper mySQLiteHelper;

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = builderUriMatcher();

    public static UriMatcher builderUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


        uriMatcher.addURI(MySQLiteHelper.AUTHORITY, MySQLiteHelper.PATH_MOVIE_TABLE, MOVIES);
        uriMatcher.addURI(MySQLiteHelper.AUTHORITY, MySQLiteHelper.PATH_MOVIE_TABLE + "/#", MOVIE_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        mySQLiteHelper = new MySQLiteHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase sqLiteDatabase = mySQLiteHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match) {
            case MOVIES: {
                retCursor = sqLiteDatabase.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);

                break;
            }
            case MOVIE_WITH_ID: {

            }

            default:
                throw new UnsupportedOperationException("Unnown uri:" + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase sqLiteDatabase = mySQLiteHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri = null;

        switch (match) {
            case MOVIES: {
                long id = sqLiteDatabase.insert(TABLE, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                } else {
                    throw new UnsupportedOperationException("Unnown uri:" + uri);
                }

                break;
            }
            case MOVIE_WITH_ID: {

            }

            default:
                throw new UnsupportedOperationException("Unnown uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase sqLiteDatabase = mySQLiteHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int returnUri =0;

        switch (match) {
            case MOVIES: {
                 returnUri = sqLiteDatabase.delete(TABLE, s, strings);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unnown uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);


        return returnUri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
