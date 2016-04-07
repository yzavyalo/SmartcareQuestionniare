# SmartcareQuestionniare

<h2>Native</h2>

public native long connectSmartSpace(String name, String ip, int port) - подключение к узла ИП

public native void disconnectSmartSpace(long nodeDescriptor) — отключение от узла ИП

public native Questionnaire  getQuestionnaire(long nodeDescriptor) - получение опросника из ИП

<h2>Class Questionnaire</h2> 

LinkedList <Question> getQuestions(); - получение списка вопросов

String getUri() - получения идентификатора вопросника в ИП

<h2>Class Question </h2>

String getDescription()  - получение вопроса

String getUri() - получения идентификатора вопрос в ИП

Answer getAnswer() - получение ответа

<h2>Class Answer </h2>

String getItems() - получение вариантов ответа

String getType() - получение типа ответа

String getUri() - получения идентификатора ответа в ИП

<h2>Class AnswerItem</h2> 

String getItemText() - получение текста варианта ответа

String getItemScore() - получение значимость варианта ответа

LinkedList<Answer> getSubAnswers() - получение списка подответов

String getUri() - получения идентификатора варианта ответа в ИП

<h2>Типы ответов</h2>

SingleChoise —  одновариантный ответ;

MultipleChoise — многовариантный ответ;

BipolarQuestion — шкальный вопрос с непрерывной шкалой;

Dichotomous — дихотомический вопрос типа "да - нет";

GuttmanScale — ответ - оценка интенсивности по шкале Гуттман;

LikertScale — ответ - разъяснение по шкале Лайкерта;

ContinuousScale —  ответ - оценка по шкале;

Text —  текст;

Number —  ответ — число;

Phone —  номер телефона;

Email —  адрес электронной почты;

Video —  загрузить видео;

Audio —  загрузить аудио;

Photo —  загрузить фото;

File —  загрузить бинарный файл.

<h2>Пример</h2>
```groovy
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
```

