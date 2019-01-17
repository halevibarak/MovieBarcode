package movies.com.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "moviesManager";
    private static final String TABLE_MOVIE = "movies";
    public static final String KEY_MOVIE = "title";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_RATING = "rating";
    private static final String KEY_RELEASE = "release_";
    private static final String KEY_GENRE = "genre";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIE + "("
                + KEY_MOVIE + " TEXT PRIMARY KEY," + KEY_IMAGE + " TEXT,"
                + KEY_RATING + " REAL," + KEY_RELEASE + " INT," + KEY_GENRE + " TEXT" + ")";
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }
    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE, movie.getTitle());
        values.put(KEY_IMAGE, movie.getImage());
        values.put(KEY_RATING, movie.getRating());
        values.put(KEY_RELEASE, movie.getReleaseYear());
        values.put(KEY_GENRE, Arrays.toString(movie.getGenre()));

        // Inserting Row
        db.insert(TABLE_MOVIE, null, values);
        db.close();
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<Movie>();
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(0));
                movie.setImage(cursor.getString(1));
                movie.setRating(cursor.getFloat(2));
                movie.setReleaseYear(cursor.getInt(3));
                movie.setGenre(cursor.getString(4).split(","));
                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        return movieList;
    }


    public void deleteMovies() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIE, null,
                new String[] {  });
        db.close();
    }

    public void addMovies(@NotNull ArrayList<Movie> movieList) {
        for (Movie mov:movieList) {
            addMovie(mov);
        }
    }
}