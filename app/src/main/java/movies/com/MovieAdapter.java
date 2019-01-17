package movies.com;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movies.com.dbqr.R;
import movies.com.model.Movie;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> MovieCellList;
    private ActionInterface mLisenner;


    public MovieAdapter(List<Movie> contactList, ActionInterface listenner) {
        this.MovieCellList = contactList;
        mLisenner = listenner;
    }

    @Override
    public int getItemCount() {
        return MovieCellList.size();
    }

    public Movie getItem(int position) {
        return MovieCellList.get(position);
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View horizontalItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new MovieViewHolder(horizontalItem);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        final Movie movie = getItem(position);
        if (movie != null) {
            holder.nameView.setText(movie.getTitle());
            holder.yearView.setText(movie.getReleaseYear()+"");

            Picasso.with(holder.imgView.getContext()).load(movie.getImage())
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imgView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLisenner.goToDescription(movie);
            }
        });
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {


        public MovieViewHolder(View itemView) {
            super(itemView);
            nameView =  itemView.findViewById(R.id.name_text);
            yearView =  itemView.findViewById(R.id.year_text);
            imgView =  itemView.findViewById(R.id.img_view);

        }

        private final ImageView imgView;
        TextView nameView;
        TextView yearView;
    }
    public void updateList(List<Movie> newlist) {
        MovieCellList.clear();
        MovieCellList.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
