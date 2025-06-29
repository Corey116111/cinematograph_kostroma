package com.example.intensiv2;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        enableFullscreen();
        setContentView(R.layout.activity_main);
    }

    /// убираем шторку и кнопки телефона, полноэкранный режим
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
    ///  при нажатии на кнопку будет звук нажатия
    private void playCustomClickSound(Context context)
    {
            MediaPlayer player = MediaPlayer.create(context, R.raw.click);
            float volume = 0.5f;
            player.setVolume(volume, volume);
            player.setOnCompletionListener(MediaPlayer::release);
            player.start();
    }

    /// идем к новелле перед меню
    public void GoToStartNovella(View view)
    {
        view.setEnabled(false);
        playCustomClickSound(view.getContext());
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
        view.startAnimation(scaleAnim);
        final View blackOverlay = findViewById(R.id.blackOverlay);

        ValueAnimator fadeAnimator = ValueAnimator.ofFloat(0f, 1f);
        fadeAnimator.setDuration(3000); // 3 секунды
        fadeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float alpha = (float) animation.getAnimatedValue();
                view.setAlpha(1f - alpha);
                blackOverlay.setAlpha(alpha);
            }
        });

        fadeAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation)
            {
                Intent intent = new Intent(MainActivity.this, HistoryBeforeMenu.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        fadeAnimator.start();
    }
}