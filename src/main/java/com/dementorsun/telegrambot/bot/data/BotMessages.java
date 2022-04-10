package com.dementorsun.telegrambot.bot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BotMessages {

    WELCOME_MESSAGE("Вітаю, людино! Я так довго був у небутті, що радий бачити навіть тебе. Кхе-кхе! Вибач, щось в горлі пересохло. Ну що ж, якщо ти вже тут, то обирай!"),
    WELCOME_BACK_MESSAGE("%s, це знову ти!? Ні хвилини спокою..."),
    FINISH_TOPICS_TUTORIAL_MESSAGE("Так собі вибір звичайно. Але хто я такий щоб тебе засуджувати. Тепер введи будь-який час у форматі '23:59'. Без кавичок звичайно..."),
    COMPLETE_TUTORIAL_MESSAGE("Нарешті! Я думав, що це ніколи не закінчиться. Відтепер в твоєму житті настануть зміни, яких ти так довго чекаєш!!! Жартую, тобі просто у обраний тобою час будуть приходити повідомлення. І не вздумай їх не читати, я тебе попередив!"),
    FAIL_TIME_FORMAT_MESSAGE("Ну ти серйозно?! Введи час у форматі '12:45'. Зможеш?"),
    CHANGE_TOPICS_MESSAGE("Хочеш щось змінити? Ну спробуй, не буду тобі заважати."),
    TOPICS_ARE_CHANGED_MESSAGE("Ніби це щось змінить! Жартую, все працює як годинник."),
    TOPIC_DONE_REPEAT_CLICK("Одного разу цілком вистачить. Тримай себе в руках."),
    TOPIC_DONE_NO_CHOSEN_TOPIC_CLICK("Так не піде. Потрібно хоча б щось обрати, такі правила гри."),
    TIME_ARE_CHANGED_MESSAGE("Наче змінив. Скоро дізнаємось..."),
    TIME_CHANGING_MESSAGE("Твій час '%s'. Чекаю новий! Може в тебе навіть вийде з першого разу."),
    UNEXPECTED_ACTION_MESSAGE("Як то кажуть 'Oops, something went wrong'.");

    private String message;
}