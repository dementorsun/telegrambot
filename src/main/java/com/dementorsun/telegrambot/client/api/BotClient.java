package com.dementorsun.telegrambot.client.api;

import com.dementorsun.telegrambot.client.data.*;
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
        final int totalPages = 161;
        int page = new Random().ints(1, totalPages).findFirst().orElse(1);

        return tmdbApiClient.getRandomMovie(page);
    }

    public String getRandomTvShow() {
        final int totalPages = 149;
        int page = new Random().ints(1, totalPages).findFirst().orElse(1);

        return tmdbApiClient.getRandomTvShow(page);
    }

    public PokemonResponse getRandomPokemon() {
        final int totalPokemons = 493;
        int pokemonId = new Random().ints(1, totalPokemons).findFirst().orElse(1);

        return gson.fromJson(pokemonApiClient.getPokemon(USER_AGENT_HEADER, pokemonId), PokemonResponse.class);
    }

    public PokemonDescriptionResponse getRandomPokemonDescription(int pokemonId) {
        return gson.fromJson(pokemonApiClient.getPokemonDescription(USER_AGENT_HEADER, pokemonId), PokemonDescriptionResponse.class);
    }
}