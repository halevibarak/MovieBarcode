package movies.com.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import movies.com.MovieModel
import movies.com.dbqr.R
import movies.com.model.Movie
import java.util.*


class SplashFragment : Fragment() {
    private var movieModel: MovieModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        movieModel = ViewModelProviders.of(this).get(MovieModel::class.java)
        movieModel!!.getMovieList()!!.observe(this, Observer { movies -> showListScreen(movies) })
        return inflater.inflate(R.layout.fragment_splash,
                container, false)

    }

    private fun showListScreen(movieList: ArrayList<Movie>?) {
        val newFragment = MovieListFragment.newInstance(movieList!!)
        val args = Bundle()
        args.putParcelableArrayList(MovieListFragment.ARG_LIST, movieList)
        newFragment.arguments = args
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {

        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }


}
