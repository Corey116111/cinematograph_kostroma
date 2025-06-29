package com.example.intensiv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuestIntroActivity extends AppCompatActivity {
    public static final String EXTRA_SCREEN_TYPE = "screen_type"; // "intro" или "placeinfo"
    public static final String EXTRA_QUESTION_INDEX = "question_index";
    public static final String EXTRA_BG_RES_NAME = "bg_res_name";

    private TextView questTitleTextView, questIntroTextView;
    private Button nextButton;
    private ImageButton soundButton;
    private int questId;
    private TestManager.QuestData questData;
    private String screenType;
    private int questionIndex;
    private String bgResName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_intro);

        questTitleTextView = findViewById(R.id.questTitleTextView);
        questIntroTextView = findViewById(R.id.questIntroTextView);
        nextButton = findViewById(R.id.nextButton);
        soundButton = findViewById(R.id.soundButton);

        questId = getIntent().getIntExtra(TestConstants.EXTRA_TEST_ID, TestConstants.TEST_GORKIY);
        screenType = getIntent().getStringExtra(EXTRA_SCREEN_TYPE);
        questionIndex = getIntent().getIntExtra(EXTRA_QUESTION_INDEX, 0);
        bgResName = getIntent().getStringExtra(EXTRA_BG_RES_NAME);
        questData = TestManager.getQuest(questId);
        if (questData == null) {
            finish();
            return;
        }
        //устанавливаем фон, если передан
        if (bgResName != null) {
            int bgResId = getResources().getIdentifier(bgResName, "drawable", getPackageName());
            if (bgResId != 0) {
                findViewById(R.id.scrollView).getRootView().setBackgroundResource(bgResId);
            }
        }
        //выбираем текст и заголовок
        if ("placeinfo".equals(screenType)) {
            //экран после вопроса
            questTitleTextView.setText(questData.getQuestions().get(questionIndex).getTitle());
            questIntroTextView.setText(questData.getPlaceInfoTexts().get(questionIndex));
            nextButton.setText("ДАЛЕЕ");
            nextButton.setOnClickListener(v -> {
                //переходим к следующему вопросу в TaskActivity
                int nextQuestionIndex = questionIndex + 1;
                if (nextQuestionIndex < questData.getQuestions().size()) {
                    Intent intent = new Intent(this, TaskActivity.class);
                    intent.putExtra(TestConstants.EXTRA_TEST_ID, questId);
                    intent.putExtra(EXTRA_QUESTION_INDEX, nextQuestionIndex);
                    startActivity(intent);
                    finish();
                } else {
                    // Квест завершен
                    Toast.makeText(this, "Квест завершен! Поздравляем!", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else {
            //обычный intro (новелла)
            if (!questData.getQuestions().isEmpty()) {
                questTitleTextView.setText(questData.getQuestions().get(0).getTitle());
            }
            questIntroTextView.setText(questData.getNovelText());
            nextButton.setText("ДАЛЕЕ");
            nextButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, TaskActivity.class);
                intent.putExtra(TestConstants.EXTRA_TEST_ID, questId);
                intent.putExtra(EXTRA_QUESTION_INDEX, 0); // начинаем с первого вопроса
                startActivity(intent);
                finish();
            });
        }
        // soundButton хз зачем кнопка на макете но можно будет добавить че там хотели
    }
} 