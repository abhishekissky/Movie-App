package com.example.moviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.moviesapp.Adapters.MovieRecyclerView;
import com.example.moviesapp.Adapters.OnMovieListener;
import com.example.moviesapp.Model.MovieModel;

import com.example.moviesapp.ViewModel.MovieListViewModel;


import java.util.List;



public class MainListActivity extends AppCompatActivity implements OnMovieListener {


    MovieListViewModel movieListViewModel;
    boolean isPopular = true;
    RecyclerView recyclerView;
    MovieRecyclerView movieRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        recyclerView = findViewById(R.id.recycler_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SearchView
        setUpSearchView();

        configureRecyclerView();
        // Calling the Observes
        ObserverAnyData();

        ObserverAnyDataPopular();

        movieListViewModel.searchMovieApiPop(1);

    }


    // Observing any data change
    private void ObserverAnyData(){

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                if (movieModels!=null){
                    for (MovieModel movieModel:movieModels){
                        Log.v("Tag","onChange: "+movieModel.getTitle());
                        movieRecyclerView.setmMovies(movieModels);
                    }
                }else {
                    Log.v("Tag","Error");
                }
            }
        });
    }
    private void ObserverAnyDataPopular(){

        movieListViewModel.getMoviesPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                if (movieModels!=null){
                    for (MovieModel movieModel:movieModels){
                        Log.v("Tag","onChange: "+movieModel.getTitle());
                        movieRecyclerView.setmMovies(movieModels);
                    }
                }else {
                    Log.v("Tag","Error");
                }
            }
        });
    }

//    //4- calling method in main activity
//    private void searchMovieApi(String query,int pageNumber){
//        movieListViewModel.searchMovieApi(query,pageNumber);
//    }

    private void configureRecyclerView(){
        movieRecyclerView = new MovieRecyclerView(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(movieRecyclerView);

        //RecyclerView Pagination
        //Loading next page of api response

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                if (!recyclerView.canScrollVertically(1)){
                    // here the show the next page of the api
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }


    @Override
    public void onMovieClick(int position) {

//        Toast.makeText(this,"Position: "+ position,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("movie",movieRecyclerView.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void setUpSearchView(){
        SearchView searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular=false;
            }
        });
    }


}