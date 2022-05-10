package com.dementorsun.telegrambot.db.file;

import com.dementorsun.telegrambot.db.dto.BotUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Deprecated
@Component
@Slf4j
@AllArgsConstructor
public class FileHandler {

    private static final String USER_DB_FILE_PATH = "src/main/resources/userDB";
    private static final Type BOT_USER_TYPE_TOKEN = new TypeToken<List<BotUser>>(){}.getType();

    private final Gson gson;

    public List<BotUser> getUsersFromDbFile() {
        List<BotUser> usersData = new ArrayList<>();

        try {
            List<BotUser> tempUsersData = new ArrayList<>();
            JsonReader jsonReader = new JsonReader(new FileReader(USER_DB_FILE_PATH));

            if (jsonReader.hasNext()) {
                tempUsersData = gson.fromJson(jsonReader, BOT_USER_TYPE_TOKEN);
            }
            usersData = tempUsersData;
            jsonReader.close();
        } catch (FileNotFoundException e) {
            log.error("DB file is not found");
        } catch (IOException e) {
            log.error("Exception is occurred during closing JSONReader: {}", e.getMessage());
        }

        return usersData;
    }

    public void updateUserDataInDbFile(BotUser botUser) {
        List<BotUser> usersData = getUsersFromDbFile();
        List<BotUser> updatedUsersData;
        Long userId = Objects.requireNonNullElse(botUser.getUserId(), Long.parseLong("666"));

        if (usersData.stream().anyMatch(userData -> userData.getUserId().equals(userId))) {
            updatedUsersData = usersData.stream()
                    .map(userData -> userData.getUserId().equals(userId) ? botUser : userData)
                    .collect(Collectors.toList());
            usersData = updatedUsersData;
        } else {
            usersData.add(botUser);
        }

        setUsersDataToDbFile(usersData);
    }

    public BotUser getUserDataFromDbFile(long userId) {
        List<BotUser> usersData = getUsersFromDbFile();

        return usersData.stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getUserId() != null)
                .filter(user -> user.getUserId().equals(userId))
                .findFirst().orElse(null);
    }

    private void setUsersDataToDbFile(List<BotUser> usersData) {
        try {
            JsonWriter jsonWriter = new JsonWriter(new FileWriter(USER_DB_FILE_PATH));
            gson.toJson(usersData, BOT_USER_TYPE_TOKEN, jsonWriter);
            jsonWriter.close();
        } catch (IOException e) {
            log.error("Exception is occurred during writing data to DB file: {}", e.getMessage());
        }
    }
}