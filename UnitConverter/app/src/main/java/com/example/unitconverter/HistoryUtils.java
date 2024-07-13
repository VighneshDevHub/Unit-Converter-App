// Utility class to handle history storage
package com.example.unitconverter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HistoryUtils {

    public static void saveHistory(Context context, String item) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = preferences.getString("history", "[]");

        List<String> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        list.add(0, item); // Add new item at the beginning

        JSONArray newJsonArray = new JSONArray(list);
        preferences.edit().putString("history", newJsonArray.toString()).apply();
    }
}
