package com.example.intensiv2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class main_menu_activity extends AppCompatActivity {

    private boolean finallyLocked = true; // Флаг блокировки квеста

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableFullscreen();
        setContentView(R.layout.main_menu);

        ImageView finallyQuest = findViewById(R.id.finally_quest);
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
            // здесь код для разблокированного состояния
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableFullscreen();
        }
    }
}