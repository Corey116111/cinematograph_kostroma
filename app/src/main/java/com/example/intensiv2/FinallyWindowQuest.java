package com.example.intensiv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FinallyWindowQuest extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        enableFullscreen();
        setContentView(R.layout.finally_quest_window);

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            enableFullscreen();
        }
    }

    public void goToMenuFromFinally (View v)
    {
        Intent intent = new Intent (this, main_menu_activity.class);
        startActivity(intent);
    }

    public void get_sertificate(View v) {
        EditText nameInput = findViewById(R.id.text_input);

        String name = nameInput.getText().toString().trim(); // получаем имя

        int minLength = 3; // мин длина имени

        if (name.isEmpty()) // если пустое
        {
            nameInput.setError("Введите ваше имя!");
            nameInput.requestFocus();
            return;
        }

        if (name.length() < minLength)
        {
            nameInput.setError("Имя должно содержать минимум " + minLength + " символа!");
            nameInput.requestFocus();
            return;
        }

        Toast.makeText(this, "Сертификат для " + name + " готов!", Toast.LENGTH_SHORT).show();

        // тут сделать выдачу сертификата в галерею
    }
}
