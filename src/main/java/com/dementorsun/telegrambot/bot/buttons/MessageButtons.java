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

    public InlineKeyboardMarkup setTopicsButtons(long userId) {
        List<List<InlineKeyboardButton>> messageAllButtons = new ArrayList<>(generateInlineKeyboardButtonList(userId));

        return new InlineKeyboardMarkup(messageAllButtons);
}

    public EditMessageReplyMarkup editTopicsButtons(EditMessageReplyMarkup editedMessage, long userId) {
        InlineKeyboardMarkup editedMessageButtonsMarkup = setTopicsButtons(userId);
        editedMessage.setReplyMarkup(editedMessageButtonsMarkup);

        return editedMessage;
    }

    private String getTopicButtonText(Map<TopicsDict,Boolean> userTopics, MessageButtonsDict botButton) {
        return userTopics.get(botButton.getTopic()) ? botButton.getMarkedButtonText() : botButton.getButtonText();
    }

    private String getTopicButtonCallBackData(Map<TopicsDict,Boolean> userTopics, MessageButtonsDict botButton) {
        return userTopics.get(botButton.getTopic()) ? botButton.getMarkedButtonCallBackData() : botButton.getButtonCallBackData();
    }

    private InlineKeyboardButton generateInlineKeyboardButton(long userId, MessageButtonsDict botButton) {
        Map<TopicsDict,Boolean> userTopics = userDataHandler.getUserTopics(userId);

        return InlineKeyboardButton.builder()
                .text(getTopicButtonText(userTopics, botButton))
                .callbackData(getTopicButtonCallBackData(userTopics, botButton))
                .build();
    }

    private List<List<InlineKeyboardButton>> generateInlineKeyboardButtonList(long userId) {
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();

        List.of(MessageButtonsDict.values()).forEach(botButton -> {
            if (botButton.equals(MessageButtonsDict.DONE_BUTTON)) {
                InlineKeyboardButton topicsDoneButton = InlineKeyboardButton.builder()
                        .text(MessageButtonsDict.DONE_BUTTON.getButtonText())
                        .callbackData(MessageButtonsDict.DONE_BUTTON.getButtonCallBackData())
                        .build();
                inlineKeyboardButtonList.add(List.of(topicsDoneButton));
            } else {
                inlineKeyboardButtonList.add(List.of(generateInlineKeyboardButton(userId, botButton)));
            }
        });

        return inlineKeyboardButtonList;
    }
}