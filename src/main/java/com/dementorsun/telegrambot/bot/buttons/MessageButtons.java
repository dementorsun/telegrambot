package com.dementorsun.telegrambot.bot.buttons;

import com.dementorsun.telegrambot.enums.MessageButtonsDict;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.enums.TopicsDict;
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

    public InlineKeyboardMarkup setTopicsButtons(long userId, boolean isNewUser) {
        List<List<InlineKeyboardButton>> messageAllButtons = new ArrayList<>(generateInlineKeyboardButtonList(userId, isNewUser));

        return new InlineKeyboardMarkup(messageAllButtons);
}

    public EditMessageReplyMarkup editTopicsButtons(EditMessageReplyMarkup editedMessage, long userId, boolean isNewUser) {
        InlineKeyboardMarkup editedMessageButtonsMarkup = setTopicsButtons(userId, isNewUser);
        editedMessage.setReplyMarkup(editedMessageButtonsMarkup);

        return editedMessage;
    }

    private String getTopicButtonText(Map<TopicsDict,Boolean> userTopics, MessageButtonsDict botButton) {
        String topicButtonText;
        if (botButton.equals(MessageButtonsDict.DONE_BUTTON)) {
            topicButtonText = botButton.getButtonText();
        }
        else {
            topicButtonText = userTopics.get(botButton.getTopic()) ? botButton.getMarkedButtonText() : botButton.getButtonText();
        }
        return topicButtonText;
    }

    private String getTopicButtonCallBackData(Map<TopicsDict,Boolean> userTopics, MessageButtonsDict botButton) {
        String topicButtonCallBackData;
        if (botButton.equals(MessageButtonsDict.DONE_BUTTON)) {
            topicButtonCallBackData = botButton.getButtonCallBackData();
        } else {
            topicButtonCallBackData = userTopics.get(botButton.getTopic()) ? botButton.getMarkedButtonCallBackData() : botButton.getButtonCallBackData();
        }

        return topicButtonCallBackData;
    }

    private InlineKeyboardButton generateInlineKeyboardButton(long userId, MessageButtonsDict botButton) {
        Map<TopicsDict,Boolean> userTopics = userDataHandler.getUserTopics(userId);

        return InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, botButton))
                .callbackData(getTopicButtonCallBackData(userTopics, botButton))
                .build();
    }

    private List<List<InlineKeyboardButton>> generateInlineKeyboardButtonList(long userId, boolean isNewUser) {
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();

        List.of(MessageButtonsDict.values()).forEach(botButton -> {
            if (!isNewUser && !botButton.equals(MessageButtonsDict.DONE_BUTTON)) {
                inlineKeyboardButtonList.add(List.of(generateInlineKeyboardButton(userId, botButton)));
            } else if (isNewUser) {
                inlineKeyboardButtonList.add(List.of(generateInlineKeyboardButton(userId, botButton)));
            }
        });

        return inlineKeyboardButtonList;
    }
}