package movies.com.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        var title: String,
        var image: String,
        var rating: Float,
        var releaseYear: Int,
        var genre: Array<String>) : Parcelable,Comparable<Movie> {


    constructor() : this("", "",
            0.toFloat(), 0, emptyArray()
    )

    override fun compareTo(other: Movie)
            = compareValuesBy(this, other) { it.releaseYear }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Movie)
            return false
        return title == other.title
        return false;

    }

}



