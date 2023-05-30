package com.dementorsun.telegrambot.bot.buttons;

import com.dementorsun.telegrambot.bot.dto.TopicButtonCallBackData;
import com.dementorsun.telegrambot.enums.MessageButtonsDict;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.enums.TopicsDict;
import com.google.gson.Gson;
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

    private static final boolean IS_TOPIC_BUTTON = true;
    private static final boolean NO_TOPIC_BUTTON = false;

    private final UserDataHandler userDataHandler;
    private final Gson gson;

    public InlineKeyboardMarkup setTopicsButtons(long userId) {
        boolean isNewUser = userDataHandler.checkIsNewUser(userId);
        List<List<InlineKeyboardButton>> messageAllButtons = new ArrayList<>(generateInlineKeyboardButtonList(userId, isNewUser));

        return new InlineKeyboardMarkup(messageAllButtons);
    }

    public EditMessageReplyMarkup editTopicsButtons(EditMessageReplyMarkup editedMessage, long userId) {
        InlineKeyboardMarkup editedMessageButtonsMarkup = setTopicsButtons(userId);
        editedMessage.setReplyMarkup(editedMessageButtonsMarkup);

        return editedMessage;
    }

    private String getTopicButtonText(Map<TopicsDict, Boolean> userTopics, MessageButtonsDict botButton) {
        String topicButtonText;
        if (MessageButtonsDict.DONE_BUTTON.equals(botButton)) {
            topicButtonText = botButton.getButtonText();
        } else {
            boolean isTopicMarked = userTopics.get(botButton.getTopic());
            topicButtonText = isTopicMarked ? botButton.getButtonText() + " ✅" : botButton.getButtonText();
        }
        return topicButtonText;
    }

    private String getTopicButtonCallBackData(Map<TopicsDict, Boolean> userTopics, MessageButtonsDict botButton) {
        String topicButtonCallBackData;
        if (MessageButtonsDict.DONE_BUTTON.equals(botButton)) {
            topicButtonCallBackData = generateJsonButtonCallBackData(NO_TOPIC_BUTTON, null, false);
        } else {
            boolean isTopicMarked = userTopics.get(botButton.getTopic());
            topicButtonCallBackData = isTopicMarked ? generateJsonButtonCallBackData(IS_TOPIC_BUTTON, botButton.getTopic(), true) :
                    generateJsonButtonCallBackData(IS_TOPIC_BUTTON, botButton.getTopic(), false);
        }

        return topicButtonCallBackData;
    }

    private InlineKeyboardButton generateInlineKeyboardButton(long userId, MessageButtonsDict botButton) {
        Map<TopicsDict, Boolean> userTopics = userDataHandler.getUserTopics(userId);

        return InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, botButton))
                .callbackData(getTopicButtonCallBackData(userTopics, botButton))
                .build();
    }

    private List<List<InlineKeyboardButton>> generateInlineKeyboardButtonList(long userId, boolean isNewUser) {
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();

        List.of(MessageButtonsDict.values()).forEach(botButton -> {
            if (!isNewUser && !MessageButtonsDict.DONE_BUTTON.equals(botButton)) {
                inlineKeyboardButtonList.add(List.of(generateInlineKeyboardButton(userId, botButton)));
            } else if (isNewUser) {
                inlineKeyboardButtonList.add(List.of(generateInlineKeyboardButton(userId, botButton)));
            }
        });

        return inlineKeyboardButtonList;
    }

    private String generateJsonButtonCallBackData(boolean isTopic, TopicsDict topic, boolean isTopicMarked) {
        return gson.toJson(new TopicButtonCallBackData(isTopic, topic, isTopicMarked), TopicButtonCallBackData.class);
    }
}