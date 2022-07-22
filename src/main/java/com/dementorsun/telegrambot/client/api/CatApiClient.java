package com.dementorsun.telegrambot.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "cat",
        url = "${cat.host}"
)
public interface CatApiClient {

    @GetMapping(value = "/v1/images/search?has_breeds=true&size=med&api_key=${cat.token}", consumes = MediaType.APPLICATION_JSON_VALUE)
    String getRandomCuteCat();
}