package com.dementorsun.telegrambot.utilities;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * Utility class for serializing and deserializing object.
 */
@Component
@RequiredArgsConstructor
public class ObjectParser {

    private final Gson gson;

    public String parseFromObject(Object object) {
        return gson.toJson(object);
    }

    public <T> T parseToObject(String json, Type type) {
        return gson.fromJson(json, type);
    }
}