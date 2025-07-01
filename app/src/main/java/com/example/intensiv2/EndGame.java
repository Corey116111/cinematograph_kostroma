package com.example.intensiv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EndGame extends AppCompatActivity {

    private ImageView endGameView;
    private int questId;
    private TestManager.QuestData questData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_of_every_quest);

        questId = getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY);
        endGameView = findViewById(R.id.endGame);
        questData = TestManager.getQuest(questId);

        // Устанавливаем флаг "квест пройден"
        QuestStateManager.setQuestPassed(this, questId, true);
        
        // Проверяем, нужно ли разблокировать финальный квест
        if (QuestStateManager.areAllQuestsPassed(this)) {
            QuestStateManager.setFinallyLocked(this, false);
        }

        // Устанавливаем фон из TestManager
        setBackgroundFromTestManager();
    }

    private void setBackgroundFromTestManager() {
        try {
            if (questData != null && questData.getPlaceInfoBgNames() != null && !questData.getPlaceInfoBgNames().isEmpty()) {
                String bgFinalImange = questData.getFinalImage();

                if (bgFinalImange != null && !bgFinalImange.isEmpty()) {
                    int bgResId = TestManager.getDrawableResourceId(bgFinalImange);
                    if (bgResId != 0) {
                        endGameView.setImageResource(bgResId);
                        //endGameView.setBackgroundResource(bgResId);
                    }
                }
            }
        } catch (Exception e) {
            // В случае ошибки оставляем дефолтный фон
            e.printStackTrace();
        }
    }

    public void goToMenuFromFinally(View view) {
        Intent intent = new Intent (this, main_menu_activity.class);
        startActivity(intent);
    }
}
