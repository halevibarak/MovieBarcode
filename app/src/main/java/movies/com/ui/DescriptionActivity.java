package movies.com.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

import movies.com.MyRecyclerViewAdapter;
import movies.com.dbqr.R;
import movies.com.model.Movie;

public class DescriptionActivity extends AppCompatActivity {

    public static final String KEY_MOVIE = "KEY_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Bundle bundle = this.getIntent().getExtras();
        Movie movie = bundle.getParcelable(KEY_MOVIE);

        ((TextView) findViewById(R.id.textView1)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.textView2)).setText(movie.getReleaseYear() + "");

        RatingBar rating = findViewById(R.id.rating);
        rating.setIsIndicator(true);
        rating.setNumStars(10);
        rating.setMax(10);
        rating.setStepSize(0.02f);
        rating.setRating(movie.getRating());

        Picasso.with(this).load(movie.getImage())
                .error(R.drawable.ic_launcher_background)
                .into((ImageView) findViewById(R.id.image));

        RecyclerView recyclerView = findViewById(R.id.gen_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, Arrays.asList(movie.getGenre()));
        recyclerView.setAdapter(adapter);

    }

}
