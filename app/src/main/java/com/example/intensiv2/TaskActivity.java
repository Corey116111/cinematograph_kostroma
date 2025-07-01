package com.example.intensiv2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import android.widget.VideoView;

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
import android.widget.RadioGroup;
import android.widget.RadioButton;

public class TaskActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    private LocationNotifier locationNotifier;
    private int hintStep = 0;
    private ImageView disortedImageView;
    private ImageView originalImageView;
    private VideoView videoView;
    private TextView textHintView;
    private ImageView hintImageView;
    private TextView taskTextView;
    private TextView titleTextView;
    private ImageButton hintButton;
    private ImageView atPlaceButton, menuButton;
    private TestManager.QuestData currentQuest;
    private TestData currentTest;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);

        int questId = getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY);
        currentQuest = TestManager.getQuest(questId);

        // Проверяем восстановление после краша
        if (savedInstanceState != null) {
            finish();
            return;
        }

        if (currentQuest == null) {
            Toast.makeText(this, "Квест не найден!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        int testId = getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, -1);
        if (testId == -1) {
            finish();
            return;
        }
        currentQuestionIndex = getIntent().getIntExtra(QuestIntroActivity.EXTRA_QUESTION_INDEX, 0);
        if (currentQuestionIndex >= currentQuest.getQuestions().size()) {
            Toast.makeText(this, "Квест завершён!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        currentTest = currentQuest.getQuestions().get(currentQuestionIndex);

        initializeViews();
        setupTestData();
        setupClickListeners();

        // Отложенный запрос разрешений
        new Handler().postDelayed(this::checkLocationPermissionAndStart, 300);
    }

    private void initializeViews() {
        disortedImageView = findViewById(R.id.disortedImageView);
        originalImageView = findViewById(R.id.originalImageView);
        videoView = findViewById(R.id.videoView);
        textHintView = findViewById(R.id.textHintView);
        //taskTextView = findViewById(R.id.taskTextView);
        titleTextView = findViewById(R.id.titleTextView);
        hintButton = findViewById(R.id.hintButton);
        atPlaceButton = findViewById(R.id.atPlaceButton);
        menuButton = findViewById(R.id.menuButton);
    }

    private void setupTestData() {
        //заголовок
        titleTextView.setText(currentTest.getTitle());

        //подсказка
        hintStep = 0;
        updateHint();
    }

    private void setupClickListeners() {
        hintButton.setOnClickListener(v -> switchHint());
        atPlaceButton.setOnClickListener(v -> showQuestionDialog());
        menuButton.setOnClickListener(v -> finish());
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableFullscreen();
        }
    }

    private void checkLocationPermissionAndStart() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            startLocationServiceSafely();
        }
    }

    private void startLocationServiceSafely() {
        try {
            if (locationNotifier != null) {
                locationNotifier.stop();
                locationNotifier = null;
            }

            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "Включите GPS в настройках", Toast.LENGTH_LONG).show();
                return;
            }

            locationNotifier = new LocationNotifier(this,
                    currentTest.getTargetLat(),
                    currentTest.getTargetLng(),
                    currentTest.getRadiusMeters());
            locationNotifier.start();
        } catch (SecurityException e) {
            Log.e("GPS", "Security exception", e);
            Toast.makeText(this, "Ошибка доступа к GPS", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("GPS", "Error starting location", e);
            Toast.makeText(this, "Ошибка инициализации GPS", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new Handler().postDelayed(this::startLocationServiceSafely, 300);
            } else {
                Toast.makeText(this, "GPS недоступен, используйте кнопку 'Я на месте'", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void switchHint() {
        hintStep = (hintStep + 1) % 4;
        updateHint();
    }

    private void updateHint() {
        disortedImageView.setVisibility(View.GONE);
        originalImageView.setVisibility(View.GONE);
        videoView.setVisibility(View.GONE);
        textHintView.setVisibility(View.GONE);
        //taskTextView.setVisibility(View.GONE);

        if (hintStep == 0) {
            disortedImageView.setVisibility(View.VISIBLE);
            int resId = TestManager.getDrawableResourceId(currentTest.getDistortedImage());
            disortedImageView.setImageResource(resId);
        } else if (hintStep == 1) {
            originalImageView.setVisibility(View.VISIBLE);
            int resId = TestManager.getDrawableResourceId(currentTest.getOriginalImage());
            originalImageView.setImageResource(resId);
        } else if (hintStep == 2) {
            textHintView.setVisibility(View.VISIBLE);
            textHintView.setText(currentTest.getTextHint());
        } else if (hintStep == 3) {
            if (currentTest.getVideoUrl() != null || !currentTest.getVideoUrl().isEmpty()) {
                videoView.setVisibility(View.VISIBLE);
                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + currentTest.getVideoUrl());
                videoView.setVideoURI(videoUri);
                videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        videoView.start();
                    }
                });
                // Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + currentTest.getVideoUrl());
                // videoView.setVideoURI(videoUri);
                // videoView.start();
            } else {
                hintStep = 0;
                updateHint();
            }
        }
    }

    private void showQuestionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        QuestionDialog dialog = QuestionDialog.newInstance(
            currentTest.getQuestion(),
            currentTest.getCorrectAnswer(),
            currentTest.getOptions()
        );
        dialog.show(fm, "question_dialog");
    }

    private void showErrorDialog() {
        FragmentManager fm = getSupportFragmentManager();
        new ErrorDialog().show(fm, "error_dialog");
    }

    public static class QuestionDialog extends DialogFragment {
        private static final String ARG_QUESTION = "question";
        private static final String ARG_CORRECT_ANSWER = "correct_answer";
        private static final String ARG_OPTIONS = "options";

        public static QuestionDialog newInstance(String question, String correctAnswer, String[] options) {
            QuestionDialog dialog = new QuestionDialog();
            Bundle args = new Bundle();
            args.putString(ARG_QUESTION, question);
            args.putString(ARG_CORRECT_ANSWER, correctAnswer);
            args.putStringArray(ARG_OPTIONS, options);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String question = getArguments().getString(ARG_QUESTION, "Вопрос");
            String correctAnswer = getArguments().getString(ARG_CORRECT_ANSWER, "");
            String[] options = getArguments().getStringArray(ARG_OPTIONS);

            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_question, null);

            TextView questionText = view.findViewById(R.id.dialogQuestionText);
            EditText answerInput = view.findViewById(R.id.dialogAnswerInput);
            RadioGroup optionsGroup = view.findViewById(R.id.dialogOptionsGroup);
            ImageView imgCancel = view.findViewById(R.id.dialogCancel);
            ImageView imgCheck = view.findViewById(R.id.dialogCheck);

            questionText.setText(question);

            if (options != null && options.length > 0) {
                // Показываем варианты, скрываем EditText
                optionsGroup.setVisibility(View.VISIBLE);
                answerInput.setVisibility(View.GONE);
                optionsGroup.removeAllViews();
                for (int i = 0; i < options.length; i++) {
                    RadioButton rb = new RadioButton(getContext());
                    rb.setText(options[i]);
                    rb.setId(i);
                    rb.setTextSize(18);
                    rb.setTextColor(0xFF333333);
                    optionsGroup.addView(rb);
                }
            } else {
                // Только текстовый ответ
                optionsGroup.setVisibility(View.GONE);
                answerInput.setVisibility(View.VISIBLE);
            }

            AlertDialog dialog = new AlertDialog.Builder(requireActivity())
                    .setView(view)
                    .create();

            imgCancel.setOnClickListener(v -> dialog.dismiss());
            imgCheck.setOnClickListener(v -> {
                String answer = null;
                if (options != null && options.length > 0) {
                    int checkedId = optionsGroup.getCheckedRadioButtonId();
                    if (checkedId >= 0 && checkedId < options.length) {
                        answer = options[checkedId].toLowerCase();
                    }
                } else {
                    answer = answerInput.getText().toString().trim().toLowerCase();
                }
                if (answer != null && correctAnswer.equals(answer)) {
                    ((TaskActivity) requireActivity()).onCorrectAnswer();
                    dialog.dismiss();
                } else {
                    ((TaskActivity) requireActivity()).showErrorDialog();
                }
            });

            return dialog;
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
        
        // Проверяем, есть ли информация о месте для текущего вопроса
        boolean hasPlaceInfo = currentQuest.getPlaceInfoTexts() != null &&
                              currentQuestionIndex < currentQuest.getPlaceInfoTexts().size();

        if (hasPlaceInfo) {
            // Показываем информацию о месте для текущего вопроса
            Intent intent = new Intent(this, QuestIntroActivity.class);
            intent.putExtra(TestConstants.EXTRA_TEST_ID, getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY));
            intent.putExtra(QuestIntroActivity.EXTRA_SCREEN_TYPE, "placeinfo");
            intent.putExtra(QuestIntroActivity.EXTRA_QUESTION_INDEX, currentQuestionIndex);
            startActivity(intent);
            finish();
        } else {
            // Проверяем, есть ли следующий вопрос
            int nextQuestionIndex = currentQuestionIndex + 1;
            if (nextQuestionIndex < currentQuest.getQuestions().size()) {
                // Есть следующий вопрос - переходим к нему
                Intent intent = new Intent(this, TaskActivity.class);
                intent.putExtra(TestConstants.EXTRA_TEST_ID, getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY));
                intent.putExtra(QuestIntroActivity.EXTRA_QUESTION_INDEX, nextQuestionIndex);
                startActivity(intent);
                finish();
            } else {
                // Квест завершен, переходим на экран EndGame
                Intent intent = new Intent(this, EndGame.class);
                intent.putExtra(TestConstants.EXTRA_TEST_ID, getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY));
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationNotifier != null) {
            try {
                locationNotifier.stop();
                locationNotifier = null;
            } catch (Exception e) {
                Log.e("GPS", "Error stopping location", e);
            }
        }
    }
}