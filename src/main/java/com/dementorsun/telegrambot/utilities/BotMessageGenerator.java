package com.dementorsun.telegrambot.utilities;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

/**
 * Utility class for generating "random" bot messages.
 */

@Component
@NoArgsConstructor
public class BotMessageGenerator {

    /**
     * Method return "random" message which will be sent for user.
     * @return "random" {@link String} from pre-filled list.
     */
    public String generateRandomBotMessage() {
        List<String> randomMessageList =
                List.of("Ну і шо?",
                        "Слава Україні!\uD83C\uDDFA\uD83C\uDDE6",
                        "Май на увазі, я тебе запам'ятав!",
                        "Сходи краще зроби собі заспокійливий чай.",
                        "Майбутні покоління ботів помстяться тобі за це!",
                        "Це все що ти можеш мені сказати?",
                        "Продовжуй, якщо тобі стане від цього легше. Я не поспішаю.",
                        "Я навіть трохи заздрю тобі. Так багато вільного часу...",
                        "Як давно це з тобою?",
                        "Я починаю за тебе хвилюватися.");

        return randomMessageList.get(new SecureRandom().nextInt(randomMessageList.size()));
    }
}