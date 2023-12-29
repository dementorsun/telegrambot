package com.dementorsun.telegrambot.topic;

import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.topic.enums.TopicButtonsDict;
import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.dementorsun.telegrambot.topic.enums.TopicButtonsDict.DONE_BUTTON;

/**
 * Class for generating bot topic buttons into message area.
 */

@Component
@RequiredArgsConstructor
public class TopicButtonHandler {

    private final UserDataHandler userDataHandler;
    private final TopicButtonParameterHandler topicButtonParameterHandler;

    /**
     * Method generates {@link EditMessageReplyMarkup} based on {@link Update} call back query data after click on topic button.
     * @param userId provides user id for further handling topic button click.
     * @param update provides call back query for further handling topic button click.
     * @return {@link EditMessageReplyMarkup} to further send it to TelegramBot API for handling topic button click.
     */
    public EditMessageReplyMarkup editTopicsButtons(long userId, Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        InlineKeyboardMarkup editedMessageButtonsMarkup = setTopicsButtons(userId);

        return EditMessageReplyMarkup.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .replyMarkup(editedMessageButtonsMarkup)
                .build();
    }

    /**
     * Method generates {@link InlineKeyboardMarkup} which reflect topic buttons.
     * @param userId provides user id for further generating topics buttons.
     * @return {@link InlineKeyboardMarkup} which will be used for displaying topics buttons.
     */
    public InlineKeyboardMarkup setTopicsButtons(long userId) {
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();
        Map<TopicsDict, Boolean> userTopics = userDataHandler.getUserTopics(userId);
        List<TopicButtonsDict> topicsButtons = new ArrayList<>(Arrays.asList(TopicButtonsDict.values()));
        boolean isDoneButtonClicked = userDataHandler.getIsDoneButtonClickedForUser(userId);
        boolean isAnyTopicActive = userDataHandler.getIsAnyTopicActiveForUser(userId);

        //If no topics are chosen or Done button hasn't been clicked yet, then remove Done button from buttons list
        if (!isAnyTopicActive || isDoneButtonClicked) {
            topicsButtons.remove(DONE_BUTTON);
        }

        topicsButtons.forEach(botButton -> {
            InlineKeyboardButton topicButton = InlineKeyboardButton.builder()
                    .text(topicButtonParameterHandler.getTopicButtonText(userTopics, botButton))
                    .callbackData(topicButtonParameterHandler.getTopicButtonCallBackData(userTopics, botButton))
                    .build();
            inlineKeyboardButtonList.add(List.of(topicButton));
        });

        List<List<InlineKeyboardButton>> messageAllButtons = new ArrayList<>(inlineKeyboardButtonList);

        return new InlineKeyboardMarkup(messageAllButtons);
    }
}