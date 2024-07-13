package com.example.unitconverter;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

public class HistoryUtils {
    private static final String PREFS_NAME = "unit_conversion_history";
    private static final String KEY_HISTORY = "history";

    public static void saveHistory(Context context, String historyItem) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        List<String> history = getHistory(context);
        history.add(0, historyItem); // Add new item at the top
        editor.putString(KEY_HISTORY, String.join(";", history));
        editor.apply();
    }

    public static List<String> getHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String historyString = prefs.getString(KEY_HISTORY, "");
        String[] historyArray = historyString.split(";");
        List<String> historyList = new ArrayList<>();
        for (String history : historyArray) {
            if (!history.isEmpty()) {
                historyList.add(history);
            }
        }
        return historyList;
    }

    public static void clearHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(KEY_HISTORY);
        editor.apply();
    }
}
