package com.dementorsun.telegrambot.client.api;

import com.dementorsun.telegrambot.client.dto.*;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotClient {

    private static final String USER_AGENT_HEADER = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

    private final NasaApiClient nasaApiClient;
    private final CatApiClient catApiClient;
    private final DogApiClient dogApiClient;
    private final PokemonApiClient pokemonApiClient;
    private final QuoteApiClient quoteApiClient;
    private final TmdbApiClient tmdbApiClient;
    private final PexelsApiClient pexelsApiClient;
    private final Gson gson;

    @Value("${pexels.token}")
    private final String pexelsToken;

    public NasaApodResponse getNasaApod() {
        return nasaApiClient.getNasaApod();
    }

    public String getRandomCuteCat() {
        return catApiClient.getRandomCuteCat();
    }

    public String getRandomCuteDog() {
        return dogApiClient.getRandomCuteDog();
    }

    public RandomQuoteResponse getRandomQuote() {
        return quoteApiClient.getRandomQuote(USER_AGENT_HEADER);
    }

    public String getRandomMovie() {
        final int totalPages = 34;
        int page = getRandomNumber(totalPages);

        return tmdbApiClient.getRandomMovie(page);
    }

    public String getRandomTvShow() {
        final int totalPages = 36;
        int page = getRandomNumber(totalPages);
        int[] genres = new int[]{16, 10766};

        return tmdbApiClient.getRandomTvShow(genres, page);
    }

    public String getRandomAnime() {
        final int totalPages = 25;
        int page = getRandomNumber(totalPages);

        return tmdbApiClient.getRandomAnime(page);
    }

    public PokemonResponse getRandomPokemon() {
        final int totalPokemons = 493;
        int pokemonId = getRandomNumber(totalPokemons);
        String response = pokemonApiClient.getPokemon(USER_AGENT_HEADER, pokemonId);

        return gson.fromJson(response, PokemonResponse.class);
    }

    public PokemonDescriptionResponse getRandomPokemonDescription(int pokemonId) {
        String response = pokemonApiClient.getPokemonDescription(USER_AGENT_HEADER, pokemonId);

        return gson.fromJson(response, PokemonDescriptionResponse.class);
    }

    public PexelsPhotoResponse getRandomPexelsPhoto(String query, int totalPages) {
        final int page = getRandomNumber(totalPages);
        String response = pexelsApiClient.getRandomPexelsPhoto(query, page, pexelsToken);

        return gson.fromJson(response, PexelsPhotoResponse.class);
    }

    public PexelsPhotoResponse.PexelsPhoto getPexelsPhotoForFailedTopic() {
        List<Integer> photosIdList = List.of(1134204, 3601097, 734479, 247314, 247195, 2865901);
        int photoId = photosIdList.get(getRandomNumber(photosIdList.size()));
        String response = pexelsApiClient.getPexelsPhotoById(photoId, pexelsToken);

        return gson.fromJson(response, PexelsPhotoResponse.PexelsPhoto.class);
    }

    private int getRandomNumber(int totalItems) {
        return new Random().ints(1, totalItems).findFirst().orElse(1);
    }
}