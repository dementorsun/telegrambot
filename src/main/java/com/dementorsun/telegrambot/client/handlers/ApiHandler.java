package com.dementorsun.telegrambot.client.handlers;

import com.dementorsun.telegrambot.client.api.BotClient;
import com.dementorsun.telegrambot.client.dto.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
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
            String explanation = Stream.of(nasaApodResponse.getExplanation().split("\\.")).limit(3).collect(Collectors.joining(".")) + ".";
            String caption = String.format("\uD83E\uDE90 *Астрономічне фото дня*\n_%s_\n%s", nasaApodResponse.getTitle(),
                                                                                             explanation);

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "\uD83E\uDE90 *Астрономічне фото дня*");
        }

        return sendPhoto;
    }

    public SendPhoto createRandomCatSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            List<RandomCatResponse> randomCatResponse = gson.fromJson(botClient.getRandomCuteCat(), CAT_TYPE_TOKEN);
            InputFile photo = new InputFile(randomCatResponse.get(0).getUrl());
            String caption = "\uD83D\uDC08 *Кіт дня*\n" + randomCatResponse.get(0).getBreeds().get(0).getName();

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "\uD83D\uDC08 *Кіт дня*");
        }

        return sendPhoto;
    }

    public SendPhoto createRandomDogSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            List<RandomDogResponse> randomDogResponse = gson.fromJson(botClient.getRandomCuteDog(), DOG_TYPE_TOKEN);
            InputFile photo = new InputFile(randomDogResponse.get(0).getUrl());
            String caption = "\uD83D\uDC15 *Пес дня*\n" + randomDogResponse.get(0).getBreeds().get(0).getName();

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "\uD83D\uDC15 *Пес дня*");
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

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "⛩ *Покемон дня*");
        }

        return sendPhoto;
    }

    public SendMessage createRandomQuoteSendMessage(long chatId) {
        SendMessage sendMessage;

        try {
            RandomQuoteResponse randomQuoteResponse = botClient.getRandomQuote();
            String quote = randomQuoteResponse.getQuoteText();
            String author = randomQuoteResponse.getQuoteAuthor();
            String message = String.format("\uD83E\uDD89 *Мудрість дня*\n\"%s\"\n*%s*", quote, author);

            sendMessage = messageFromApiHandler.generateSendMessage(chatId, message);
        } catch (Exception e) {
            sendMessage = generateFailedSendMessage(chatId, "\uD83E\uDD89 *Мудрість дня*");
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

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "\uD83C\uDFAC *Фільм дня*");
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

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "\uD83D\uDCFA *Серіал дня*");
        }

        return sendPhoto;
    }

    public SendPhoto createRandomAnimeSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            List<TmdbResponse.TmdbItem> animeList = gson.fromJson(botClient.getRandomAnime(), TmdbResponse.class).getResults();
            TmdbResponse.TmdbItem anime = getTmdbItemWithOverview(animeList);
            InputFile photo = new InputFile(tmdbImageUrl + anime.getPosterPath());
            String caption = String.format("\uD83D\uDC7A *Аніме дня*\n*%s(%s)*\n_%s_\n\"%s\"\n_TMDB рейтинг:_ *%s*",
                    anime.getName(),
                    anime.getFirstAirDate().substring(0, 4),
                    anime.getOriginalName(),
                    anime.getOverview(),
                    anime.getVoteAverage());

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "\uD83D\uDC7A *Аніме дня*");
        }

        return sendPhoto;
    }

    public SendPhoto createScenerySendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            PexelsPhotoResponse pexelsPhotoResponse = botClient.getRandomPexelsPhoto("landscape", 3000);
            InputFile photo = new InputFile(pexelsPhotoResponse.getPhotos().get(0).getSrc().getLarge());
            String title = pexelsPhotoResponse.getPhotos().get(0).getAlt();
            String caption = String.format("\uD83D\uDDBC *Пейзаж дня*\n_%s_", title);

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "\uD83D\uDDBC *Пейзаж дня*");
        }

        return sendPhoto;
    }

    public SendPhoto createAnimalsSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            PexelsPhotoResponse pexelsPhotoResponse = botClient.getRandomPexelsPhoto("wild_animals", 3000);
            InputFile photo = new InputFile(pexelsPhotoResponse.getPhotos().get(0).getSrc().getLarge());
            String title = pexelsPhotoResponse.getPhotos().get(0).getAlt();
            String caption = String.format("\uD83E\uDD81 *Дикий звір дня*\n_%s_", title);

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "\uD83E\uDD81 *Дикий звір дня*");
        }

        return sendPhoto;
    }

    public SendPhoto createFlowersSendPhoto(long chatId) {
        SendPhoto sendPhoto;

        try {
            PexelsPhotoResponse pexelsPhotoResponse = botClient.getRandomPexelsPhoto("wild_flowers", 400);
            InputFile photo = new InputFile(pexelsPhotoResponse.getPhotos().get(0).getSrc().getLarge());
            String title = pexelsPhotoResponse.getPhotos().get(0).getAlt();
            String caption = String.format("\uD83C\uDF3C *Квіти дня*\n_%s_", title);

            sendPhoto = messageFromApiHandler.generateSendPhoto(chatId, photo, caption);
        } catch (Exception e) {
            sendPhoto = generateFailedSendPhoto(chatId, "\uD83C\uDF3C *Квіти дня*");
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

    private SendPhoto generateFailedSendPhoto(long chatId, String caption) {
        PexelsPhotoResponse.PexelsPhoto pexelsPhotoResponse = botClient.getPexelsPhotoForFailedTopic();
        InputFile photo = new InputFile(pexelsPhotoResponse.getSrc().getLarge());
        String newCaption = String.format("%s\n_Як то кажуть, самсінг вент ронг. Будемо сподіватися, що завтра все буде ок._", caption);

        return messageFromApiHandler.generateSendPhoto(chatId, photo, newCaption);
    }

    private SendMessage generateFailedSendMessage(long chatId, String message) {
        String newMessage = String.format("%s\n_Як то кажуть, самсінг вент ронг. Будемо сподіватися, що завтра все буде ок._", message);

        return messageFromApiHandler.generateSendMessage(chatId, newMessage);
    }
}