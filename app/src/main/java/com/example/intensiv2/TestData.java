package com.example.intensiv2;

import java.io.Serializable;

public class TestData implements Serializable {
    private String title;
    private String[] hintImages;
    private String question;
    private String correctAnswer;
    private double targetLat;
    private double targetLng;
    private float radiusMeters;
    private int testId;
    private String originalImage;
    private String disortedImage;
    private String videoUrl;
    private String textHint;
    private String[] options;

    public TestData(String title, String[] hintImages,
                   String question, String correctAnswer, 
                   double targetLat, double targetLng, float radiusMeters, int testId, String originalImage, String disortedImage, String videoUrl, String textHint) {
        this(title, hintImages, question, correctAnswer, targetLat, targetLng, radiusMeters, testId, originalImage, disortedImage, videoUrl, textHint, null);
    }

    public TestData(String title, String[] hintImages,
                   String question, String correctAnswer,
                   double targetLat, double targetLng, float radiusMeters, int testId, String originalImage, String disortedImage, String videoUrl, String textHint, String[] options) {
        this.title = title;
        this.hintImages = hintImages;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.targetLat = targetLat;
        this.targetLng = targetLng;
        this.radiusMeters = radiusMeters;
        this.testId = testId;
        this.disortedImage = disortedImage;
        this.originalImage = originalImage;
        this.videoUrl = videoUrl;
        this.textHint = textHint;
        this.options = options;
    }

    //геттеры
    public String getTitle() { return title; } //название(горький53)
    public String[] getHintImages() { return hintImages; } //картинки
    public String getQuestion() { return question; } //вопрос теста
    public String getCorrectAnswer() { return correctAnswer; } //ответ теста
    public double getTargetLat() { return targetLat; }
    public double getTargetLng() { return targetLng; }
    public float getRadiusMeters() { return radiusMeters; } //радиус для gps
    public int getTestId() { return testId; } //id
    public String getDistortedImage() { return disortedImage; }
    public String getOriginalImage() { return originalImage; }
    public String getVideoUrl() { return videoUrl; }
    public String getTextHint() { return textHint; }

    public String[] getOptions() { return options; }
}