package com.dementorsun.telegrambot.client.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class TmdbResponse {

    List<TmdbItem> results;

    @Setter
    @Getter
    public static class TmdbItem {
        @SerializedName("original_title")
        String originalTitle;
        @SerializedName("original_name")
        String originalName;
        String overview;
        @SerializedName("poster_path")
        String posterPath;
        @SerializedName("release_date")
        String releaseDate;
        @SerializedName("first_air_date")
        String firstAirDate;
        String title;
        String name;
        @SerializedName("vote_average")
        Double voteAverage;
    }
}