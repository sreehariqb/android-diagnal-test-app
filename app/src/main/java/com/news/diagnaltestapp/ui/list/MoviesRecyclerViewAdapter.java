package com.news.diagnaltestapp.ui.list;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.news.diagnaltestapp.R;
import com.news.diagnaltestapp.data.model.Content;
import com.news.diagnaltestapp.utilities.Constants;
import com.news.diagnaltestapp.utilities.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder> {

    private Context context;
    private List<Content> movieList = new ArrayList<>();
    private String searchString = Constants.EMPTY_STRING;

    MoviesRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_movies_list_item, viewGroup, false);
        ViewUtil.setMovieListItemLayoutParam(view, viewGroup, context.getResources());
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        ViewUtil.setPosterImage(context.getAssets(), movieList.get(moviesViewHolder.getAdapterPosition()).getPosterImage(), moviesViewHolder.moviePoster);
        moviesViewHolder.movieTitle.setText(movieList.get(moviesViewHolder.getAdapterPosition()).getName());
        String movieName = movieList.get(moviesViewHolder.getAdapterPosition()).getName();
        if (!searchString.equals(Constants.EMPTY_STRING) &&
                movieName.toLowerCase().contains(searchString.toLowerCase())) {
            int startPos = movieName.toLowerCase().indexOf(searchString.toLowerCase());
            int endPos = startPos + searchString.length();

            Spannable spanText = Spannable.Factory.getInstance().newSpannable(moviesViewHolder.movieTitle.getText());
            spanText.setSpan(new ForegroundColorSpan(Color.YELLOW), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            moviesViewHolder.movieTitle.setText(spanText, TextView.BufferType.SPANNABLE);
        }

    }

    void updateMovies(List<Content> moviesItems) {
        for(Content movie: moviesItems) {
            add(movie);
        }
    }

    void updateSearchResult(List<Content> movies, String searchString) {
        this.searchString = searchString;
        movieList.clear();
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    private void add(Content movie) {
        movieList.add(movie);
        notifyItemInserted(movieList.size()- Constants.NUMBER_ONE);
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : Constants.NUMBER_ZERO;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle;
        ProgressBar progressLoader;
        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            progressLoader = itemView.findViewById(R.id.progress_circular);
        }
    }
}
