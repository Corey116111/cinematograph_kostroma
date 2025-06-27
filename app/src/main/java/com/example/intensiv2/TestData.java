package com.example.intensiv2;

import java.io.Serializable;

public class TestData implements Serializable {
    private String title;
    private String[] hintImages;
    private String[] hintTexts;
    private String question;
    private String correctAnswer;
    private double targetLat;
    private double targetLng;
    private float radiusMeters;
    private int testId;


    public TestData(String title, String[] hintImages, String[] hintTexts,
                    String question, String correctAnswer,
                    double targetLat, double targetLng, float radiusMeters, int testId) {
        this.title = title;
        this.hintImages = hintImages;
        this.hintTexts = hintTexts;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.targetLat = targetLat;
        this.targetLng = targetLng;
        this.radiusMeters = radiusMeters;
        this.testId = testId;
    }

    //геттеры
    public String getTitle() { return title; } //название(горький53)
    public String[] getHintImages() { return hintImages; } //картинки
    public String[] getHintTexts() { return hintTexts; }
    public String getQuestion() { return question; } //вопрос теста
    public String getCorrectAnswer() { return correctAnswer; } //ответ теста
    public double getTargetLat() { return targetLat; }
    public double getTargetLng() { return targetLng; }
    public float getRadiusMeters() { return radiusMeters; } //радиус для gps
    public int getTestId() { return testId; } //id
}