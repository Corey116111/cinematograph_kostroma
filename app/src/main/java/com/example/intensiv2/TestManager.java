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
                Arrays.asList(romansQuestion, romansQuestion2, romansQuestion3), //тестовая информация
                Arrays.asList(novelText, placeInfo, placeInfo2, placeInfo3), //информация до/после нахождения точки
                Arrays.asList("novell1","bg_task", "bg_task2","bg_task3","bg_task4") //картинки для текстовой информации
        ));

        // ГОРЬКИЙ 53
        String gorkiyNovel = "1953 год. Горький. Закрытый город, где тени прошлого длиннее настоящего... Здесь, среди шепота стен и тревожных сводок, расследуется убийство, всколыхнувшее тишину послевоенных лет. Но сам Горький тех лет почти исчез под слоями времени.\n" +
                "А знаешь, где ожил этот детектив из эпохи стали и страха? Здесь. В Костроме. Она стала его двойником, его кинематографической машиной времени. Каждый кадр сериала – камень в мостовой этого города.\n";
        TestData gorkiyQuestion = new TestData(
                "ГОРЬКИЙ 53",
                new String[]{"gorkiy_hint1", "gorkiy_hint2", "gorkiy_hint3", "gorkiy_hint4"},
                "Как называется сейчас здание на котором была надпись “30 лет СССР”",
                "надо почитать самим..",
                55.751244, 37.618423, 20f, 1,
                "",
                "",
                "",
                "Эхо из 1953: Это самая длинная пешеходная улица Костромы. Для съемок перекрыли 500 метров и пустили ретро-трамвай, которого никогда не было!"
        );
        String gorkiyPlaceInfo = "";
        quests.put(1, new QuestData(
                gorkiyNovel,
                Arrays.asList(gorkiyQuestion),
                Arrays.asList(gorkiyNovel, gorkiyPlaceInfo),
                Arrays.asList("bg_gorkiy_place1")
        ));
        // ЗЛЫЕ ЛЮДИ
        String evilNovel = "Конец XIX века раскроет перед вами свои тайны: вы станете частью расследования громкого и загадочного преступления, шагните в самые тёмные уголки этой истории… \n" +
                "или сумеете обойти её глухие закоулки…\n";
        TestData evilQuestion = new TestData(
                "ЗЛЫЕ ЛЮДИ",
                new String[]{"evil_hint1"},
                "Какая тайна покоится на своде врат?",
                "колокольня",
                55.753244, 37.620423, 30f, 1,
                "eviloriginal3",
                "evildistorted3",
                "handshake",
                "Врата открываются там, где возвышается башня Молочных рядов, взирая на тебя с величественным спокойствием."
        );
        TestData evilQuestion2 = new TestData(
                "ЗЛЫЕ ЛЮДИ",
                new String[]{"evil_hint2"},
                "Стоит ли на месте фонарь?",
                "нет",
                55.753244, 37.620423, 30f, 1,
                "eviloriginal1",
                "evildistorted1",
                "handshake",
                "Место, где переплелись древняя культура Костромы, сочная гастрономия чебуреков и стихия моды в мелочных рядов"
        );
        TestData evilQuestion3 = new TestData(
                "ЗЛЫЕ ЛЮДИ",
                new String[]{"evil_hint3"},
                "Какая тайна покоится на своде врат?",
                "колокольня",
                55.753244, 37.620423, 30f, 1,
                "eviloriginal2",
                "evildistorted2",
                "handshake",
                "Врата открываются там, где возвышается башня Молочных рядов, взирая на тебя с величественным спокойствием."
        );
        String evilPlaceInfo = "Мы побывали в финальных главах многосерийного полотна «Злые люди», где действие разворачивается на закате XIX века. Сыщик Степан, позабыв о деле, засмотрелся на очаровательную торговку, и теперь Яну Францевичу буквально приходилось волоком вытаскивать рассеянного коллегу с шумного базара к спасительным воротам\n";
        String evilPlaceInfo2 = "Неугомонная мадам Бариньяк ведет оживленную беседу, а юная Варвара, с трудом сдерживающая трепет, ловит каждое ее слово, шагая по шумным торговым улицам, мелочным Костромским рядам. Они были возведены вместо множества небольших деревянных лавок и прилавков, возникающих стихийно и диссонирующих с классической каменной застройкой костромского центра.";
        quests.put(3, new QuestData(
                evilNovel,
                Arrays.asList(evilQuestion),
                Arrays.asList(evilPlaceInfo, evilPlaceInfo),
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