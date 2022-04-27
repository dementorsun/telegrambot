package com.dementorsun.telegrambot.client.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class PokemonDescriptionResponse {

    @SerializedName("flavor_text_entries")
    private List<PokemonDescription> pokemonDescriptions;

    @Getter
    public static class PokemonDescription {
        @SerializedName("flavor_text")
        private String description;
        private Language language;

        @Getter
        public static class Language {
            private String name;
        }
    }
}