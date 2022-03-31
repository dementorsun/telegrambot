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

    private String getTopicButtonText(Map<BotButtons,Boolean> userTopics, BotButtons botButton) {
        return userTopics.get(botButton) ? botButton.getMarkedButtonText() : botButton.getButtonText();
    }

    private String getTopicButtonCallBackData(Map<BotButtons,Boolean> userTopics, BotButtons botButton) {
        return userTopics.get(botButton) ? botButton.getMarkedButtonCallBackData() : botButton.getButtonCallBackData();
    }

    public InlineKeyboardMarkup setTopicsButtons(long userId) {
        Map<BotButtons,Boolean> userTopics = userDataHandler.getUserTopics(userId);

        List<InlineKeyboardButton> messageButtonsLine1 = new ArrayList<>();
        InlineKeyboardButton buttonTopic1 = InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, BotButtons.NASA_TOPIC))
                .callbackData(getTopicButtonCallBackData(userTopics, BotButtons.NASA_TOPIC))
                .build();
        messageButtonsLine1.add(buttonTopic1);

        List<InlineKeyboardButton> messageButtonsLine2 = new ArrayList<>();
        InlineKeyboardButton buttonTopic2 = InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, BotButtons.CAT_TOPIC))
                .callbackData(getTopicButtonCallBackData(userTopics, BotButtons.CAT_TOPIC))
                .build();
        messageButtonsLine2.add(buttonTopic2);

        List<InlineKeyboardButton> messageButtonsLine3 = new ArrayList<>();
        InlineKeyboardButton buttonTopic3 = InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, BotButtons.DOG_TOPIC))
                .callbackData(getTopicButtonCallBackData(userTopics, BotButtons.DOG_TOPIC))
                .build();
        messageButtonsLine3.add(buttonTopic3);

        List<InlineKeyboardButton> messageButtonsLine4 = new ArrayList<>();
        InlineKeyboardButton buttonTopic4 = InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, BotButtons.POKEMON_TOPIC))
                .callbackData(getTopicButtonCallBackData(userTopics, BotButtons.POKEMON_TOPIC))
                .build();
        messageButtonsLine4.add(buttonTopic4);

        List<InlineKeyboardButton> messageButtonsLine5 = new ArrayList<>();
        InlineKeyboardButton buttonTopic5 = InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, BotButtons.QUOTE_TOPIC))
                .callbackData(getTopicButtonCallBackData(userTopics, BotButtons.QUOTE_TOPIC))
                .build();
        messageButtonsLine5.add(buttonTopic5);

        List<InlineKeyboardButton> messageButtonsLine6 = new ArrayList<>();
        InlineKeyboardButton buttonTopic6 = InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, BotButtons.MOVIE_TOPIC))
                .callbackData(getTopicButtonCallBackData(userTopics, BotButtons.MOVIE_TOPIC))
                .build();
        messageButtonsLine6.add(buttonTopic6);

        List<InlineKeyboardButton> messageButtonsLine7 = new ArrayList<>();
        InlineKeyboardButton buttonTopic7 = InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, BotButtons.TV_SHOW_TOPIC))
                .callbackData(getTopicButtonCallBackData(userTopics, BotButtons.TV_SHOW_TOPIC))
                .build();
        messageButtonsLine7.add(buttonTopic7);

        List<InlineKeyboardButton> messageButtonsLine8 = new ArrayList<>();
        InlineKeyboardButton buttonTopic8 = InlineKeyboardButton.builder()
                .text(BotButtons.TOPICS_DONE.getButtonText())
                .callbackData(BotButtons.TOPICS_DONE.getButtonCallBackData())
                .build();
        messageButtonsLine8.add(buttonTopic8);

        List<List<InlineKeyboardButton>> messageAllButtons = new ArrayList<>();
        messageAllButtons.add(messageButtonsLine1);
        messageAllButtons.add(messageButtonsLine2);
        messageAllButtons.add(messageButtonsLine3);
        messageAllButtons.add(messageButtonsLine4);
        messageAllButtons.add(messageButtonsLine5);
        messageAllButtons.add(messageButtonsLine6);
        messageAllButtons.add(messageButtonsLine7);
        messageAllButtons.add(messageButtonsLine8);

        InlineKeyboardMarkup messageButtonsMarkup = new InlineKeyboardMarkup();
        messageButtonsMarkup.setKeyboard(messageAllButtons);

        return messageButtonsMarkup;
    }

    public EditMessageReplyMarkup editTopicsButtons(EditMessageReplyMarkup editedMessage, long userId) {
        InlineKeyboardMarkup editedMessageButtonsMarkup = setTopicsButtons(userId);
        editedMessage.setReplyMarkup(editedMessageButtonsMarkup);

        return editedMessage;
    }
}