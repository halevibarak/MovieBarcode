package movies.com.ui

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import movies.com.ActionInterface
import movies.com.AppController
import movies.com.MovieAdapter
import movies.com.dbqr.R
import movies.com.model.DatabaseHandler
import movies.com.model.Movie
import movies.com.ui.DecoderActivity.FROM_BARCODE
import movies.com.ui.DescriptionActivity.KEY_MOVIE
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class MovieListFragment : Fragment(), ActionInterface {


    private var mRecyclerView: RecyclerView? = null
    private var mMovieList: MutableList<Movie>? = null
    private var mMovieAdapter: MovieAdapter? = null
    private var mDataBase: DatabaseHandler? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.list_fragment,
                container, false)

        mRecyclerView = root.findViewById(R.id.movie_list)
        mRecyclerView!!.layoutManager = LinearLayoutManager(mRecyclerView!!.context)
        mRecyclerView!!.addItemDecoration(MovieDecoration(dpToPx(10)))
        mRecyclerView!!.itemAnimator = DefaultItemAnimator()
        mRecyclerView!!.setHasFixedSize(true)
        mMovieList = arguments!!.getParcelableArrayList(ARG_LIST)
        mMovieAdapter = MovieAdapter(mMovieList, this)
        mRecyclerView!!.adapter = mMovieAdapter

        val fab = root.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context!!,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                                Manifest.permission.CAMERA)) {
                    showPermissionSnackbar()
                } else {
                    ActivityCompat.requestPermissions(activity!!,
                            arrayOf(Manifest.permission.CAMERA),
                            1)
                }
            } else {
                val descActivityIntent = Intent(context, DecoderActivity::class.java)
                startActivityForResult(descActivityIntent, 10)
            }
        }

        return root

    }

    override fun goToDescription(movie: Movie) {
        val descActivityIntent = Intent(context, DescriptionActivity::class.java)
        val b = Bundle()
        b.putParcelable(KEY_MOVIE, movie)
        descActivityIntent.putExtras(b)
        activity!!.startActivity(descActivityIntent)
    }

    private fun showPermissionSnackbar() {
        Snackbar.make(mRecyclerView!!, getString(R.string.permission_text), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val descActivityIntent = Intent(context, DecoderActivity::class.java)
                    startActivityForResult(descActivityIntent, 10)
                }
                return
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                val result = data!!.getStringExtra(FROM_BARCODE)
                val gson = Gson()
                var jObject: JSONObject? = null
                try {
                    mRecyclerView.let {
                        jObject = JSONObject(result)
                        val movie = gson.fromJson(jObject.toString(), Movie::class.java)
                        if (mMovieList!!.contains(movie)) {
                            Snackbar.make(mRecyclerView!!, "Current movie already exist in the Database", Snackbar.LENGTH_LONG).show()
                            return
                        }
                        mDataBase = AppController.getDatabaseHandler_(AppController.instance_!!)
                        mDataBase!!.addMovie(movie)
                        mMovieList!!.add(movie)

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                mMovieList.let {
                    mMovieList!!.sortDescending()
                    mMovieAdapter!!.updateList(mMovieList)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRecyclerView = null
        if (mDataBase != null) mDataBase!!.close()
        mDataBase = null
        mMovieList = null
        mMovieAdapter = null
    }

    companion object {
        val ARG_LIST = "ARG_LIST"


        fun newInstance(movieList: ArrayList<Movie>): MovieListFragment {
            val fragment = MovieListFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_LIST, movieList)
            fragment.arguments = args
            return fragment

        }

        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}
