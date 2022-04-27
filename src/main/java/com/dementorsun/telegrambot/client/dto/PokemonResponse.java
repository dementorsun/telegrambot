package com.dementorsun.telegrambot.client.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class PokemonResponse {

    private String name;
    private Sprite sprites;
    private Integer id;
    private List<PokemonType> types;

    @Getter
    public static class Sprite {
        private Other other;

        @Getter
        public static class Other {
            @SerializedName("official-artwork")
            private OfficialArtwork officialArtwork;

            @Getter
            public static class OfficialArtwork {
                @SerializedName("front_default")
                private String frontDefault;
            }
        }
    }

    @Getter
    public static class PokemonType {
        private Type type;

        @Getter
        public static class Type {
            private String name;
        }
    }
}