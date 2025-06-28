package com.example.intensiv2;

import java.util.HashMap;
import java.util.Map;

public class TestManager {
    private static final Map<Integer, TestData> tests = new HashMap<>();
    
    static {
        //горький
        tests.put(1, new TestData(
            "ГОРЬКИЙ 53",
            new String[]{"gorkiy_hint1", "gorkiy_hint2", "gorkiy_hint3", "gorkiy_hint4"},
            new String[]{
                "Найдите это место",
                "Оригинальный кадр из фильма",
                "Видеофрагмент сцены",
                "Текстовая подсказка: Ищите беседку с колоннами"
            },
            "Сколько колонн у беседки?",
            "7",
            55.751244, 37.618423, 20f, 1
        ));
        
        tests.put(2, new TestData(
            "РОМАНОВ",
            new String[]{"romans_hint1", "romans_hint2", "romans_hint3", "romans_hint4"},
            new String[]{
                "Найдите это место",
                "Оригинальный кадр из фильма",
                "Видеофрагмент сцены", 
                "Текстовая подсказка: Ищите памятник"
            },
            "В каком году установлен памятник?",
            "1913",
            55.752244, 37.619423, 25f, 2
        ));
        
        //злые люди
        tests.put(3, new TestData(
            "ЗЛЫЕ ЛЮДИ",
            new String[]{"evil_hint1", "evil_hint2", "evil_hint3", "evil_hint4"},
            new String[]{
                "Найдите это место",
                "Оригинальный кадр из фильма",
                "Видеофрагмент сцены",
                "Текстовая подсказка: Ищите здание с колоннами"
            },
            "Сколько этажей в здании?",
            "3",
            55.753244, 37.620423, 30f, 3
        ));
    }
    
    public static TestData getTest(int testId) {
        return tests.get(testId);
    }
    
    public static boolean hasTest(int testId) {
        return tests.containsKey(testId);
    }
    
    public static int getDrawableResourceId(String resourceName) {
        try {
            return R.drawable.class.getField(resourceName).getInt(null);
        } catch (Exception e) {
            //возвращаем дефолтную картинку если ресурс не найден
            return R.drawable.example;
        }
    }
} 