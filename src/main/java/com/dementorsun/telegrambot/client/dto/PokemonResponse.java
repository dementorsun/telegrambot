package com.dementorsun.telegrambot.client.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PokemonResponse {

    String name;
    Sprite sprites;
    Integer id;
    List<PokemonType> types;

    @Getter
    public static class Sprite {
        Other other;

        @Getter
        public static class Other {
            @SerializedName("official-artwork")
            OfficialArtwork officialArtwork;

            @Getter
            public static class OfficialArtwork {
                @SerializedName("front_default")
                String frontDefault;
            }
        }
    }

    @Getter
    public static class PokemonType {
        Type type;

        @Getter
        public static class Type {
            String name;
        }
    }
}