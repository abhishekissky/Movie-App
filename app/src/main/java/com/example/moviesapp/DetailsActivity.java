package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.Model.MovieModel;
import com.example.moviesapp.Model.ReviewJSONResponse;
import com.example.moviesapp.Model.ReviewModel;
import com.example.moviesapp.Util.Credentials;
import com.example.moviesapp.Util.MovieApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {

    private TextView title,details,feedback;
    private ImageView imageView;
    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title = findViewById(R.id.title_details);
        details = findViewById(R.id.textView_details);
        imageView = findViewById(R.id.imageView_details);
        ratingBar = findViewById(R.id.ratingBar_details);
        feedback = findViewById(R.id.feedback);
        getFromIntentData();
    }

    private void getFromIntentData() {
        if(getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            Log.v("Tag","Movie Detail :"+movieModel.getTitle());
            Log.v("Tag","Movie Detail :"+movieModel.getOverview());
            Log.v("Tag","Movie Detail :"+movieModel.getVote_count());
            int id = movieModel.getId();
            Log.v("id", String.valueOf(id));


            title.setText(movieModel.getTitle());
            details.setText(movieModel.getOverview());
            ratingBar.setRating(movieModel.getVote_count()/2);


            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"+movieModel.getPoster_path())
                    .into(imageView);


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Credentials.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieApi movieApi = retrofit.create(MovieApi.class);

            Call<ReviewJSONResponse> reviewJSONResponseCall = movieApi.getReview(id,Credentials.API_KEY);
            reviewJSONResponseCall.enqueue(new Callback<ReviewJSONResponse>() {
                @Override
                public void onResponse(Call<ReviewJSONResponse> call, Response<ReviewJSONResponse> response) {
                    ReviewJSONResponse jsonResponse = response.body();
                    Log.v("jso", String.valueOf(jsonResponse.getResults()));
                    ReviewModel[] reviewModel = jsonResponse.getResults();
                    Log.v("jso",reviewModel[0].getContent());
                    if (!reviewModel[0].equals(null)) {
                        feedback.setText(reviewModel[0].getContent());
                    }


                }

                @Override
                public void onFailure(Call<ReviewJSONResponse> call, Throwable t) {

                }
            });

        }
    }
}