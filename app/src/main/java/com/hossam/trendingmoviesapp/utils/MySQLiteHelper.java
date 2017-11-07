package com.hossam.trendingmoviesapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.hossam.trendingmoviesapp.model.MovieModel;

/**
 * Created by hossamonsy on 20/08/17.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {


    // Content Provider

    public static final String AUTHORITY = "com.hossam.trendingmoviesapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIE_TABLE = "FavoriteMovies";

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_MOVIE_TABLE).build();


    //


    public static String TABLE = "FavoriteMovies";
    // Books Table Columns names


    public static final String KEY_VOTE_COUNT = "vote_count";
    public static final String KEY_ID = "id";
    public static final String KEY_VIDEO = "video";
    public static final String KEY_VOTE_AVERAGE = "vote_average";
    public static final String KEY_TITLE = "title";
    public static final String KEY_POPULARITY = "popularity";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_ORIGINAL_LANGUAGE = "original_language";
    public static final String KEY_ORIGINAL_TITLE = "original_title";
    public static final String KEY_BACKDROP_PATH = "backdrop_path";
    public static final String KEY_ADULT = "adult";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_RELEASE_DATE = "release_date";

    public static final String[] COLUMNS = {KEY_VOTE_COUNT, KEY_ID, KEY_VIDEO, KEY_VOTE_AVERAGE, KEY_TITLE, KEY_POPULARITY, KEY_POSTER_PATH,
            KEY_ORIGINAL_LANGUAGE, KEY_ORIGINAL_TITLE, KEY_BACKDROP_PATH, KEY_ADULT, KEY_OVERVIEW, KEY_RELEASE_DATE};

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "FavoriteMoviesDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create PRODUCT table


        String CREATE_FavoriteMovies_TABLE = "CREATE TABLE FavoriteMovies ( " +
                "vote_count INTEGER , " +
                "id INTEGER PRIMARY KEY, " +
                "video BOOLEAN, " +
                "vote_average DOUBLE, " +
                "title TEXT, " +
                "popularity DOUBLE, " +
                "poster_path TEXT, " +
                "original_language TEXT, " +
                "original_title TEXT, " +
                "backdrop_path TEXT, " +
                "adult TEXT, " +
                "overview TEXT, " +
                "release_date TEXT)";
/*
        String CREATE_AR_PRODUCTS_TABLE = "CREATE TABLE arproducts ( " +
                "categoryId TEXT , " +
                "name TEXT, "+
                "id INTEGER PRIMARY KEY, " +
                "imageurl BLOB, " +
                "logourl BLOB, " +
                "hasquotation TEXT, " +
                "description TEXT)";*/
        // PRODUCTS books table
        db.execSQL(CREATE_FavoriteMovies_TABLE);

        Log.v("Tryout1", "Tryout1Tryout1" + db.getVersion());
    }

    public void DataBaseExecuting() {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older PRODUCTS table if existed
        db.execSQL("DROP TABLE IF EXISTS FavoriteMovies");

        // create fresh PRODUCTS table
        this.onCreate(db);
    }


    public void addFavoriteMovie(MovieModel movieModel, String Table) {
        Log.d("addBook", movieModel.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
//        KEY_VOTE_COUNT = "aawhdyawgdya"
//        KEY_NAME = "aawhdyawgdya"
//        KEY_ID = "aawhdyawgdya"
//        KEY_VIDEO = "aawhdyawgdya"
//        KEY_VOTE_AVERAGE = "aawhdyawgdya"
//        KEY_TITLE = "aawhdyawgdya"
//        KEY_POPULARITY = "aawhdyawgdya"
//        KEY_POSTER_PATH = "aawhdyawgdya"
//        KEY_ORIGINAL_LANGUAGE = "aawhdyawgdya"
//        KEY_ORIGINAL_TITLE = "aawhdyawgdya"
//        KEY_BACKDROP_PATH = "aawhdyawgdya"
//        KEY_ADULT = "aawhdyawgdya"
//        KEY_OVERVIEW = "aawhdyawgdya"
//        KEY_RELEASE_DATE = "aawhdyawgdya"

        ContentValues values = new ContentValues();
        values.put(KEY_VOTE_COUNT, movieModel.getVoteCount()); // 0
        values.put(KEY_ID, movieModel.getId()); // 1
        values.put(KEY_VIDEO, movieModel.getVideo()); // 2
        values.put(KEY_VOTE_AVERAGE, movieModel.getVoteAverage()); // 3
        values.put(KEY_TITLE, movieModel.getTitle()); // 4
        values.put(KEY_POPULARITY, movieModel.getPopularity()); // 5
        values.put(KEY_POSTER_PATH, movieModel.getPosterPath()); // 6
        values.put(KEY_ORIGINAL_LANGUAGE, movieModel.getOriginalLanguage()); // 7
        values.put(KEY_ORIGINAL_TITLE, movieModel.getOriginalTitle()); // 8
        values.put(KEY_BACKDROP_PATH, movieModel.getBackdropPath()); // 9
        values.put(KEY_ADULT, movieModel.getAdult()); // 10
        values.put(KEY_OVERVIEW, movieModel.getOverview()); // 11
        values.put(KEY_RELEASE_DATE, movieModel.getReleaseDate()); // 12


        // 3. insert
        db.insert(Table, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values


        // 4. close
        db.close();
    }

    public Integer deleteFavoriteMovie(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }


    public void removeAll() {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS FavoriteMovies");
//        db.execSQL("DROP TABLE IF EXISTS arproducts");

        // create fresh PRODUCTS table
        this.onCreate(db);
    }

//    public ProductModel getProduct(int id){
//
//        // 1. get reference to readable DB
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // 2. build query
//        Cursor cursor =
//                db.query(TABLE, // a. table
//                        COLUMNS, // b. column names
//                        " id = ?", // c. selections
//                        new String[] { String.valueOf(id) }, // d. selections args
//                        null, // e. group by
//                        null, // f. having
//                        null, // g. order by
//                        null); // h. limit
//
//        // 3. if we got results get the first one
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        // 4. build book object
//        ProductModel productModel = new ProductModel();
//        productModel.setCategoryId(cursor.getString(0)); // getCategoryId
//        productModel.setName(cursor.getString(1)); // getName
//        productModel.setId(Integer.parseInt(cursor.getString(2))); // getId
//        productModel.setImageurl(cursor.getString(3));
////        productModel.setImageurl(cursor.getBlob(3)); // getImageurl
//        productModel.setLogourl(cursor.getString(4)); // getLogourl
//        if(cursor.getString(5).equals("true"))
//        productModel.setHasquotation(true); // isHasquotation
//        else
//            productModel.setHasquotation(false); // isHasquotation
//
//        productModel.setDescription(cursor.getString(6)); // getDescription
//
//        Log.d("getProduct("+id+")", productModel.toString());
//
//        // 5. return book
//        return productModel;
//    }
}
