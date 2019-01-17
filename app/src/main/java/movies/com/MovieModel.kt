package movies.com

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import movies.com.model.DatabaseHandler
import movies.com.model.Movie
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class MovieModel(application: Application) : AndroidViewModel(application) {

    private var movieList: JsonLiveData? = null


    private val refresh = MutableLiveData<Int>()
    private var mDataBase: DatabaseHandler? = null

    init {
        if (movieList == null)
            movieList = JsonLiveData(application)

    }

    fun getMovieList(): MutableLiveData<ArrayList<Movie>>? {
        return movieList
    }

    inner class JsonLiveData(context: Context) : MutableLiveData<ArrayList<Movie>>() {


        init {
            LoadData(context)
        }


        private fun LoadData(context: Context) {
            mDataBase = AppController.getDatabaseHandler_(AppController.instance_!!)
            val list = mDataBase!!.allMovies as ArrayList<Movie>
            if (list.size > 0) {
                setValue(list)
                refresh.setValue(1)
                return
            }
            val postRequest = object : StringRequest(Request.Method.GET, URL_JSON,
                    Response.Listener { response ->
                        val gson = Gson()
                        var array: JSONArray? = null
                        try {
                            array = JSONArray(response)
                            if (array.length() > 0) {
                                val movieList = ArrayList<Movie>()
                                for (i in 0 until array.length()) {
                                    val jObject = array.get(i) as JSONObject
                                    val movie = gson.fromJson(jObject.toString(), Movie::class.java)
                                    movieList.add(movie)
                                }
                                movieList.sortDescending()
                                setValue(movieList)
                                refresh.setValue(1)
                                mDataBase!!.addMovies(movieList)
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                   null
            ) {
                override fun getParams(): Map<String, String> {
                    return HashMap()
                }
            }
            AppController.instance_!!.addToRequestQueue(postRequest)
        }
    }

    companion object {
        private val URL_JSON = "https://api.androidhive.info/json/movies.json"
    }
}