package com.example.moviesapp.Request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.AppExecutors;
import com.example.moviesapp.Model.MovieModel;
import com.example.moviesapp.R;
import com.example.moviesapp.Response.MovieSearchResponse;
import com.example.moviesapp.Util.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    public static MovieApiClient instance;

    //Live Data for Search
    private MutableLiveData<List<MovieModel>> mMovies;

    //making Global Runnable for Search
    RetrieveMoviesRunnable retrieveMoviesRunnable;

    //Live Data for popular
    private MutableLiveData<List<MovieModel>> mMoviesPop;

    //making global runnable for popular
    RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;

    public static MovieApiClient getInstance() {
        if(instance==null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
    }


    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPop(){
        return mMoviesPop;
    }

    //1- This method are calling through classes for search
    public void searchMovieApi(String query,int pageNumber){

        if (retrieveMoviesRunnable!=null){
            retrieveMoviesRunnable=null;
        }

        retrieveMoviesRunnable= new RetrieveMoviesRunnable(query,pageNumber);

        final Future myHandler = AppExecutors.getInstance().NetworkIO().submit(retrieveMoviesRunnable);
        AppExecutors.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancel the Retrofit call

                myHandler.cancel(true);

            }
        },5000, TimeUnit.MILLISECONDS);

    }

    //1- This method are calling through classes for popular
    public void searchMovieApiPop(int pageNumber){

        if (retrieveMoviesRunnablePop!=null){
            retrieveMoviesRunnablePop=null;
        }

        retrieveMoviesRunnablePop= new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandlerPop = AppExecutors.getInstance().NetworkIO().submit(retrieveMoviesRunnablePop);
        AppExecutors.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancel the Retrofit call

                myHandlerPop.cancel(true);

            }
        },1000, TimeUnit.MILLISECONDS);

    }

    //Retrieve the data from RestApi by runnable class for Search
    //We have 2 type of Queries:Query and Id
    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            // Getting the response objects

            try {
                Response response = getMovies(query,pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if (response.code()==200){

                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());

                    if (pageNumber==1){
                        //sending data to live data
                        //postValue: used for background thread
                        //setValue : not for background thread
                        mMovies.postValue(list);

                    }else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }

                }else {
                    String error = response.errorBody().toString();
                    Log.v("Tag","Response Error "+error);
                    mMovies.postValue(null);
                }


            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }


        }

        //Search method query

        Call<MovieSearchResponse> getMovies(String query, int pageNumber){

            return Service.getMovieApi().searchMovieApi(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }

        public void cancelRequest(){
            Log.v("Tag","Cancelling Search Request");
            cancelRequest=true;
        }
    }

    //Retrieve the data from RestApi by runnable class for Popular
    private class RetrieveMoviesRunnablePop implements Runnable{


        private int pageNumberP;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop( int pageNumber) {

            this.pageNumberP = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            // Getting the response objects

            try {
                Response responsePop = getMoviesPop(pageNumberP).execute();
                if(cancelRequest){
                    return;
                }
                if (responsePop.code()==200){

                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)responsePop.body()).getMovies());

                    if (pageNumberP==1){
                        //sending data to live data
                        //postValue: used for background thread
                        //setValue : not for background thread
                        mMoviesPop.postValue(list);

                    }else {
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }

                }else {
                    String error = responsePop.errorBody().toString();
                    Log.v("Tag","Response Error "+error);
                    mMovies.postValue(null);
                }


            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }


        }

        //Search method query

        Call<MovieSearchResponse> getMoviesPop(int pageNumber){

            return Service.getMovieApi().GetPop(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        public void cancelRequest(){
            Log.v("Tag","Cancelling Search Request");
            cancelRequest=true;
        }
    }
}

