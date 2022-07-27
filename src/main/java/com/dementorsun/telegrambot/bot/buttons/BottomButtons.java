package com.dementorsun.telegrambot.bot.buttons;

import com.dementorsun.telegrambot.enums.BottomButtonsDict;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Deprecated
@Component
@AllArgsConstructor
public class BottomButtons {

    @Deprecated
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
        List<String> bottomKeyboardButtonsRow1 = List.of(BottomButtonsDict.CHANGE_TOPICS_BUTTON.getButtonText(),
                                                         BottomButtonsDict.CHANGE_TIME_BUTTON.getButtonText());
        bottomKeyboardRow1.addAll(bottomKeyboardButtonsRow1);

        KeyboardRow bottomKeyboardRow2 = new KeyboardRow();
        List<String> bottomKeyboardButtonsRow2 = List.of(BottomButtonsDict.SILENCE_MODE_BUTTON.getButtonText());
        bottomKeyboardRow2.addAll(bottomKeyboardButtonsRow2);

        return List.of(bottomKeyboardRow1, bottomKeyboardRow2);
    }
}