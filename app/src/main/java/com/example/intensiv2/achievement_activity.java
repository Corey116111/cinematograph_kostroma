package com.example.intensiv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class achievement_activity extends AppCompatActivity {
    private ImageView gorkiyAchievement;
    private ImageView romansAchievement;
    private ImageView evilPeopleAchievement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableFullscreen();
        setContentView(R.layout.achievement);

        gorkiyAchievement = findViewById(R.id.delo_g53_id);
        romansAchievement = findViewById(R.id.message);
        evilPeopleAchievement = findViewById(R.id.dossier);

        updateAchievements();
    }

    private void updateAchievements() {
        boolean gorkiyPassed = QuestStateManager.isQuestPassed(this, TestConstants.TEST_GORKIY);
        boolean romansPassed = QuestStateManager.isQuestPassed(this, TestConstants.TEST_ROMANS);
        boolean evilPeoplePassed = QuestStateManager.isQuestPassed(this, TestConstants.TEST_EVIL_PEOPLE);

        gorkiyAchievement.setImageResource(gorkiyPassed
                ? R.drawable.delo_g53_unlock
                : R.drawable.delo_g53_lock);

        romansAchievement.setImageResource(romansPassed
                ? R.drawable.message_unlock
                : R.drawable.message_lock);

        evilPeopleAchievement.setImageResource(evilPeoplePassed
                ? R.drawable.dossier_unlock
                : R.drawable.dossier_lock);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAchievements();
    }

    private void enableFullscreen() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    public void goToMenuFromAchievement(View v) {
        Intent intent = new Intent(this, main_menu_activity.class);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableFullscreen();
        }
    }
}