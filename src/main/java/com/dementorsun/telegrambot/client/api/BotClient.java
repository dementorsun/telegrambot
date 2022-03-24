package com.dementorsun.telegrambot.client.api;

import com.dementorsun.telegrambot.client.data.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
@AllArgsConstructor
public class BotClient {

    private final NasaApiClient nasaApiClient;
    private final CatApiClient catApiClient;
    private final DogApiClient dogApiClient;
    private final QuoteApiClient quoteApiClient;
    private final TmdbApiClient tmdbApiClient;

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
        String header = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

        return quoteApiClient.getRandomQuote(header);
    }

    public String getRandomMovie() {
        final int totalPages = 161;
        int page = new Random().ints(1, totalPages).findFirst().getAsInt();

        return tmdbApiClient.getRandomMovie(page);
    }

    public String getRandomTvShow() {
        final int totalPages = 149;
        int page = new Random().ints(1, totalPages).findFirst().getAsInt();

        return tmdbApiClient.getRandomTvShow(page);
    }
}