package com.dementorsun.telegrambot.client.api;

import com.dementorsun.telegrambot.client.dto.NasaApodResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "nasa",
        url = "${nasa.host}"
)
public interface NasaApiClient {

    @GetMapping(value = "/planetary/apod?api_key=${nasa.token}", consumes = MediaType.APPLICATION_JSON_VALUE)
    NasaApodResponse getNasaApod();
}