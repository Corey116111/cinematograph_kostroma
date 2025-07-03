package com.example.intensiv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.media.MediaPlayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EndGame extends AppCompatActivity {

    private ImageView endGameView;
    private int questId;
    private TestManager.QuestData questData;
    private MediaPlayer audioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_of_every_quest);
        enableFullscreen();
        questId = getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY);
        endGameView = findViewById(R.id.endGame);
        questData = TestManager.getQuest(questId);

        //устанавливаем флаг "квест пройден"
        QuestStateManager.setQuestPassed(this, questId, true);
        
        //проверяем, нужно ли разблокировать финальный квест
        if (QuestStateManager.areAllQuestsPassed(this)) {
            QuestStateManager.setFinallyLocked(this, false);
        }

        //устанавливаем фон из TestManager
        setBackgroundFromTestManager();

        // --- ДОБАВЛЯЕМ ОБРАБОТЧИК КНОПКИ ЗВУКА ---
        ImageView soundButton = findViewById(R.id.soundButton);
        soundButton.setOnClickListener(v -> {
            if (audioPlayer != null && audioPlayer.isPlaying()) {
                audioPlayer.stop();
                audioPlayer.release();
                audioPlayer = null;
                return;
            }
            String audioName = questData != null ? questData.getEndAudio() : null;
            if (audioName != null && !audioName.isEmpty()) {
                int resId = getResources().getIdentifier(audioName, "raw", getPackageName());
                if (resId != 0) {
                    if (audioPlayer != null) {
                        audioPlayer.stop();
                        audioPlayer.release();
                    }
                    audioPlayer = MediaPlayer.create(this, resId);
                    float volume = 0.7f;
                    audioPlayer.setVolume(volume, volume);
                    audioPlayer.setOnCompletionListener(mp -> {
                        mp.release();
                        audioPlayer = null;
                    });
                    audioPlayer.start();
                } else {
                    Toast.makeText(this, "Аудио не найдено", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Нет аудио для этого экрана", Toast.LENGTH_SHORT).show();
            }
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

    /// если выйти из приложения, режим сохранится
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            enableFullscreen();
        }
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
        if (audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer.release();
            audioPlayer = null;
        }
        Intent intent = new Intent (this, main_menu_activity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if (audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer.release();
            audioPlayer = null;
        }
        super.onDestroy();
    }
}
