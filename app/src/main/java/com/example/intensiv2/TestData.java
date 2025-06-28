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
    private String originalImage;
    private String videoUrl;
    private String textHint;


    public TestData(String title, String[] hintImages, String[] hintTexts, 
                   String question, String correctAnswer, 
                   double targetLat, double targetLng, float radiusMeters, int testId, String originalImage, String videoUrl, String textHint) {
        this.title = title;
        this.hintImages = hintImages;
        this.hintTexts = hintTexts;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.targetLat = targetLat;
        this.targetLng = targetLng;
        this.radiusMeters = radiusMeters;
        this.testId = testId;
        this.originalImage = originalImage;
        this.videoUrl = videoUrl;
        this.textHint = textHint;
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
    public String getOriginalImage() { return originalImage; }
    public String getVideoUrl() { return videoUrl; }
    public String getTextHint() { return textHint; }
} 