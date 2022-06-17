package com.adobe.aem.support.core.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

public class TestUtils {

    @Test
    public void testTypeTokenJsonArray() {
        JsonArray array = new JsonArray();
        array.add("Pat");
        array.add("was");
        array.add("here");

        List<String> list = Utils.getListFromJsonArray(array,  new TypeToken<List<String>>() {});

        assertEquals(3, list.size());
        assertEquals("Pat", list.get(0));
        assertEquals("was", list.get(1));
        assertEquals("here", list.get(2));
    }

    @Test
    public void testTypeTokenJsonArrayNull() {
        List<String> list = Utils.getListFromJsonArray(null,  new TypeToken<List<String>>() {});

        assertEquals(0, list.size());
    }
    
}
