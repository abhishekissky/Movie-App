package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.Model.MovieModel;

public class DetailsActivity extends AppCompatActivity {

    private TextView title,details;
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
        getFromIntentData();
    }

    private void getFromIntentData() {
        if(getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            Log.v("Tag","Movie Detail :"+movieModel.getTitle());
            Log.v("Tag","Movie Detail :"+movieModel.getOverview());
            Log.v("Tag","Movie Detail :"+movieModel.getVote_count());


            title.setText(movieModel.getTitle());
            details.setText(movieModel.getOverview());
            ratingBar.setRating(movieModel.getVote_count()/2);


            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"+movieModel.getPoster_path())
                    .into(imageView);

        }
    }
}