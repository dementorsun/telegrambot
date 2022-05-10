package com.dementorsun.telegrambot.db.redis;

import com.dementorsun.telegrambot.db.dto.BotUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotUserRepository extends CrudRepository<BotUser, Long> {
}