package com.dementorsun.telegrambot.topic;

import com.dementorsun.telegrambot.topic.button.*;
import com.dementorsun.telegrambot.topic.enums.TopicButtonsDict;
import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

import static com.dementorsun.telegrambot.topic.enums.TopicsDict.*;

/**
 * Container for {@link TopicButtonsDict} topics buttons which is using for defying which topic button user clicked.
 */
@Component
public class TopicsButtonContainer {

    private final Map<TopicsDict, TopicButton> topicButtonsContainer;
    private final DoneTopicButton doneTopicButton;

    public TopicsButtonContainer(NasaTopicButton nasaTopicButton,
                                 NatureTopicButton natureTopicButton,
                                 AnimalsTopicButton animalsTopicButton,
                                 ForestTopicButton forestTopicButton,
                                 CatTopicButton catTopicButton,
                                 DogTopicButton dogTopicButton,
                                 PokemonTopicButton pokemonTopicButton,
                                 MovieTopicButton movieTopicButton,
                                 TvShowTopicButton tvShowTopicButton,
                                 AnimeTopicButton animeTopicButton,
                                 QuoteTopicButton quoteTopicButton,
                                 DoneTopicButton doneTopicButton) {
        this.doneTopicButton = doneTopicButton;

        topicButtonsContainer = new EnumMap<>(TopicsDict.class);
        topicButtonsContainer.put(NASA_TOPIC, nasaTopicButton);
        topicButtonsContainer.put(NATURE_TOPIC, natureTopicButton);
        topicButtonsContainer.put(ANIMALS_TOPIC, animalsTopicButton);
        topicButtonsContainer.put(FOREST_TOPIC, forestTopicButton);
        topicButtonsContainer.put(CAT_TOPIC, catTopicButton);
        topicButtonsContainer.put(DOG_TOPIC, dogTopicButton);
        topicButtonsContainer.put(POKEMON_TOPIC, pokemonTopicButton);
        topicButtonsContainer.put(MOVIE_TOPIC, movieTopicButton);
        topicButtonsContainer.put(TV_SHOW_TOPIC, tvShowTopicButton);
        topicButtonsContainer.put(ANIME_TOPIC, animeTopicButton);
        topicButtonsContainer.put(QUOTE_TOPIC, quoteTopicButton);
    }

    /**
     * Method return {@link TopicButton} object for further execution topic button logic.
     * @param topicDict provides enum {@link TopicsDict} to connect with topic button.
     * @return {@link TopicButton} object to execute topic button logic.
     */
    public TopicButton retrieveTopicButton(TopicsDict topicDict) {
        return topicButtonsContainer.getOrDefault(topicDict, doneTopicButton);
    }
}