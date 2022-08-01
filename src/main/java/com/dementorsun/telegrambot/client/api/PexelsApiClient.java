package com.dementorsun.telegrambot.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "pexels",
        url = "${pexels.host}"
)
public interface PexelsApiClient {

    @GetMapping(value = "/v1/search?size=large&per_page=1",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String getRandomPexelsPhoto(@RequestParam("query") String query,
                                @RequestParam("page") int page,
                                @RequestHeader("Authorization") String token);

    @GetMapping(value = "/v1/photos/{photoId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String getPexelsPhotoById(@PathVariable("photoId") int photoId,
                              @RequestHeader("Authorization") String token);
}