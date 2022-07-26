package com.dementorsun.telegrambot.client.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PexelsPhotoResponse {

    List<PexelsPhoto> photos;

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    public static class PexelsPhoto {
        PhotoSrc src;
        String alt;

        @FieldDefaults(level = AccessLevel.PRIVATE)
        @Getter
        public static class PhotoSrc {
            String large;
        }
    }
}