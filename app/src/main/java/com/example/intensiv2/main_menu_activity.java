package com.example.intensiv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class main_menu_activity extends AppCompatActivity {

    public boolean gorkiy_not_passed = true;
    public boolean romans_not_passed = true;
    public boolean evil_people_not_passed = true;
    public boolean finallyLocked = true; // финальный квест заблокирован
    private ImageView finallyQuest;
    private ImageView gorkiy;
    private ImageView romans;
    private ImageView evil_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableFullscreen();
        setContentView(R.layout.main_menu);

        finallyQuest = findViewById(R.id.finally_quest);
        gorkiy = findViewById(R.id.gorkiy);
        romans = findViewById(R.id.romans);
        evil_people = findViewById(R.id.evil_people);

        loadQuestStates();
        updateQuestImages();
    }

    public void goToAchievement(View v)
    {
        Intent intent = new Intent (this, achievement_activity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadQuestStates();
        updateQuestImages();
    }

    private void updateQuestImages()
    {
        runOnUiThread(() -> {
            ViewGroup.LayoutParams params = finallyQuest.getLayoutParams();

            finallyQuest.setImageResource(finallyLocked
                    ? R.drawable.finally_lock
                    : R.drawable.finally_unlocked);
            finallyQuest.setLayoutParams(params);

            params = gorkiy.getLayoutParams();
            gorkiy.setImageResource(gorkiy_not_passed
                    ? R.drawable.gorkiy_lock
                    : R.drawable.gorkiy_unlocked);
            gorkiy.setLayoutParams(params);

            params = romans.getLayoutParams();
            romans.setImageResource(romans_not_passed
                    ? R.drawable.romans_lock
                    : R.drawable.romans_unlocked);
            romans.setLayoutParams(params);

            params = evil_people.getLayoutParams();
            evil_people.setImageResource(evil_people_not_passed
                    ? R.drawable.evil_people_lock
                    : R.drawable.evil_people_unlocked);
            evil_people.setLayoutParams(params);
        });
    }

    private void enableFullscreen()
    {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    public void unlockQuest(View v)
    {
        gorkiy_not_passed = false;
        romans_not_passed = false;
        evil_people_not_passed = false;
        finallyLocked = false;

        saveQuestStates();
        updateQuestImages();
    }

    public void finally_quest_click(View v)
    {
        if (finallyLocked)
        {
            Toast.makeText(main_menu_activity.this,
                    "Чтобы разблокировать, пройдите квесты по доступным фильмам",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent (this, FinallyWindowQuest.class);
            startActivity(intent);
        }
    }

    public void gorkiy_click(View v)
    {
        Intent intent = new Intent(this, QuestIntroActivity.class);
        intent.putExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void romans_click(View v)
    {
        Intent intent = new Intent(this, QuestIntroActivity.class);
        intent.putExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_ROMANS);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void evil_people_click(View v)
    {
        Intent intent = new Intent(this, QuestIntroActivity.class);
        intent.putExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_EVIL_PEOPLE);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableFullscreen();
        }
    }


    /// cохранение состояний в SharedPreferences
    private void saveQuestStates() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("finallyLocked", finallyLocked);
        editor.putBoolean("gorkiyLocked", gorkiy_not_passed);
        editor.putBoolean("romansLocked", romans_not_passed);
        editor.putBoolean("evilPeopleLocked", evil_people_not_passed);
        editor.apply();
    }

    /// pагрузка состояний из SharedPreferences
    private void loadQuestStates() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        finallyLocked = prefs.getBoolean("finallyLocked", true);
        gorkiy_not_passed = prefs.getBoolean("gorkiyLocked", true);
        romans_not_passed = prefs.getBoolean("romansLocked", true);
        evil_people_not_passed = prefs.getBoolean("evilPeopleLocked", true);
    }
}