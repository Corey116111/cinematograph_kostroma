package com.example.intensiv2;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

public class TestManager {
    public static class QuestData {
        private String novelText; // текст новеллы
        private List<TestData> questions; // список вопросов
        private List<String> placeInfoTexts; // тексты для экранов информации о месте
        private List<String> placeInfoBgNames;

        public QuestData(String novelText, List<TestData> questions, List<String> placeInfoTexts, List<String> placeInfoBgNames) {
            this.novelText = novelText;
            this.questions = questions;
            this.placeInfoTexts = placeInfoTexts;
            this.placeInfoBgNames = placeInfoBgNames;
        }
        public String getNovelText() { return novelText; }
        public List<TestData> getQuestions() { return questions; }
        public List<String> getPlaceInfoTexts() { return placeInfoTexts; }
        public List<String> getPlaceInfoBgNames() { return placeInfoBgNames; }
    }

    private static final Map<Integer, QuestData> quests = new HashMap<>();
    
    static {
        // Пример для "Жестокий романс"
        String novelText = "Вы перенесетесь в XIX век — эпоху жгучих страстей и томительных романов, где в душном воздухе купеческих домов витают ароматы крепкого чая и неразделённой любви.\n\nПеред вами откроется Волга — величавая и безмятежная, а старинный приволжский городок, словно сошедший с полотен Левитана, погрузит в атмосферу ностальгии по местам, кажущимся до боли знакомыми. Здесь каждый шёпот волн, каждый отсвет заката в оконных стёклах дышит поэзией русского жестокого романа.";
        TestData romansQuestion = new TestData(
            "ЖЕСТОКИЙ РОМАНС",
            new String[]{"romans_hint1"},
            new String[]{"Как драгоценная реликвия в ладонях, главный символ Костромы покоится на ее гербе\n\"Найдите это место\""},
            "В каком году установлен пароход?",
            "1913",
            57.7679, 40.9269, 25f, 1
        );
        TestData romansQuestion2 = new TestData(
            "ЖЕСТОКИЙ РОМАНС",
            new String[]{"romans_hint2"},
            new String[]{"Очаровательный уголок Костромы, место, где некогда замирал в восхищении перед волжскими просторами сам Александр Островский...\nнайдите это место"},
            "В каком году установлен пароход?",
            "1913",
            57.7679, 40.9269, 25f, 2
        );
        String placeInfo = "Побывав в самом начале фильма, невольно задумываешься о его истоках. Интересно, что сам Рязанов изначально не планировал экранизировать пьесу Островского — в его творческих планах было множество других проектов, которые, однако, так и остались нереализованными. Лишь после настоятельного совета супруги он открыл для себя \"Беспреданницу\", и что стало отправной точкой. \n\"Пришёл — будто впервые, — вспоминал позже режиссер. — И сразу понял: это моё, буду снимать!\"";
        String placeInfo2 = "Съёмки фильма проходили в живописных городах России — Ярославле, Москве и преимущественно в Костроме. Именно здесь, в знаменитой беседке Островского, был снят этот проникновенный эпизод. \nХотя подобных беседок немало, костромская была выбрана осознанно — её изысканная архитектура как нельзя лучше передавала атмосферу произведения. \nОсобое очарование кадру придавало естественное освещение, подаренное удачным расположением, и аутентичные декорации старинного городского парка.";
        quests.put(1, new QuestData(
            novelText,
            Arrays.asList(romansQuestion, romansQuestion2),
            Arrays.asList(placeInfo, placeInfo2),
            Arrays.asList("bg_task", "bg_task2")
        ));

        // ГОРЬКИЙ 53
        String gorkiyNovel = "";
        TestData gorkiyQuestion = new TestData(
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
        );
        String gorkiyPlaceInfo = ""; 
        quests.put(2, new QuestData(
            gorkiyNovel,
            Arrays.asList(gorkiyQuestion),
            Arrays.asList(gorkiyPlaceInfo),
            Arrays.asList("bg_gorkiy_place1")
        ));

        // ЗЛЫЕ ЛЮДИ
        String evilNovel = "";
        TestData evilQuestion = new TestData(
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
            55.753244, 37.620423, 30f, 1
        );
        String evilPlaceInfo = ""; 
        quests.put(3, new QuestData(
            evilNovel,
            Arrays.asList(evilQuestion),
            Arrays.asList(evilPlaceInfo),
            Arrays.asList("bg_evil_place1")
        ));
    }
    
    public static int getDrawableResourceId(String resourceName) {
        try {
            return R.drawable.class.getField(resourceName).getInt(null);
        } catch (Exception e) {
            //возвращаем дефолтную картинку если ресурс не найден
            return R.drawable.example;
        }
    }

    public static QuestData getQuest(int questId) {
        return quests.get(questId);
    }

    public static boolean hasQuest(int questId) {
        return quests.containsKey(questId);
    }
} 