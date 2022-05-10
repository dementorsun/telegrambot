package com.dementorsun.telegrambot.client.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PokemonDescriptionResponse {

    @SerializedName("flavor_text_entries")
    List<PokemonDescription> pokemonDescriptions;

    @Getter
    public static class PokemonDescription {
        @SerializedName("flavor_text")
        String description;
        Language language;

        @Getter
        public static class Language {
            String name;
        }
    }
}