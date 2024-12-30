package com.expandtesting.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

public class TestDataUtils {

    public final static String Test_Data = "src/test/resources/testdata.browser/";

    //TODO: Read data from json file
    public static String getJsonData(String jsonFilename, String field) {
        try {
            // Define object of file Reader
            FileReader reader = new FileReader(Test_Data + jsonFilename + ".json");
            // Parse the JSON directly into a JsonElement
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(field).getAsString();
        } catch (Exception e) {
            System.out.println("error on getting data from test-data file");
            ;
        }
        return "";
    }

}
