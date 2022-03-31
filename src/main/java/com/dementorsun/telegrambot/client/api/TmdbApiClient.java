package com.dementorsun.telegrambot.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "tmdb",
        url = "${tmdb.host}"
)
@RequestMapping("/3/discover")

public interface TmdbApiClient {

    @GetMapping(value = "/movie?api_key=${tmdb.token}&language=uk-UA&vote_count.gte=100&vote_average.gte=7&primary_release_date.gte=1975&region=UA,US",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String getRandomMovie(@RequestParam("page") int page);

    @GetMapping(value = "/tv?api_key=${tmdb.token}&language=uk-UA&vote_count.gte=50&vote_average.gte=7&watch_region=US",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String getRandomTvShow(@RequestParam("page") int page);
}