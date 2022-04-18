package com.dementorsun.telegrambot.bot.buttons;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
@AllArgsConstructor
public class BottomButtons {

    private static final List<String> BOTTOM_KEYBOARD_ROW1_BUTTONS = List.of("Змінити свої топіки \uD83E\uDDF3", "Змінити час ⏳");
    private static final List<String> BOTTOM_KEYBOARD_ROW2_BUTTONS = List.of("Зупинити цей спам ❌");

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
        KeyboardRow bottomKeyboardRow1 = new KeyboardRow();
        bottomKeyboardRow1.addAll(BOTTOM_KEYBOARD_ROW1_BUTTONS);

        KeyboardRow bottomKeyboardRow2 = new KeyboardRow();
        bottomKeyboardRow2.addAll(BOTTOM_KEYBOARD_ROW2_BUTTONS);

        return List.of(bottomKeyboardRow1, bottomKeyboardRow2);
    }
}