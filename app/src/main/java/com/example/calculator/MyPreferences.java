package com.example.calculator;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyPreferences {
    private static final String PREF_NAME = "CalculatorHistory";
    private static final String KEY_ARRAY_LIST_1 = "HistoryExpressions";
    private static final String KEY_ARRAY_LIST_2 = "HistoryResults";


    public static void saveHistoryExpressions(Context context, ArrayList<String> arrayList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(arrayList);

        editor.putString(KEY_ARRAY_LIST_1, json);
        editor.apply();
    }

    // Retrieve ArrayList from SharedPreferences - List 1
    public static ArrayList<String> getHistoryExpressions(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_ARRAY_LIST_1, null);

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    // Save ArrayList to SharedPreferences - List 2
    public static void saveHistoryResults(Context context, ArrayList<String> arrayList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(arrayList);

        editor.putString(KEY_ARRAY_LIST_2, json);
        editor.apply();
    }

    // Retrieve ArrayList from SharedPreferences - List 2
    public static ArrayList<String> getHistoryResults(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_ARRAY_LIST_2, null);

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}

