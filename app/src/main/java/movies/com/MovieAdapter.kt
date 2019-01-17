package movies.com

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import movies.com.dbqr.R
import movies.com.model.Movie


class MovieAdapter(private val MovieCellList: MutableList<Movie>, private val mLisenner: ActionInterface) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun getItemCount(): Int {
        return MovieCellList.size
    }

    fun getItem(position: Int): Movie? {
        return MovieCellList[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val horizontalItem = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return MovieViewHolder(horizontalItem)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = getItem(position)
        if (movie != null) {
            holder.nameView.text = movie.title
            holder.yearView.text = movie.releaseYear.toString() + ""
            Glide.with(holder.imgView.getContext()).load(movie.image).into(holder.imgView);
        }
        holder.itemView.setOnClickListener { mLisenner.goToDescription(movie!!) }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val imgView: ImageView = itemView.findViewById(R.id.img_view)
        internal var nameView: TextView = itemView.findViewById(R.id.name_text)
        internal var yearView: TextView = itemView.findViewById(R.id.year_text)

    }
}
