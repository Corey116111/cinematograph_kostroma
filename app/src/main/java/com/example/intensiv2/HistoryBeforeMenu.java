package com.example.intensiv2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

public class HistoryBeforeMenu extends AppCompatActivity {
    private int clickCount = 0;
    private ConstraintLayout history_before_menu_id_glav;
    private ImageButton soundButton;
    private MediaPlayer audioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableFullscreen();
        setContentView(R.layout.history_before_menu);
        history_before_menu_id_glav = findViewById(R.id.history_before_menu_id_glav);

        soundButton = findViewById(R.id.soundButton);
        if (soundButton != null) {
            soundButton.setOnClickListener(v -> {
                if (audioPlayer != null && audioPlayer.isPlaying()) {
                    audioPlayer.stop();
                    audioPlayer.release();
                    audioPlayer = null;
                    return;
                }
                int audioIndex = clickCount;
                if (audioIndex >= TestManager.getHistoryAudioCount()) audioIndex = TestManager.getHistoryAudioCount() - 1;
                String audioName = TestManager.getHistoryAudioName(audioIndex);
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
    }

    /// убираю шторы и кнопки телефона
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableFullscreen();
        }
    }

    // следующая реплика
    public void next(View view) {
        if (audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer.release();
            audioPlayer = null;
        }
        clickCount++;

        if (clickCount == 1)  // контролируем нажатия для реплик
        {
            history_before_menu_id_glav.setBackgroundResource(R.drawable.novella2);
        }
        else if (clickCount == 2)
        {
            history_before_menu_id_glav.setBackgroundResource(R.drawable.novella3);
        }
        else if (clickCount == 3)
        {
            history_before_menu_id_glav.setBackgroundResource(R.drawable.novella4);
        }
        else
        {
            view.setEnabled(false);
            goToMainMenu();
        }
    }
    /// переход в меню
    public void goToMainMenu()
    {
        Intent intent = new Intent (this, main_menu_activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioPlayer != null) {
            audioPlayer.release();
            audioPlayer = null;
        }
    }
}