package com.dementorsun.telegrambot.client.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class TmdbResponse {

    private List<TmdbItem> results;

    @Setter
    @Getter
    public static class TmdbItem {
        @SerializedName("original_title")
        private String originalTitle;
        @SerializedName("original_name")
        private String originalName;
        private String overview;
        @SerializedName("poster_path")
        private String posterPath;
        @SerializedName("release_date")
        private String releaseDate;
        @SerializedName("first_air_date")
        private String firstAirDate;
        private String title;
        private String name;
        @SerializedName("vote_average")
        private Double voteAverage;
    }
}