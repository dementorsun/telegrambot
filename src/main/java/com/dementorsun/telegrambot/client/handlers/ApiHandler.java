package com.dementorsun.telegrambot.client.handlers;

import com.dementorsun.telegrambot.client.api.BotClient;
import com.dementorsun.telegrambot.client.data.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApiHandler {

    private static final Type CAT_TYPE_TOKEN = new TypeToken<List<RandomCatResponse>>(){}.getType();
    private static final Type DOG_TYPE_TOKEN = new TypeToken<List<RandomDogResponse>>(){}.getType();

    private final Gson gson;
    private final BotClient botClient;
    private final MessageFromApiHandler messageFromApiHandler;

    @Value("${tmdb.image.url}")
    private String tmdbImageUrl;

    public SendPhoto createNasaApodSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            NasaApodResponse nasaApodResponse = botClient.getNasaApod();
            InputFile photo = new InputFile(nasaApodResponse.getUrl());
            String caption = "\uD83E\uDE90 *Астрономічне фото дня*\n" + nasaApodResponse.getTitle();

            sendPhoto = messageFromApiHandler.createSendPhoto(chatId, photo, caption);

        } catch (NullPointerException e) {
            sendPhoto = null;
        }

        return sendPhoto;
    }

    public SendPhoto createRandomCatSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            List<RandomCatResponse> randomCatResponse = gson.fromJson(botClient.getRandomCuteCat(), CAT_TYPE_TOKEN);
            InputFile photo = new InputFile(randomCatResponse.get(0).getUrl());
            String caption = "\uD83D\uDC08 *Кіт дня*\n" + randomCatResponse.get(0).getBreeds().get(0).getName();

            sendPhoto = messageFromApiHandler.createSendPhoto(chatId, photo, caption);

        } catch (NullPointerException e) {
            sendPhoto = null;
        }

        return sendPhoto;
    }

    public SendPhoto createRandomDogSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            List<RandomDogResponse> randomDogResponse = gson.fromJson(botClient.getRandomCuteDog(), DOG_TYPE_TOKEN);
            InputFile photo = new InputFile(randomDogResponse.get(0).getUrl());
            String caption = "\uD83D\uDC15 *Пес дня*\n" + randomDogResponse.get(0).getBreeds().get(0).getName();

            sendPhoto = messageFromApiHandler.createSendPhoto(chatId, photo, caption);

        } catch (NullPointerException e) {
            sendPhoto = null;
        }

        return sendPhoto;
    }

    public SendPhoto createRandomPokemonSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            PokemonResponse pokemonResponse = botClient.getRandomPokemon();
            InputFile photo = new InputFile(pokemonResponse.getSprites().getOther().getOfficialArtwork().getFrontDefault());
            int pokemonId = pokemonResponse.getId();
            PokemonDescriptionResponse pokemonDescriptionResponse = botClient.getRandomPokemonDescription(pokemonId);
            String caption = getPokemonCaption(pokemonResponse, pokemonDescriptionResponse);

            sendPhoto = messageFromApiHandler.createSendPhoto(chatId, photo, caption);

        } catch (NullPointerException e) {
            sendPhoto = null;
        }

        return sendPhoto;
    }

    public SendMessage createRandomQuoteSendMessage(long chatId) {
        SendMessage sendMessage;

        try {
            RandomQuoteResponse randomQuoteResponse = botClient.getRandomQuote();
            String quote = randomQuoteResponse.getQuoteText();
            String author = randomQuoteResponse.getQuoteAuthor();

            sendMessage = messageFromApiHandler.createSendMessage(chatId,
                    String.format("\uD83E\uDD89 *Мудрість дня*\n\"%s\"\n*%s*", quote, author));

        } catch (NullPointerException e) {
            sendMessage = null;
        }

        return sendMessage;
    }

    public SendPhoto createRandomMovieSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            List<TmdbResponse.TmdbItem> movieList = gson.fromJson(botClient.getRandomMovie(), TmdbResponse.class).getResults();
            TmdbResponse.TmdbItem movie = getTmdbItemWithOverview(movieList);

            InputFile photo = new InputFile(tmdbImageUrl + movie.getPosterPath());
            String caption = String.format("\uD83C\uDFAC *Фільм дня*\n*%s(%s)*\n_%s_\n\"%s\"\n_TMDB рейтинг:_ *%s*",
                                            movie.getTitle(),
                                            movie.getReleaseDate().substring(0, 4),
                                            movie.getOriginalTitle(),
                                            movie.getOverview(),
                                            movie.getVoteAverage());
            sendPhoto = messageFromApiHandler.createSendPhoto(chatId, photo, caption);

        } catch (NullPointerException e) {
            sendPhoto = null;
        }

        return sendPhoto;
    }

    public SendPhoto createRandomTvShowSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            List<TmdbResponse.TmdbItem> tvShowList = gson.fromJson(botClient.getRandomTvShow(), TmdbResponse.class).getResults();
            TmdbResponse.TmdbItem tvShow = getTmdbItemWithOverview(tvShowList);

            InputFile photo = new InputFile(tmdbImageUrl + tvShow.getPosterPath());
            String caption = String.format("\uD83D\uDCFA *Серіал дня*\n*%s(%s)*\n_%s_\n\"%s\"\n_TMDB рейтинг:_ *%s*",
                                            tvShow.getName(),
                                            tvShow.getFirstAirDate().substring(0, 4),
                                            tvShow.getOriginalName(),
                                            tvShow.getOverview(),
                                            tvShow.getVoteAverage());
            sendPhoto = messageFromApiHandler.createSendPhoto(chatId, photo, caption);

        } catch (NullPointerException e) {
            sendPhoto = null;
        }

        return sendPhoto;
    }

    private TmdbResponse.TmdbItem getTmdbItemWithOverview(List<TmdbResponse.TmdbItem> movieList) {
        TmdbResponse.TmdbItem tmdbItem;
        List<TmdbResponse.TmdbItem> movieListWithOverview = movieList.stream()
                .filter(item -> !item.getOverview().isEmpty())
                .collect(Collectors.toList());

        if (movieListWithOverview.isEmpty()) {
            tmdbItem = movieList.stream().findAny().orElse(movieList.get(0));
            tmdbItem.setOverview("На жаль, опису немає (");
        } else {
            tmdbItem = movieListWithOverview.get(new Random().nextInt(movieListWithOverview.size()));
        }

        return tmdbItem;
    }

    private String getPokemonCaption(PokemonResponse pokemonResponse, PokemonDescriptionResponse pokemonDescriptionResponse) {
        String pokemonNameTemp = pokemonResponse.getName();
        String pokemonName = pokemonNameTemp.substring(0, 1).toUpperCase() + pokemonNameTemp.substring(1);

        String pokemonTypes = pokemonResponse.getTypes()
                .stream()
                .map(PokemonResponse.PokemonType::getType)
                .map(PokemonResponse.PokemonType.Type::getName)
                .collect(Collectors.joining(", "));

        String pokemonDescription = pokemonDescriptionResponse.getPokemonDescriptions()
                .stream()
                .filter(description -> description.getLanguage().getName().equals("en"))
                .map(PokemonDescriptionResponse.PokemonDescription::getDescription)
                .findFirst().orElse("На жаль, опису немає(").replace("\n", " ");

        return String.format("⛩ *Покемон дня*\n*Назва:* %s\n*Тип:* %s\n*Опис:* %s", pokemonName, pokemonTypes, pokemonDescription);
    }
}