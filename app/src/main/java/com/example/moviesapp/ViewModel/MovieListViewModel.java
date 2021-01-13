package com.example.moviesapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesapp.Model.MovieModel;
import com.example.moviesapp.Repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {


    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository=MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getMoviesPop() {
        return movieRepository.getMoviesPop();
    }

    // 3- Calling method in view-model
    public void searchMovieApi(String query,int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }
    public void searchMovieApiPop(int pageNumber){
        movieRepository.searchMovieApiPop(pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
}

