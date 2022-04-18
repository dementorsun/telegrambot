package com.dementorsun.telegrambot.bot.buttons;

import com.dementorsun.telegrambot.bot.data.BotButtons;
import com.dementorsun.telegrambot.db.UserDataHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class MessageButtons {

    private final UserDataHandler userDataHandler;

    public InlineKeyboardMarkup setTopicsButtons(long userId) {
        List<List<InlineKeyboardButton>> messageAllButtons = new ArrayList<>(generateInlineKeyboardButtonList(userId));

        return new InlineKeyboardMarkup(messageAllButtons);
}

    public EditMessageReplyMarkup editTopicsButtons(EditMessageReplyMarkup editedMessage, long userId) {
        InlineKeyboardMarkup editedMessageButtonsMarkup = setTopicsButtons(userId);
        editedMessage.setReplyMarkup(editedMessageButtonsMarkup);

        return editedMessage;
    }

    private String getTopicButtonText(Map<BotButtons,Boolean> userTopics, BotButtons botButton) {
        return userTopics.get(botButton) ? botButton.getMarkedButtonText() : botButton.getButtonText();
    }

    private String getTopicButtonCallBackData(Map<BotButtons,Boolean> userTopics, BotButtons botButton) {
        return userTopics.get(botButton) ? botButton.getMarkedButtonCallBackData() : botButton.getButtonCallBackData();
    }

    private InlineKeyboardButton generateInlineKeyboardButton(long userId, BotButtons botButton) {
        Map<BotButtons,Boolean> userTopics = userDataHandler.getUserTopics(userId);

        return InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, botButton))
                .callbackData(getTopicButtonCallBackData(userTopics, botButton))
                .build();
    }

    private List<List<InlineKeyboardButton>> generateInlineKeyboardButtonList(long userId) {
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();

        for (BotButtons botButton : BotButtons.values()) {
            if (botButton.equals(BotButtons.TOPICS_DONE)) {
                InlineKeyboardButton topicsDoneButton = InlineKeyboardButton.builder()
                        .text(BotButtons.TOPICS_DONE.getButtonText())
                        .callbackData(BotButtons.TOPICS_DONE.getButtonCallBackData())
                        .build();
                inlineKeyboardButtonList.add(List.of(topicsDoneButton));
            } else {
                inlineKeyboardButtonList.add(List.of(generateInlineKeyboardButton(userId, botButton)));
            }
        }

        return inlineKeyboardButtonList;
    }
}