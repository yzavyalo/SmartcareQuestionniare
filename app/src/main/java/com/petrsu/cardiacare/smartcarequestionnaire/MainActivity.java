package com.petrsu.cardiacare.smartcarequestionnaire;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.petrsu.cardiacare.smartcare.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;

    public MainActivity() {
    }

    String TAG = "SS-main";

    /*
    * Common functions for interaction with SmartSpace
     */


    static protected long nodeDescriptor;
    SmartCareLibrary smart;
    // Native code part end
    static protected Questionnaire questionnaire;
    // Save answer
    static protected Feedback feedback;
    Toolbar mToolbar;
    AccountStorage storage;
    String filename = "questionnaire.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*****************************
         * SS init
         *****************************/

        setRegisteredActivity();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        smart = new SmartCareLibrary();
        nodeDescriptor = smart.connectSmartSpace("X", "78.46.130.194", 10010);
        questionnaire = smart.getQuestionnaire(nodeDescriptor);
        printQuestionnaire(questionnaire);
        // id, personName, guestionnaire
        feedback = new Feedback("1 test", "Student", questionnaire.getUri());
/*
        feedback = new Feedback("1 test", "Student", "feedback");
        //создаю опросник
        questionnaire = new Questionnaire("questionaire");

        //вопрос создаем
        Question question = new Question("question", "Quest?");
        //тип ответа создаем
        Answer answer_from_sib = new Answer("answer", "Text");
        //добавляю тип в вопрос
        question.setAnswer(answer_from_sib);
        //текст ответ создаем
        AnswerItem item = new AnswerItem("111","111","");
        //добавляю текст ответа в тип
        answer_from_sib.addAnswerItem(item);
        //добавляю вопрос в опросник
        questionnaire.addQuestion(question);

        //вопрос создаем
        Question question2 = new Question("question2", "Quest2?");
        //тип ответа создаем
        Answer answer_from_sib2 = new Answer("answer2", "MultipleChoise");
        //добавляю тип в вопрос
        question2.setAnswer(answer_from_sib2);
        //текст ответ создаем
        AnswerItem item21 = new AnswerItem("221","221","item1");
        //добавляю текст ответа в тип
        answer_from_sib2.addAnswerItem(item21);
        //текст ответ создаем
        AnswerItem item22 = new AnswerItem("222","222","item2");
        //добавляю текст ответа в тип
        answer_from_sib2.addAnswerItem(item22);
        //добавляю вопрос в опросник
        questionnaire.addQuestion(question2);

        //вопрос создаем
        Question question3 = new Question("question3", "Quest3?");
        //тип ответа создаем
        Answer answer_from_sib3 = new Answer("answer3", "SingleChoise");
        //добавляю тип в вопрос
        question3.setAnswer(answer_from_sib3);
        //текст ответ создаем
        AnswerItem item31 = new AnswerItem("331","331","item1");
        //добавляю текст ответа в тип
        answer_from_sib3.addAnswerItem(item31);
        //текст ответ создаем
        AnswerItem item32 = new AnswerItem("332","332","item2");
        //добавляю текст ответа в тип
        answer_from_sib3.addAnswerItem(item32);
        //добавляю вопрос в опросник
        questionnaire.addQuestion(question3);

        //вопрос создаем
        Question question4 = new Question("question4", "Quest text4?");
        //тип ответа создаем
        Answer answer_from_sib4 = new Answer("answer4", "BipolarQuestion");
        //добавляю тип в вопрос
        question4.setAnswer(answer_from_sib4);
        //текст ответ создаем
        AnswerItem item4 = new AnswerItem("444","444","0");
        //добавляю текст ответа в тип
        answer_from_sib4.addAnswerItem(item4);
        //текст ответ создаем
        AnswerItem item42 = new AnswerItem("444","444","100");
        //добавляю текст ответа в тип
        answer_from_sib4.addAnswerItem(item42);
        //добавляю вопрос в опросник
        questionnaire.addQuestion(question4);

        //вопрос создаем
        Question question5 = new Question("question4", "Quest text4?");
        //тип ответа создаем
        Answer answer_from_sib5 = new Answer("answer4", "GuttmanScale");
        //добавляю тип в вопрос
        question5.setAnswer(answer_from_sib5);
        //текст ответ создаем
        AnswerItem item5 = new AnswerItem("444","444","0");
        //добавляю текст ответа в тип
        answer_from_sib5.addAnswerItem(item5);
        //текст ответ создаем
        AnswerItem item52 = new AnswerItem("444","444","100");
        //добавляю текст ответа в тип
        answer_from_sib5.addAnswerItem(item52);
        //добавляю вопрос в опросник
        questionnaire.addQuestion(question5);*/


