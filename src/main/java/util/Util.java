package util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import model.BaseEntity;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@UtilityClass
public class Util {
    public static String generateNewId() {
        int randomInt = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
        int currentTimeNano = LocalDateTime.now().getNano();
        return randomInt + "" + currentTimeNano;
    }

    public static <T extends BaseEntity> T getByIdFromList(List<T> list, String id) {
        return list.stream()
                .filter(entity -> entity.getId().equals(id))
                .findAny()
                .orElseThrow();
    }

    public static <T extends BaseEntity> List<T> getListFromFile(Gson gson, String fileName, Class<T> clazz) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            return jsonArray.asList().stream()
                    .map(jsonElement -> gson.fromJson(jsonElement, clazz))
                    .collect(Collectors.toList());
        }
    }

    public static <T extends BaseEntity> void saveToFile(List<T> list, Gson gson, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            gson.toJson(list, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T extends BaseEntity> List<T> updateEntityInList(List<T> list, T updatedEntity) {
        return list.stream()
                .map(entity -> {
                    if (entity.getId().equals(updatedEntity.getId())) {
                        return updatedEntity;
                    }
                    return entity;
                })
                .collect(Collectors.toList());
    }
}