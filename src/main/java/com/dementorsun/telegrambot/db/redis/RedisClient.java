package com.dementorsun.telegrambot.db.redis;

import com.dementorsun.telegrambot.db.dto.BotUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RedisClient {

    private final BotUserRepository botUserRepository;

    public void saveUserData(BotUser botUser) {
        botUserRepository.save(botUser);
    }

    public BotUser getUserData(long userId) {
        return botUserRepository.findById(userId).orElse(null);
    }

    public List<BotUser> getAllUsersData() {
        List<BotUser> usersData = new ArrayList<>();
        botUserRepository.findAll().forEach(usersData::add);

        return usersData;
    }
}