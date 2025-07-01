package com.example.intensiv2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuestIntroActivity extends AppCompatActivity {
    public static final String EXTRA_SCREEN_TYPE = "screen_type"; // "intro" или "placeinfo"
    public static final String EXTRA_QUESTION_INDEX = "question_index";
    public static final String EXTRA_BG_RES_NAME = "bg_res_name";

    private ImageView nextButton;
    private ImageButton soundButton;
    private Button startQuestButton;
    private Button backButton;
    private int questId;
    private TestManager.QuestData questData;
    private String screenType;
    private int questionIndex;
    private String bgResName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_intro);
        enableFullscreen();
        nextButton = findViewById(R.id.nextButton);
        soundButton = findViewById(R.id.soundButton);
        startQuestButton = findViewById(R.id.startQuestButton);
        backButton = findViewById(R.id.backButton);

        questId = getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY);
        screenType = getIntent().getStringExtra(EXTRA_SCREEN_TYPE);
        questionIndex = getIntent().getIntExtra(EXTRA_QUESTION_INDEX, 0);
        bgResName = getIntent().getStringExtra(EXTRA_BG_RES_NAME);
        questData = TestManager.getQuest(questId);
        if (questData == null) {
            finish();
            return;
        }

        // По умолчанию скрываем новые кнопки
        startQuestButton.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);

        // Устанавливаем фон из TestManager
        setBackgroundFromTestManager();

        //выбираем текст и заголовок
        if ("ticketinfo".equals(screenType)) {
            // Показываем только на экране ticketinfo
            startQuestButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.GONE);

            startQuestButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, QuestIntroActivity.class);
                intent.putExtra(EXTRA_SCREEN_TYPE, "intro");
                intent.putExtra(TestConstants.EXTRA_TEST_ID, questId);
                startActivity(intent);
                finish();
            });

            backButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, main_menu_activity.class);
                startActivity(intent);
                finish();
            });
        } else if ("placeinfo".equals(screenType)) {
            //экран после вопроса
            nextButton.setEnabled(false);
            nextButton.setOnClickListener(v -> {
                v.setEnabled(false);
                //переходим к следующему вопросу в TaskActivity
                int nextQuestionIndex = questionIndex + 1;
                if (nextQuestionIndex < questData.getQuestions().size()) {
                    Intent intent = new Intent(this, TaskActivity.class);
                    intent.putExtra(TestConstants.EXTRA_TEST_ID, questId);
                    intent.putExtra(EXTRA_QUESTION_INDEX, nextQuestionIndex);
                    startActivity(intent);
                    finish();
                } else {
                    // Квест завершен, переходим на экран EndGame
                    Intent intent = new Intent(this, EndGame.class);
                    intent.putExtra(TestConstants.EXTRA_TEST_ID, questId);
                    startActivity(intent);
                    finish();
                }
            });
            nextButton.setEnabled(true);
            nextButton.setVisibility(View.VISIBLE);
        } else {
            //обычный intro (новелла)
            nextButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, TaskActivity.class);
                intent.putExtra(TestConstants.EXTRA_TEST_ID, questId);
                intent.putExtra(EXTRA_QUESTION_INDEX, 0); // начинаем с первого вопроса
                startActivity(intent);
                finish();
            });
            nextButton.setEnabled(true);
            nextButton.setVisibility(View.VISIBLE);
        }
        // soundButton хз зачем кнопка на макете но можно будет добавить че там хотели
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
                String bgImageName = null;

                if("ticketinfo".equals(screenType)){
                    bgImageName = questData.getTicketInfoImage();
                }
                else if ("placeinfo".equals(screenType)) {
                    // Для экрана информации о месте используем изображение по индексу вопроса + 1
                    // (индекс 0 - для intro, индексы 1+ для placeinfo экранов)
                    int bgIndex = questionIndex + 1;
                    if (bgIndex < questData.getPlaceInfoBgNames().size()) {
                        bgImageName = questData.getPlaceInfoBgNames().get(bgIndex);
                    }
                } else {
                    // Для intro экрана используем первое изображение (индекс 0)
                    bgImageName = questData.getPlaceInfoBgNames().get(0);
                }

                if (bgImageName != null && !bgImageName.isEmpty()) {
                    int bgResId = TestManager.getDrawableResourceId(bgImageName);
                    if (bgResId != 0) {
                        findViewById(R.id.scrollView).setBackgroundResource(bgResId);
                    }
                }
            }
        } catch (Exception e) {
            // В случае ошибки оставляем дефолтный фон
            e.printStackTrace();
        }
    }
}