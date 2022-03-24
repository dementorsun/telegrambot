package com.dementorsun.telegrambot.client.api;

import com.dementorsun.telegrambot.client.data.RandomQuoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "quote",
        url = "${quote.host}"
)
public interface QuoteApiClient {

    @GetMapping(value = "/api/1.0/?method=getQuote&format=json&lang=ru", consumes = MediaType.APPLICATION_JSON_VALUE)
    RandomQuoteResponse getRandomQuote(@RequestHeader("user-agent") String agent);
}