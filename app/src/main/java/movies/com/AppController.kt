package movies.com

import android.app.Application
import android.content.Context

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import movies.com.model.DatabaseHandler


class AppController : Application() {

    private var mRequestQueue: RequestQueue? = null


    val requestQueue_: RequestQueue
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(applicationContext)
            }
            return mRequestQueue!!
        }

    override fun onCreate() {
        super.onCreate()
        instance_ = this
    }


    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        requestQueue_.add(req)
    }

    companion object {

        val TAG = AppController::class.java
                .simpleName
        private var mDatabaseHandler: DatabaseHandler? = null

        @get:Synchronized
        var instance_: AppController? = null
            private set

        fun getDatabaseHandler_(context: Context): DatabaseHandler {
            if (mDatabaseHandler == null) {
                synchronized(context.javaClass) {
                    if (mDatabaseHandler == null)
                        mDatabaseHandler = DatabaseHandler(context)
                }
            }
            return mDatabaseHandler!!
        }
    }
}
