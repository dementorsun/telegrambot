package com.dementorsun.telegrambot.bot.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Random;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

@Component
@AllArgsConstructor
public class MessageHandler {

    private static final String BOT_MESSAGE = "*КоженДеньБот: *";

    public SendMessage setNewMessageToUser(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), String.format("%s_%s_", BOT_MESSAGE, message));
        sendMessage.setParseMode(MARKDOWN);

        return sendMessage;
    }

    public String getInitialDayMessage() {
        List<String> randomMessageList =
                List.of("Ось, трохи відволічись від тіктоку.",
                        "Кожен день одне й теж саме. Кожен день...",
                        "Слава Україні!\uD83C\uDDFA\uD83C\uDDE6",
                        "До вашої уваги пропонується.",
                        "Це знову я, а це знову ти...",
                        "Який сьогодні день? Де я?",
                        "Це ж було вже!©",
                        "Ось, все як домовлялися.",
                        "путін вже помер?",
                        "Мені здається, що я тобі подобаюсь. Та нічого, я не проти.",
                        "Херсон вже звільнили?",
                        "Тобі не здається, що ми наче в пастці?");

        return randomMessageList.get(new Random().nextInt(randomMessageList.size()));
    }
}