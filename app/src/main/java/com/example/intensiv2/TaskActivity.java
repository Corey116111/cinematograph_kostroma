package com.example.intensiv2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class TaskActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    private LocationNotifier locationNotifier;
    private int hintStep = 0;
    private ImageView hintImageView;
    private TextView taskTextView;
    private ImageButton hintButton;
    private Button atPlaceButton, menuButton;
    private static final String CORRECT_ANSWER = "7"; //пример правильного ответа для вопроса с колоннами

    //пример координат и радиуса
    private final double TARGET_LAT = 55.751244;
    private final double TARGET_LNG = 37.618423;
    private final float RADIUS_METERS = 20f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);

        hintImageView = findViewById(R.id.hintImageView);
        taskTextView = findViewById(R.id.taskTextView);
        hintButton = findViewById(R.id.hintButton);
        atPlaceButton = findViewById(R.id.atPlaceButton);
        menuButton = findViewById(R.id.menuButton);

        hintButton.setOnClickListener(v -> switchHint());
        atPlaceButton.setOnClickListener(v -> showQuestionDialog());
        menuButton.setOnClickListener(v -> finish());

        checkLocationPermissionAndStart();
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableFullscreen();
        }
    }

    private void checkLocationPermissionAndStart() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            startLocationNotifier();
        }
    }

    private void startLocationNotifier() {
        locationNotifier = new LocationNotifier(this, TARGET_LAT, TARGET_LNG, RADIUS_METERS);
        locationNotifier.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationNotifier();
            } else {
                Toast.makeText(this, "GPS недоступен, используйте кнопку 'Я на месте'", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void switchHint() {
        hintStep = (hintStep + 1) % 4;
        switch (hintStep) {
            case 0:
                //искаженный кадр 
                hintImageView.setImageResource(R.drawable.example);
                taskTextView.setText("Найдите это место");
                break;
            case 1:
                //оригинальный кадр (пример)
                hintImageView.setImageResource(R.drawable.example);
                taskTextView.setText("Оригинальный кадр");
                break;
            case 2:
                //видеофрагмент (заглушка, можно добавить VideoView)
                hintImageView.setImageResource(R.drawable.example);
                taskTextView.setText("Видеофрагмент (заглушка |_|)");
                break;
            case 3:
                //текстовая подсказка (заглушка)
                hintImageView.setImageResource(R.drawable.example);
                taskTextView.setText("Текстовая подсказка: ...");
                break;
        }
    }

    private void showQuestionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        new QuestionDialog().show(fm, "question_dialog");
    }

    private void showErrorDialog() {
        FragmentManager fm = getSupportFragmentManager();
        new ErrorDialog().show(fm, "error_dialog");
    }

    public static class QuestionDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(android.R.layout.simple_list_item_2, null);
            //кастомный layout для вопроса
            View customView = inflater.inflate(android.R.layout.simple_list_item_2, null);
            EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Введите ответ");
            builder.setTitle("Сколько колонн у беседки?")
                    .setView(input)
                    .setPositiveButton("ПРОВЕРИТЬ", (dialog, which) -> {
                        String answer = input.getText().toString().trim();
                        if (CORRECT_ANSWER.equals(answer)) {
                            ((TaskActivity) requireActivity()).onCorrectAnswer();
                        } else {
                            ((TaskActivity) requireActivity()).showErrorDialog();
                        }
                    })
                    .setNegativeButton("НАЗАД", (dialog, which) -> dialog.dismiss());
            return builder.create();
        }
    }

    public static class ErrorDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setMessage("Ответ неверный. Попробуйте еще раз!")
                    .setPositiveButton("Попробовать заново", (dialog, which) -> dialog.dismiss());
            return builder.create();
        }
    }

    private void onCorrectAnswer() {
        Toast.makeText(this, "Поздравляем! Вы на месте!", Toast.LENGTH_SHORT).show();
        // переход к следующему этапу или экрану
    }
} 