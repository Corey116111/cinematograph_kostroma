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
            55.751244, 37.618423, 20f, 1,
                "",
                "",
                "",
                ""
        );
        String gorkiyPlaceInfo = "";
        quests.put(1, new QuestData(
            gorkiyNovel,
            Arrays.asList(gorkiyQuestion),
            Arrays.asList(gorkiyPlaceInfo),
            Arrays.asList("bg_gorkiy_place1")
        ));

        String novelText = "Вы перенесетесь в XIX век — эпоху жгучих страстей и томительных романов, где в душном воздухе купеческих домов витают ароматы крепкого чая и неразделённой любви.\n\nПеред вами откроется Волга — величавая и безмятежная, а старинный приволжский городок, словно сошедший с полотен Левитана, погрузит в атмосферу ностальгии по местам, кажущимся до боли знакомыми. Здесь каждый шёпот волн, каждый отсвет заката в оконных стёклах дышит поэзией русского жестокого романа.";
        TestData romansQuestion = new TestData(
                "ЖЕСТОКИЙ РОМАНС",
                new String[]{"romans_hint1"},
                new String[]{"Как драгоценная реликвия в ладонях, главный символ Костромы покоится на ее гербе\n\"Найдите это место\""},
                "Как много шагов по лестнице отделяет нас от мгновения, остановленного кадре фильма? Сосчитайте количество шагов по лестнице, с места отрывка фильма",
                "13",
                57.7679, 40.9269, 25f, 1,
                "originalromans1",
                "disortedromans1",
                "gerb",
                "Как драгоценная реликвия в ладонях, главный символ Костромы покоится на ее гербе"
        );
        TestData romansQuestion2 = new TestData(
                "ЖЕСТОКИЙ РОМАНС",
                new String[]{"romans_hint2"},
                new String[]{"Очаровательный уголок Костромы, место, где некогда замирал в восхищении перед волжскими просторами сам Александр Островский...\nнайдите это место"},
                "Сколько изящных колонн украшают беседку Островского, если от общего числа ажурных перильных опор отнять массивные несущие столбы?",
                "7",
                57.7679, 40.9269, 25f, 2,
                "originalromans2",
                "distortedromans2",
                "besedka",
                "Очаровательный уголок Костромы, место, где некогда замирал в восхищении перед волжскими просторами сам Александр Островский..."
        );
        TestData romansQuestion3 = new TestData(
                "ЖЕСТОКИЙ РОМАНС",
                new String[]{"romans_hint3"},
                new String[]{"Очаровательный уголок Костромы, место, где некогда замирал в восхищении перед волжскими просторами сам Александр Островский...\nнайдите это место"},
                "Что же это за величественное строение, что возвышается перед вами? \n" +
                        "Ответ: Старая пристань\n",
                "Старая пристань",
                57.7679, 40.9269, 25f, 2,
                "originalromans3",
                "distortedromans3",
                "pristan",
                "Как страж веков, это здание гордо высится над седыми водами Волги-матушки, овеянное дыханием истории"
        );
        String placeInfo = "Побывав в самом начале фильма, невольно задумываешься о его истоках. Интересно, что сам Рязанов изначально не планировал экранизировать пьесу Островского — в его творческих планах было множество других проектов, которые, однако, так и остались нереализованными. Лишь после настоятельного совета супруги он открыл для себя \"Беспреданницу\", и что стало отправной точкой. \n\"Пришёл — будто впервые, — вспоминал позже режиссер. — И сразу понял: это моё, буду снимать!\"";
        String placeInfo2 = "Съёмки фильма проходили в живописных городах России — Ярославле, Москве и преимущественно в Костроме. Именно здесь, в знаменитой беседке Островского, был снят этот проникновенный эпизод. \nХотя подобных беседок немало, костромская была выбрана осознанно — её изысканная архитектура как нельзя лучше передавала атмосферу произведения. \nОсобое очарование кадру придавало естественное освещение, подаренное удачным расположением, и аутентичные декорации старинного городского парка.";
        String placeInfo3 = "Ресторан 'Старая пристань' переносит гостей на легендарную 'Ласточку' - ту самую, где случилась судьбоносная сцена. \n" +
                "Но эти воды помнят и другой, тревожный момент: во время съёмок, когда Карандышев в отчаянии гнался за пароходом, Андрей Мягков, сидевший в лодке задом наперёд, чудом избежал беды, не заметив, как оказался на опасном расстоянии от безжалостного винта.\n";
        quests.put(2, new QuestData(
                novelText,
                Arrays.asList(romansQuestion, romansQuestion2, romansQuestion3),
                Arrays.asList(novelText, placeInfo, placeInfo2, placeInfo3),
                Arrays.asList("novell1","bg_task", "bg_task2","bg_task3","bg_task4")
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
            55.753244, 37.620423, 30f, 1,
                "",
                "",
                "",
                ""
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