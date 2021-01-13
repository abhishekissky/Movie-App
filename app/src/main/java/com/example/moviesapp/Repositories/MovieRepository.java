package com.example.moviesapp.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.Model.MovieModel;
import com.example.moviesapp.Request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance() {

        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }
    public LiveData<List<MovieModel>> getMoviesPop() {
        return movieApiClient.getMoviesPop();
    }

    //2- Calling the Method

    public void searchMovieApi(String query,int pageNumber){
        mQuery=query;
        mPageNumber=pageNumber;
        movieApiClient.searchMovieApi(query,pageNumber);
    }

    public void searchMovieApiPop(int pageNumber){
        this.mPageNumber=pageNumber;
        movieApiClient.searchMovieApiPop(pageNumber);
    }

    public void searchNextPage(){
        searchMovieApi(mQuery,mPageNumber+1);
    }


}