/*
        Button loadFromSS = (Button)findViewById(R.id.buttonSSLoad);
        loadFromSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionnaire = getQuestionnaire(nodeDescriptor);
                printQuestionnaire(questionnaire);
            }
        });
*/
        Button saveToJson = (Button)findViewById(R.id.buttonSJson);
        saveToJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson json = new Gson();
                //String jsonStr = json.toJson(questionnaire);
                String jsonStr = json.toJson(feedback);
                System.out.println(jsonStr);
                writeData(jsonStr);
            }
        });

        Button loadFromJson = (Button)findViewById(R.id.buttonLJson);
        loadFromJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String jsonFromFile = readSavedData();
//                    Gson json = new Gson();
//                    Questionnaire qst = json.fromJson(jsonFromFile,Questionnaire.class);
//                    questionnaire = qst;
//                    printQuestionnaire(questionnaire);
                    String jsonFromFile = readSavedData();
                    Gson json = new Gson();
                    Feedback qst = json.fromJson(jsonFromFile,Feedback.class);
                    feedback = qst;
                    //printQuestionnaire(feedback);
            }
        });


        storage = new AccountStorage();
        storage.sPref = getSharedPreferences(storage.ACCOUNT_PREFERENCES, MODE_PRIVATE);

        Button AboutLoad; // About
        AboutLoad = (Button) findViewById(R.id.AboutLoad);// About
        AboutLoad.setOnClickListener(new View.OnClickListener() {// About
            @Override // About
            public void onClick(View v) {// About
                Intent intentq = new Intent(MainActivity.this, AboutActivity.class);// About
                startActivity(intentq);// About
            }// About
        });// About

        //ButtonExit
        Button ButtonExit; // Exit
        ButtonExit = (Button) findViewById(R.id.ButtonExit);// Exit
        ButtonExit.setOnClickListener(new View.OnClickListener() {// About
            @Override // About
            public void onClick(View v) {// Exit
                finish();
            }// About
        });// Exit

        Button QuestionnaireLoad; // QuestionnaireActivity
        QuestionnaireLoad = (Button) findViewById(R.id.QuestionnaireLoad);// Questionnaire
        QuestionnaireLoad.setOnClickListener(new View.OnClickListener() {// Questionaire
            @Override // Questionaire
            public void onClick(View v) {// Questionnaire
                Intent intentq = new Intent(MainActivity.this, QuestionnaireActivity.class);// Questionnaire
                startActivity(intentq);// Questionnaire

            }// Questionaire
        });// Questionnaire
    }

    public void writeData ( String data ){
        try {
            FileOutputStream fOut = openFileOutput (filename , MODE_PRIVATE );
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(data);
            osw.flush();
            osw.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String readSavedData(){
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader buffreader = new BufferedReader(isr);

            String readString = buffreader.readLine();
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine();
            }
            isr.close();
        } catch ( IOException ioe) {
            ioe.printStackTrace();
        }
        return datax.toString();
    }

    public void printQuestionnaire(Questionnaire questionnaire){
        LinkedList<Question> q = questionnaire.getQuestions();

        for (int i = 0; i < q.size(); i++) {
            Question qst = q.get(i);
            Log.i(TAG, qst.getDescription());
            Answer a = qst.getAnswer();
            //if (a.size()>0) {
            //for(int h = 0; h < a.size(); h++) {
            Log.i(TAG, a.getType());
            LinkedList<AnswerItem> ai = a.getItems();
            if (ai.size() > 0) {
                Log.i(TAG, "AnswerItem");
                for (int j = 0; j < ai.size(); j++) {
                    AnswerItem item = ai.get(j);
                    Log.i(TAG, item.getItemText());
                    LinkedList<Answer> suba = item.getSubAnswers();
                    if (suba.size() > 0) {
                        for (int k = 0; k < suba.size(); k++) {
                            Log.i(TAG, "subAnswer");
                            Answer sitem = suba.get(k);
                            Log.i(TAG, sitem.getType());
                            LinkedList<AnswerItem> sai = sitem.getItems();
                        }
                    }
                    // }
                    //}
                }
            }
        }
    }

    public void setRegisteredActivity() {
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentAccount = new Intent(this, UserAccount.class);
            startActivity(intentAccount);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        moveTaskToBack(true);
        super.onDestroy();

        smart.disconnectSmartSpace(nodeDescriptor);
        System.exit(0);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.petrsu.cardiacare.smartcarequestionnaire/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.petrsu.cardiacare.smartcarequestionnaire/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}
