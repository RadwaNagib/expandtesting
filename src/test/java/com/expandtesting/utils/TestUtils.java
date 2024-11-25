package com.expandtesting.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestUtils {

    public static <T> T readJsonFromFile(String filePath, Class<T> tClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), tClass);

    }

}
