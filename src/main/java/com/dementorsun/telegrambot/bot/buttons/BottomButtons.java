package com.dementorsun.telegrambot.bot.buttons;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class BottomButtons {

    public void setBottomButtons(SendMessage replyMessage) {
        ReplyKeyboardMarkup bottomKeyboard = createBottomButtonsMarkup();

        List<KeyboardRow> bottomKeyboardRows = createBottomButtons();
        bottomKeyboard.setKeyboard(bottomKeyboardRows);

        replyMessage.setReplyMarkup(bottomKeyboard);
    }

    private ReplyKeyboardMarkup createBottomButtonsMarkup() {
        ReplyKeyboardMarkup bottomKeyboard = new ReplyKeyboardMarkup();
        bottomKeyboard.setResizeKeyboard(true);
        bottomKeyboard.setOneTimeKeyboard(true);

        return bottomKeyboard;
    }

    private List<KeyboardRow> createBottomButtons() {
        List<KeyboardRow> bottomKeyboardRows = new ArrayList<>();

        KeyboardRow bottomKeyboardRow1 = new KeyboardRow();
        bottomKeyboardRow1.add("Змінити свої топіки \uD83E\uDDF3");
        bottomKeyboardRow1.add("Змінити час ⏳");

        KeyboardRow bottomKeyboardRow2 = new KeyboardRow();
        bottomKeyboardRow2.add("Зупинити цей спам ❌");

        bottomKeyboardRows.add(bottomKeyboardRow1);
        bottomKeyboardRows.add(bottomKeyboardRow2);

        return bottomKeyboardRows;
    }
}