package com.dementorsun.telegrambot.bot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BotButtons {

    NASA_TOPIC("\uD83E\uDE90 Астрономічне фото дня", "\uD83E\uDE90 Астрономічне фото дня ✅", "NASA_TOPIC", "NASA_TOPIC_MARKED"),
    CAT_TOPIC("\uD83D\uDC08 Кіт дня", "\uD83D\uDC08 Кіт дня ✅", "CAT_TOPIC", "CAT_TOPIC_MARKED"),
    DOG_TOPIC("\uD83D\uDC15 Пес дня", "\uD83D\uDC15 Пес дня ✅", "DOG_TOPIC", "DOG_TOPIC_MARKED"),
    POKEMON_TOPIC("⛩ Покемон дня", "⛩ Покемон дня ✅", "POKEMON_TOPIC", "POKEMON_TOPIC_MARKED"),
    QUOTE_TOPIC("\uD83E\uDD89 Мудрість дня", "\uD83E\uDD89 Мудрість дня ✅", "QUOTE_TOPIC", "QUOTE_TOPIC_MARKED"),
    MOVIE_TOPIC("\uD83C\uDFAC Фільм дня", "\uD83C\uDFAC Фільм дня ✅", "MOVIE_TOPIC", "MOVIE_TOPIC_MARKED"),
    TV_SHOW_TOPIC("\uD83D\uDCFA Серіал дня", "\uD83D\uDCFA Серіал дня ✅", "TV_SHOW_TOPIC", "TV_SHOW_TOPIC_MARKED"),
    TOPICS_DONE("Готово", null, "TOPICS_DONE", null);

    private final String buttonText;
    private final String markedButtonText;
    private final String buttonCallBackData;
    private final String markedButtonCallBackData;
}