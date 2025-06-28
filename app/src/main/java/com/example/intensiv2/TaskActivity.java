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
    private TextView titleTextView;
    private ImageButton hintButton;
    private Button atPlaceButton, menuButton;
    private TestData currentTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);

        //id теста из Intent
        int testId = getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY);
        currentTest = TestManager.getTest(testId);
        
        if (currentTest == null) {
            Toast.makeText(this, "Тест не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initializeViews();
        setupTestData();
        setupClickListeners();
        checkLocationPermissionAndStart();
    }

    private void initializeViews() {
        hintImageView = findViewById(R.id.hintImageView);
        taskTextView = findViewById(R.id.taskTextView);
        titleTextView = findViewById(R.id.titleTextView);
        hintButton = findViewById(R.id.hintButton);
        atPlaceButton = findViewById(R.id.atPlaceButton);
        menuButton = findViewById(R.id.menuButton);
    }

    private void setupTestData() {
        //заголовок
        titleTextView.setText(currentTest.getTitle());
        
        //подсказка
        updateHint();
    }

    private void setupClickListeners() {
        hintButton.setOnClickListener(v -> switchHint());
        atPlaceButton.setOnClickListener(v -> showQuestionDialog());
        menuButton.setOnClickListener(v -> finish());
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
        locationNotifier = new LocationNotifier(this, currentTest.getTargetLat(), currentTest.getTargetLng(), currentTest.getRadiusMeters());
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
        hintStep = (hintStep + 1) % currentTest.getHintImages().length;
        updateHint();
    }

    private void updateHint() {
        //устанавливаем изображение
        int imageResourceId = TestManager.getDrawableResourceId(currentTest.getHintImages()[hintStep]);
        hintImageView.setImageResource(imageResourceId);
        
        //устанавливаем текст
        taskTextView.setText(currentTest.getHintTexts()[hintStep]);
    }

    private void showQuestionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        QuestionDialog dialog = QuestionDialog.newInstance(currentTest.getQuestion(), currentTest.getCorrectAnswer());
        dialog.show(fm, "question_dialog");
    }

    private void showErrorDialog() {
        FragmentManager fm = getSupportFragmentManager();
        new ErrorDialog().show(fm, "error_dialog");
    }

    public static class QuestionDialog extends DialogFragment {
        private static final String ARG_QUESTION = "question";
        private static final String ARG_CORRECT_ANSWER = "correct_answer";

        public static QuestionDialog newInstance(String question, String correctAnswer) {
            QuestionDialog dialog = new QuestionDialog();
            Bundle args = new Bundle();
            args.putString(ARG_QUESTION, question);
            args.putString(ARG_CORRECT_ANSWER, correctAnswer);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String question = getArguments().getString(ARG_QUESTION, "Вопрос");
            String correctAnswer = getArguments().getString(ARG_CORRECT_ANSWER, "");
            
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Введите ответ");
            
            builder.setTitle(question)
                    .setView(input)
                    .setPositiveButton("ПРОВЕРИТЬ", (dialog, which) -> {
                        String answer = input.getText().toString().trim();
                        if (correctAnswer.equals(answer)) {
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
        // успешно
    }
} 