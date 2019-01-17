package movies.com.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import movies.com.MovieModel;
import movies.com.dbqr.R;
import movies.com.model.Movie;


public class SplashFragment extends Fragment {
    private MovieModel movieModel;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    public SplashFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movieModel = ViewModelProviders.of(this).get(MovieModel.class);
        movieModel.getMovieList().observe(this, movie -> {
            showListScreen(movie);
        });
        return inflater.inflate(R.layout.fragment_splash,
                container, false);

    }

    private void showListScreen(ArrayList<Movie> movieList) {
        MovieListFragment newFragment = MovieListFragment.Companion.newInstance(movieList);
        Bundle args = new Bundle();
        args.putParcelableArrayList(MovieListFragment.Companion.getARG_LIST(),  movieList);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
