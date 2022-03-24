package com.dementorsun.telegrambot.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "dog",
        url = "${dog.host}"
)
public interface DogApiClient {

    @GetMapping(value = "/v1/images/search?has_breeds=true&size=med", consumes = MediaType.APPLICATION_JSON_VALUE)
    String getRandomCuteDog();
}