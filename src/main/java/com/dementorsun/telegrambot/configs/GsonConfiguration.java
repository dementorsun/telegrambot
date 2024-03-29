package com.dementorsun.telegrambot.configs;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GsonConfiguration {

    @Bean
    public Gson gson() {
        return new Gson();
    }
}