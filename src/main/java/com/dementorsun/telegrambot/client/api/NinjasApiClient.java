package com.dementorsun.telegrambot.client.api;

import com.dementorsun.telegrambot.client.dto.NinjasQuoteEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(
        name = "quote",
        url = "${ninjas.host}",
        path = "/v1"
)
@RequestMapping(headers = "X-Api-Key=${ninjas.token}")
public interface NinjasApiClient {

    @GetMapping(value = "/quotes")
    List<NinjasQuoteEntity> getRandomQuotes();
}