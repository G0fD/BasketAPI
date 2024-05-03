package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Slf4j
public class ReadConfig {

    final Gson gson = new Gson();

    public Map<String, List<String>> readConfigFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Type productMapType = TypeToken.get(Map.class).getType();
            return gson.fromJson(reader, productMapType);
        } catch (IOException e) {
            log.error("Error while reading config file", e);
            throw new RuntimeException("Error while reading config file");
        }
    }
}
