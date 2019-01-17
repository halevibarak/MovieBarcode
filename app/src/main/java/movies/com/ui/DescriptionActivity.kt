package movies.com.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import movies.com.MyRecyclerViewAdapter
import movies.com.dbqr.R
import movies.com.model.Movie
import java.util.*

class DescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val bundle = this.intent.extras
        val movie = bundle!!.getParcelable<Movie>(KEY_MOVIE)

        (findViewById<View>(R.id.textView1) as TextView).text = movie!!.title
        (findViewById<View>(R.id.textView2) as TextView).text = movie.releaseYear.toString() + ""

        val rating = findViewById<RatingBar>(R.id.rating)
        rating.setIsIndicator(true)
        rating.numStars = 10
        rating.max = 10
        rating.stepSize = 0.02f
        rating.rating = movie.rating

        Picasso.with(this).load(movie.image)
                .error(R.drawable.ic_launcher_background)
                .into(findViewById<View>(R.id.image) as ImageView)

        val recyclerView = findViewById<RecyclerView>(R.id.gen_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MyRecyclerViewAdapter(this, Arrays.asList(*movie.genre))
        recyclerView.adapter = adapter

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    companion object {

        val KEY_MOVIE = "KEY_MOVIE"
    }
}
