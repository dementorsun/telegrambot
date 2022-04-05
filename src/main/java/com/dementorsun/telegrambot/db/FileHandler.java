package com.dementorsun.telegrambot.db;

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
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
class FileHandler {

    private static final String USER_DB_FILE_PATH = "src/main/resources/userDB";
    private static final Type BOT_USER_TYPE_TOKEN = new TypeToken<List<BotUser>>(){}.getType();

    private final Gson gson;

    private List<BotUser> readFromDbFile() {
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
            log.error("Exception is occurred during closing JSONReader:", e.getMessage());
        }

        return usersData;
    }

    private void writeToDbFile(List<BotUser> usersData) {
        try {
            JsonWriter jsonWriter = new JsonWriter(new FileWriter(USER_DB_FILE_PATH));
            gson.toJson(usersData, BOT_USER_TYPE_TOKEN, jsonWriter);
            jsonWriter.close();
        } catch (IOException e) {
            log.error("Exception is occurred during writing to DB file: {}", e.getMessage());
        }
    }

    public void updateUserDataInDbFile(BotUser botUser) {
        List<BotUser> usersData = readFromDbFile();
        List<BotUser> updatedUsersData;
        long userId = botUser.userInfo.userId;

        if (usersData.stream().anyMatch(userData -> userData.userInfo.userId.equals(userId))) {
            updatedUsersData = usersData.stream()
                    .map(userData -> userData.userInfo.userId.equals(userId) ? botUser : userData)
                    .collect(Collectors.toList());
            usersData = updatedUsersData;
        } else {
            usersData.add(botUser);
        }

        writeToDbFile(usersData);

        log.info("'{}' user data is saved to DB file", userId);
    }

    public BotUser getUserDataFromDbFile(long userId) {
        List<BotUser> usersData = readFromDbFile();

        BotUser botUser = usersData.stream()
                .filter(user -> user.userInfo.userId.equals(userId))
                .findFirst().orElse(null);

        log.info("'{}' user data is fetched from DB file", userId);

        return botUser;
    }

    public List<BotUser> getAllUsersFromDbFile() {
        return readFromDbFile();
    }
}