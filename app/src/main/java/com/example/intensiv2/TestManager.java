package com.example.intensiv2;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

public class TestManager {
    public static class QuestData {
        private List<TestData> questions; // список вопросов
        private List<String> placeInfoBgNames;
        private String finalImage;
        private String ticketInfoImage;

        public QuestData(List<TestData> questions, List<String> placeInfoBgNames, String finalImage, String ticketInfoImage) {
            this.questions = questions;
            this.placeInfoBgNames = placeInfoBgNames;
            this.finalImage = finalImage;
            this.ticketInfoImage = ticketInfoImage;
        }
        public List<TestData> getQuestions() { return questions; }
        public List<String> getPlaceInfoBgNames() { return placeInfoBgNames; }
        public String getFinalImage() { return finalImage; }
        public String getTicketInfoImage() { return ticketInfoImage; }
    }

    private static final Map<Integer, QuestData> quests = new HashMap<>();
    
    static {
        // Пример для "Жестокий романс"
        TestData romansQuestion = new TestData(
                "ЖЕСТОКИЙ РОМАНС",
                new String[]{"romans_hint1"},
                "Как много шагов по лестнице отделяет нас от мгновения, остановленного кадре фильма? Сосчитайте количество шагов по лестнице, с места отрывка фильма",
                "12",
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
                "17",
                57.7679, 40.9269, 25f, 2,
                "originalromans2",
                "distortedromans2",
                "besedka",
                "Очаровательный уголок Костромы, место, где некогда замирал в восхищении перед волжскими просторами сам Александр Островский..."
        );
        TestData romansQuestion3 = new TestData(
                "ЖЕСТОКИЙ РОМАНС",
                new String[]{"romans_hint3"},
                "Что же это за величественное строение, что возвышается перед вами? \n",
                "старая пристань", // сделать потом ToLower() к инпутам
                57.7679, 40.9269, 25f, 2,
                "originalromans3",
                "distortedromans3",
                "pristan",
                "Как страж веков, это здание гордо высится над седыми водами Волги-матушки, овеянное дыханием истории"
        );
        String finalImageRomans = "finalimageromans";
        String ticketInfoImageRomans = "ticketromans";
        quests.put(2, new QuestData(
                Arrays.asList(romansQuestion, romansQuestion2, romansQuestion3), //тестовая информация
                Arrays.asList("novell1","bg_task1", "bg_task2","bg_task3"), //картинки для текстовой информации
                finalImageRomans,
                ticketInfoImageRomans
        ));
        //endregion

        //region ГОРЬКИЙ 53
        TestData gorkiyQuestion = new TestData(
                "ГОРЬКИЙ 53",
                new String[]{"gorkiy_hint1"},
                "Как сейчас называется здание перед которым вы стоите?",
                "художественный музей",
                55.751244, 37.618423, 20f, 1,
                "originalgorkiy1",
                "distortedgorkiy1",
                "muzey",
                "Эхо из 1953: Это самая длинная пешеходная улица Костромы. Для съемок перекрыли 500 метров и пустили ретро-трамвай, которого никогда не было!",
                new String[]{"Художественный музей", "Театр Островского", "Дворянское собрание"}
        );
        TestData gorkiyQuestion2 = new TestData(
                "ГОРЬКИЙ 53",
                new String[]{"gorkiy_hint2"},
                "Как называется улица на которой вы сейчас находитесь?",
                "симановского",
                55.751244, 37.618423, 20f, 1,
                "originalgorkiy2",
                "distortedgorkiy2",
                "simon",
                "Улица, где в 1985-м висела растяжка «Миру — мир!». Сейчас её нет, но остались старинные фонари и дома с лепниной"
        );
        TestData gorkiyQuestion3 = new TestData(
                "ГОРЬКИЙ 53",
                new String[]{"gorkiy_hint3"},
                "Как называется здание перед вами?",
                "гауптвахта",
                55.751244, 37.618423, 20f, 1,
                "originalgorkiy3",
                "distortedgorkiy3",
                "gaup",
                "Высоченная башня с часами, где в 1953-м встречали поезда. В реальности она тушила огонь, а не встречала гостей",
                new String[]{"Филармония", "Каланча", "Гауптвахта"}
        );
        String finalImageGorkiy = "finalimagegorkiy";
        String ticketInfoImageGorkiy = "ticketgorkiy";
        quests.put(1, new QuestData(
                Arrays.asList(gorkiyQuestion, gorkiyQuestion2, gorkiyQuestion3),
                Arrays.asList("bg_gorkiy1", "bg_gorkiy2", "bg_gorkiy3", "bg_gorkiy4"),
                finalImageGorkiy,
                ticketInfoImageGorkiy
        ));
        //endregion

        //region ЗЛЫЕ ЛЮДИ
        TestData evilQuestion = new TestData(
                "ЗЛЫЕ ЛЮДИ",
                new String[]{"evil_hint1"},
                "Какая тайна покоится на своде врат?",
                "колокольня",
                55.753244, 37.620423, 30f, 1,
                "eviloriginal3",
                "evildistorted3",
                "handshake",
                "Врата открываются там, где возвышается башня Молочных рядов, взирая на тебя с величественным спокойствием.",
                new String[] {"Часовня","Крыша с флюгером", "Колокольня", "Шпиль с острым навершием"}
        );
        TestData evilQuestion2 = new TestData(
                "ЗЛЫЕ ЛЮДИ",
                new String[]{"evil_hint2"},
                "Стоит ли на месте фонарь?",
                "нет",
                55.753244, 37.620423, 30f, 1,
                "eviloriginal1",
                "evildistorted1",
                "womens",
                "Место, где переплелись древняя культура Костромы, сочная гастрономия чебуреков и стихия моды в мелочных рядов",
                new String[]{"Да", "Нет"}
        );
        TestData evilQuestion3 = new TestData(
                "ЗЛЫЕ ЛЮДИ",
                new String[]{"evil_hint3"},
                "Сколько золотых маковок скрыто за краем этого дивного вида?",
                "6",
                55.753244, 37.620423, 30f, 1,
                "eviloriginal2",
                "evildistorted2",
                "peopleonstage",
                "Места суть там, где возвышается храм Спаса в Рядах",
                new String[]{"1", "5", "6", "7"}
        );
        String finalImageEvil = "finalimageevil";
        String ticketInfoImageEvil = "ticketevil";
        quests.put(3, new QuestData(
                Arrays.asList(evilQuestion, evilQuestion2, evilQuestion3),
                Arrays.asList("evilnovell", "bg_placevil1", "bg_placevil2", "bg_placevil3"),
                finalImageEvil,
                ticketInfoImageEvil
        ));
        //endregion
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