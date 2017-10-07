package com.phuctran.makeabakingapp;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.phuctran.makeabakingapp.utils.SharedPreferencesCompat;


/**
 * Created by PhucTV on 6/23/16.
 */
public class BakingPreferences {
    private static BakingPreferences INSTANCE = null;
    private static final String FILE_NAME = "recipe_prefs";

    private final String KEY_UPTODATE = "KEY_UPTODATE";
    private final String KEY_RECIPE_ID = "KEY_RECIPE_ID";

    private SharedPreferences sharedPreferences;

    public static BakingPreferences getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BakingPreferences();
        }
        return INSTANCE;
    }

    public BakingPreferences() {
        sharedPreferences = BakingApplication.getContext().getSharedPreferences(FILE_NAME, 0);
    }

    public void updateUpToDateStatus(boolean isUptodate) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_UPTODATE, isUptodate);
        SharedPreferencesCompat.apply(editor);
    }


    public boolean isUpToDate() {
        return sharedPreferences.getBoolean(KEY_UPTODATE, false);
    }

    public int getWidgetRecipeId() {
        return sharedPreferences.getInt(KEY_RECIPE_ID, -1);
    }
    public void setWidgetRecipeId(int recipeId) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_RECIPE_ID, recipeId);
        SharedPreferencesCompat.apply(editor);
    }

}
