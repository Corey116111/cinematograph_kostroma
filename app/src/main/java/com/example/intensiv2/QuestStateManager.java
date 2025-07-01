package com.example.intensiv2;

import android.content.Context;
import android.content.SharedPreferences;

public class QuestStateManager {
    private static final String PREFS_NAME = "quest_states";
    private static final String KEY_QUEST_PREFIX = "quest_passed_";
    private static final String KEY_FINALLY_LOCKED = "finally_locked";

    public static boolean isQuestPassed(Context context, int questId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_QUEST_PREFIX + questId, false);
    }

    public static void setQuestPassed(Context context, int questId, boolean passed) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_QUEST_PREFIX + questId, passed).apply();
    }

    public static boolean isFinallyLocked(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_FINALLY_LOCKED, true);
    }

    public static void setFinallyLocked(Context context, boolean locked) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_FINALLY_LOCKED, locked).apply();
    }

    public static boolean areAllQuestsPassed(Context context) {
        return isQuestPassed(context, TestConstants.TEST_GORKIY) &&
               isQuestPassed(context, TestConstants.TEST_ROMANS) &&
               isQuestPassed(context, TestConstants.TEST_EVIL_PEOPLE);
    }
}
