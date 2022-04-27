package com.dementorsun.telegrambot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageButtonsDict {

    NASA_BUTTON("\uD83E\uDE90 Астрономічне фото дня", "\uD83E\uDE90 Астрономічне фото дня ✅", "NASA_TOPIC", "NASA_TOPIC_MARKED", TopicsDict.NASA_TOPIC),
    CAT_BUTTON("\uD83D\uDC08 Кіт дня", "\uD83D\uDC08 Кіт дня ✅", "CAT_TOPIC", "CAT_TOPIC_MARKED", TopicsDict.CAT_TOPIC),
    DOG_BUTTON("\uD83D\uDC15 Пес дня", "\uD83D\uDC15 Пес дня ✅", "DOG_TOPIC", "DOG_TOPIC_MARKED", TopicsDict.DOG_TOPIC),
    POKEMON_BUTTON("⛩ Покемон дня", "⛩ Покемон дня ✅", "POKEMON_TOPIC", "POKEMON_TOPIC_MARKED", TopicsDict.POKEMON_TOPIC),
    QUOTE_BUTTON("\uD83E\uDD89 Мудрість дня", "\uD83E\uDD89 Мудрість дня ✅", "QUOTE_TOPIC", "QUOTE_TOPIC_MARKED", TopicsDict.QUOTE_TOPIC),
    MOVIE_BUTTON("\uD83C\uDFAC Фільм дня", "\uD83C\uDFAC Фільм дня ✅", "MOVIE_TOPIC", "MOVIE_TOPIC_MARKED", TopicsDict.MOVIE_TOPIC),
    TV_SHOW_BUTTON("\uD83D\uDCFA Серіал дня", "\uD83D\uDCFA Серіал дня ✅", "TV_SHOW_TOPIC", "TV_SHOW_TOPIC_MARKED", TopicsDict.TV_SHOW_TOPIC),
    DONE_BUTTON("Готово", "Готово", "TOPICS_DONE", "TOPICS_DONE", null);

    private final String buttonText;
    private final String markedButtonText;
    private final String buttonCallBackData;
    private final String markedButtonCallBackData;
    private final TopicsDict topic;
}