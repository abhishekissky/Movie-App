package com.example.moviesapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.Model.MovieModel;
import com.example.moviesapp.R;
import com.example.moviesapp.Util.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<MovieHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;
    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=null;
         if (viewType==DISPLAY_POP) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
            MovieHolder movieHolder = new MovieHolder(view,onMovieListener);
            return movieHolder;
        }else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
            MovieHolder movieHolder = new MovieHolder(view,onMovieListener);
            return movieHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType==DISPLAY_POP){
            holder.title.setText(mMovies.get(position).getTitle());
            holder.release_date.setText(mMovies.get(position).getRelease_date());
            holder.language.setText(mMovies.get(position).getOriginal_language());
            holder.ratingBar.setRating((mMovies.get(position).getVote_count()) / 2);
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(position).getPoster_path())
                    .into(holder.imageView);
        }else

            holder.title.setText(mMovies.get(position).getTitle());
            holder.release_date.setText(mMovies.get(position).getRelease_date());
            holder.language.setText(mMovies.get(position).getOriginal_language());
            holder.ratingBar.setRating(mMovies.get(position).getVote_count() / 2);
            Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(position).getPoster_path())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (mMovies!=null) {

            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel>mMovies){
        this.mMovies=mMovies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (Credentials.POPULAR){
            return DISPLAY_POP;
        }else
            return DISPLAY_SEARCH;
    }

    public MovieModel getSelectedMovie(int position){
        if (mMovies != null){
            if (mMovies.size()>0){
                return mMovies.get(position);
            }
        }
        return null;
    }
}
