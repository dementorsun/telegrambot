package com.dementorsun.telegrambot.client.api;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "pexels",
        url = "${pexels.host}"
)
@Headers("Authorization: ${pexels.token}")
public interface PexelsApiClient {

    @GetMapping(value = "/v1/search?size=large&per_page=1",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String getRandomPexelsPhoto(@RequestParam("query") String query,
                                @RequestParam("page") int page);
}