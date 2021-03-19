package com.example.moviesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class ReviewJSONResponse {

    @Expose
    @SerializedName("results")
    private ReviewModel[] results;

    public ReviewModel[] getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "ReviewJSONResponse{" +
                "results=" + Arrays.toString(results) +
                '}';
    }
}
