package com.petrsu.cardiacare.smartcarequestionnaire;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.petrsu.cardiacare.smartcare.*;

import java.util.LinkedList;

public class QuestionnaireActivity extends AppCompatActivity {

    RecyclerView QuestionnaireRecyclerView;
    RecyclerView.Adapter QuestionnaireAdapter;
    RecyclerView.LayoutManager QuestionnaireLayoutManager;
    public Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        QuestionnaireRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //QuestionnaireRecyclerView.setHasFixedSize(true);

        QuestionnaireLayoutManager= new LinearLayoutManager(getApplicationContext());
        QuestionnaireRecyclerView.setLayoutManager(QuestionnaireLayoutManager);

        LinkedList<Question> q = MainActivity.questionnaire.getQuestions();
        //get list
        //MainActivity.feedback.setResponses(MainActivity.questionnaire.getQuestions());

        int[] Types = new int[q.size()];

        for (int i = 0; i < q.size(); i++) {
            Question qst = q.get(i);
            Answer a = qst.getAnswer();
            switch(a.getType()) {
                case "Text":
                    Types[i] = RecyclerViewAdapter.Tekst;
                    //= gst;
                    break;
                case "MultipleChoise":
                    Types[i] = RecyclerViewAdapter.Multiplechoice;
                    break;
                case "SingleChoise":
                    Types[i] = RecyclerViewAdapter.Singlechoice;
                    break;
                case "BipolarQuestion":
                    Types[i] = RecyclerViewAdapter.Bipolarquestion;
                    break;
                case "Dichotomous":
                    Types[i] = RecyclerViewAdapter.Dichotomous;
                    break;
                case "GuttmanScale":
                    Types[i] = RecyclerViewAdapter.Guttmanscale;
                    break;
                case "LikertScale":
                    Types[i] = RecyclerViewAdapter.Likertscale;
                    break;
                case "ContinuousScale":
                    Types[i] = RecyclerViewAdapter.Continuousscale;
                    break;
                default:
                    Types[i] = RecyclerViewAdapter.DefaultValue;
            }
        }

        QuestionnaireAdapter = new RecyclerViewAdapter(MainActivity.questionnaire.getQuestions(), Types, context);
        QuestionnaireRecyclerView.setAdapter(QuestionnaireAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    //save result
        LinkedList<Question> q = MainActivity.questionnaire.getQuestions();

        for (int i = 0; i < QuestionnaireRecyclerView.getChildCount(); i++) {
            //getChildAt(i); return View
            Question qst = q.get(i);
            Answer ans = qst.getAnswer();
            // uri respose == iri question
            Response resp = new Response(q.get(i).getUri(), q.get(i).getUri());
            // add to list response
            //MainActivity.feedback.addResponse(resp);

            LinkedList<ResponseItem> AnswerofUser;
            switch(ans.getType()) {
                case "Text":
                    // ответ, с ИД вответа и какими-то параметрами, разберусь потом
                    ResponseItem TextAnswer = new ResponseItem(ans.getUri(), "textItem", "fileUri");
                    // получаю 0 элемент из списка Answer, TextCard только один ответ
                    AnswerItem ansitforText = new AnswerItem(ans.getItems().get(0));
                    // получаю значение из EditText, в других это заранее известно (только надо выбрать отмеченный)
                    AnswerItem AnswerText = new AnswerItem(ansitforText.getUri(), ansitforText.getItemScore(), ((EditText)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.editText)).getText().toString());
                    /*
                    if(((EditText)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.editText)).getText().toString() != "") { // (EditText) -- TekstAnswer.getText()
                        AnswerItem f1 = new AnswerItem(a.getItems().get(0));
                        AnswerText = new AnswerItem(f1.getUri(), f1.getItemScore(), ((EditText)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.editText)).getText().toString());//(a.getItems().get(0));
                    }
                    */
                    // Собираю полученное значение в ответ
                    TextAnswer.addLinkedAnswerItem(AnswerText);
                    // Собираю полученное значение в "вопрос"
                    resp.addResponseItem(TextAnswer);
                    // Собираю и отправляю в главную программу, вопрос с ответом (на который ответил пользователь)
                    MainActivity.feedback.addResponse(resp);
                    break;
                case "MultipleChoise":
                    // ответ, с ИД вответа и какими-то параметрами, разберусь потом
                    ResponseItem MultipleChoiseAnswer = new ResponseItem(ans.getUri(), "textItem", "fileUri");
                    for(int j = 0; j < ans.getItems().size(); j++) {
                        // получаю j элемент из списка Answer, MultipleChoiseCard много ответов
                        AnswerItem ansitforMultipleChoise = new AnswerItem(ans.getItems().get(j));
                        // если из списка LinearLayout есть выбранный CheckBox
                        if(( (CheckBox) ((LinearLayout)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.LinearMultiple)).getChildAt(j)).isChecked()) {
                            // получаю значение из CheckBox, точнее из списка ответов к данному вопросу
                            AnswerItem AnswerMultipleChoise = new AnswerItem(ansitforMultipleChoise.getUri(), ansitforMultipleChoise.getItemScore(), ansitforMultipleChoise.getItemText()/**/);
                            // Собираю полученное значение в ответ
                            MultipleChoiseAnswer.addLinkedAnswerItem(AnswerMultipleChoise);
                            // Собираю полученное значение в "вопрос"
                            resp.addResponseItem(MultipleChoiseAnswer);
                        }
                    }
                    // Собираю и отправляю в главную программу, вопрос с ответом (на который ответил пользователь)
                    MainActivity.feedback.addResponse(resp);
                    break;
                case "SingleChoise":

                    break;
                case "BipolarQuestion":

                    break;
                case "Dichotomous":

                    break;
                case "GuttmanScale":

                    break;
                case "LikertScale":

                    break;
                case "ContinuousScale":

                    break;
                default:
            }
        }
    }
}

