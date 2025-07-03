package com.example.intensiv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class main_menu_activity extends AppCompatActivity {

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
        updateQuestImages();
    }

    private void updateQuestImages()
    {
        runOnUiThread(() -> {
            ViewGroup.LayoutParams params = finallyQuest.getLayoutParams();

            boolean finallyLocked = QuestStateManager.isFinallyLocked(this);

            if (!finallyLocked)
            {
                finallyQuest.setImageResource(R.drawable.finally_unlocked);
                Animation glowAnimation = AnimationUtils.loadAnimation(this, R.anim.glow_pulse);
                finallyQuest.startAnimation(glowAnimation);
            }

            params = gorkiy.getLayoutParams();
            boolean gorkiyPassed = QuestStateManager.isQuestPassed(this, TestConstants.TEST_GORKIY);
            gorkiy.setImageResource(gorkiyPassed
                    ? R.drawable.gorkiy_unlocked
                    : R.drawable.gorkiy_lock);
            gorkiy.setLayoutParams(params);

            params = romans.getLayoutParams();
            boolean romansPassed = QuestStateManager.isQuestPassed(this, TestConstants.TEST_ROMANS);
            romans.setImageResource(romansPassed
                    ? R.drawable.romans_unlocked
                    : R.drawable.romans_lock);
            romans.setLayoutParams(params);

            params = evil_people.getLayoutParams();
            boolean evilPeoplePassed = QuestStateManager.isQuestPassed(this, TestConstants.TEST_EVIL_PEOPLE);
            evil_people.setImageResource(evilPeoplePassed
                    ? R.drawable.evil_people_unlocked
                    : R.drawable.evil_people_lock);
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

    public void finally_quest_click(View v)
    {
        boolean finallyLocked = QuestStateManager.isFinallyLocked(this);
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
        intent.putExtra(QuestIntroActivity.EXTRA_SCREEN_TYPE, "ticketinfo");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void romans_click(View v)
    {
        Intent intent = new Intent(this, QuestIntroActivity.class);
        intent.putExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_ROMANS);
        intent.putExtra(QuestIntroActivity.EXTRA_SCREEN_TYPE, "ticketinfo");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void evil_people_click(View v)
    {
        Intent intent = new Intent(this, QuestIntroActivity.class);
        intent.putExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_EVIL_PEOPLE);
        intent.putExtra(QuestIntroActivity.EXTRA_SCREEN_TYPE, "ticketinfo");
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
}