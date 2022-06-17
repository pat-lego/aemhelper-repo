package com.adobe.aem.support.core.helpers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

public class Utils {

    private static final Gson gson = new Gson();

    public Utils()  {
        // Helper class
    }

    public static <T> List<T> getListFromJsonArray(JsonArray array, TypeToken<List<T>> typeToken) {
        if (array == null) {
            return new ArrayList<T>();
        }
        return gson.fromJson(array, typeToken.getType());
    }
    
}
