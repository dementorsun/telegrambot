package com.dementorsun.telegrambot.client.api;

import com.dementorsun.telegrambot.client.dto.*;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
@AllArgsConstructor
public class BotClient {

    private static final String USER_AGENT_HEADER = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

    private final NasaApiClient nasaApiClient;
    private final CatApiClient catApiClient;
    private final DogApiClient dogApiClient;
    private final PokemonApiClient pokemonApiClient;
    private final QuoteApiClient quoteApiClient;
    private final TmdbApiClient tmdbApiClient;
    private final Gson gson;

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
        final int totalPages = 47;
        int page = getRandomResponseItem(totalPages);

        return tmdbApiClient.getRandomMovie(page);
    }

    public String getRandomTvShow() {
        final int totalPages = 68;
        int page = getRandomResponseItem(totalPages);

        return tmdbApiClient.getRandomTvShow(page);
    }

    public PokemonResponse getRandomPokemon() {
        final int totalPokemons = 493;
        int pokemonId = getRandomResponseItem(totalPokemons);
        String response = pokemonApiClient.getPokemon(USER_AGENT_HEADER, pokemonId);

        return gson.fromJson(response, PokemonResponse.class);
    }

    public PokemonDescriptionResponse getRandomPokemonDescription(int pokemonId) {
        String response = pokemonApiClient.getPokemonDescription(USER_AGENT_HEADER, pokemonId);

        return gson.fromJson(response, PokemonDescriptionResponse.class);
    }

    private int getRandomResponseItem(int totalItems) {
        return new Random().ints(1, totalItems).findFirst().orElse(1);
    }
}