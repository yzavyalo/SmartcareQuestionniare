# smartcare_questionniare

Native
public native long connectSmartSpace(String name, String ip, int port) - подключение к узла ИП
public native void disconnectSmartSpace(long nodeDescriptor) — отключение от узла ИП
public native Questionnaire  getQuestionnaire(long nodeDescriptor) - получение опросника из ИП
Class Questionnaire 
LinkedList <Question> getQuestions(); - получение списка вопросов
String getUri() - получения идентификатора вопросника в ИП

Class Question 
String getDescription()  - получение вопроса
 String getUri() - получения идентификатора вопрос в ИП
Answer getAnswer() - получение ответа

Class Answer 
String getItems() - получение вариантов ответа
String getType() - получение типа ответа
String getUri() - получения идентификатора ответа в ИП

Class AnswerItem 
String getItemText() - получение текста варианта ответа
String getItemScore() - получение значимость варианта ответа
LinkedList<Answer> getSubAnswers() - получение списка подответов
String getUri() - получения идентификатора варианта ответа в ИП

Типы ответов
SingleChoise —  одновариантный ответ
MultipleChoise — многовариантный ответ
BipolarQuestion — шкальный вопрос с непрерывной шкалой
Dichotomous — дихотомический вопрос типа "да - нет"
GuttmanScale — ответ - оценка интенсивности по шкале Гуттман
LikertScale — ответ - разъяснение по шкале Лайкерта
ContinuousScale —  ответ - оценка по шкале
Text —  текст
Number —  ответ — число
Phone —  номер телефона
Email —  адрес электронной почты
Video —  загрузить видео
Audio —  загрузить аудио
Photo —  загрузить фото 
File —  загрузить бинарный файл

Пример
static protected long nodeDescriptor;
nodeDescriptor = connectSmartSpace("X", "78.46.130.194", 10010);
        if (nodeDescriptor == -1){ return;}	
Questionnaire questionnaire;
questionnaire = getQuestionnaire(nodeDescriptor);
LinkedList <Question> q = questionnaire.getQuestions();
for (int i = 0; i < q.size(); i++) {
    Question qst = q.get(i);
    Log.i(TAG, qst.getDescription());
    Answer a = qst.getAnswer();
    Log.i(TAG, a.getType());
    LinkedList<AnswerItem> ai = a.getItems();
    if (ai.size() > 0){
        for (int j = 0; j < ai.size(); j++) {
            AnswerItem item = ai.get(j);
            Log.i(TAG, item.getItemText());
            LinkedList<Answer> suba = item.getSubAnswers();
            if (suba.size() > 0) {
               for (int k = 0; k < suba.size(); k++) {
                   Answer sitem = suba.get(k);
                   Log.i(TAG, a.getType());
                   LinkedList<AnswerItem> sai = sitem.getItems();
               }
           }
        }
    }
}
disconnectSmartSpace(nodeDescriptor);


