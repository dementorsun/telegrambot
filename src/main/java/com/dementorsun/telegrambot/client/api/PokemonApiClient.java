package com.dementorsun.telegrambot.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "pokemon",
        url = "${pokemon.host}",
        path = "/api/v2"
)

public interface PokemonApiClient {

    @GetMapping(value = "/pokemon/{pokemonId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    String getPokemon(@RequestHeader("user-agent") String agent,
                      @PathVariable("pokemonId") int pokemonId);

    @GetMapping(value = "/pokemon-species/{pokemonId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    String getPokemonDescription(@RequestHeader("user-agent") String agent,
                                 @PathVariable("pokemonId") int pokemonId);
}