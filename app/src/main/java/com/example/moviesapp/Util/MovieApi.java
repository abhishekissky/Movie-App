package com.example.moviesapp.Util;

import com.example.moviesapp.Model.MovieModel;
import com.example.moviesapp.Model.ReviewJSONResponse;
import com.example.moviesapp.Response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovieApi(
            @Query("api_key") String API_Key,
            @Query("query") String Query,
            @Query("page") int Page
    );


    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> responseById(
            @Path("movie_id") int movie_id,
            @Query("api_key") String API_Key);



    //Popular Movie Api
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> GetPop(
            @Query("api_key") String API_KEY,
            @Query("page") int pageNumber
    );

    @GET("/3/movie/{movie_id}/reviews")
    Call<ReviewJSONResponse> getReview(
            @Path("movie_id") int movie_id,
            @Query("api_key") String API_Key
    );
}


