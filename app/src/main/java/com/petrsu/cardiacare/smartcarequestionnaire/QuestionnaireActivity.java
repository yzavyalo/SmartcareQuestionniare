package com.petrsu.cardiacare.smartcarequestionnaire;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.content.Intent;
import android.widget.TextView;

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

        LinkedList<Question> questionnaire = MainActivity.questionnaire.getQuestions();
        //get list
        //MainActivity.feedback.setResponses(MainActivity.questionnaire.getQuestions());

        int[] Types = new int[questionnaire.size()];

        for (int i = 0; i < questionnaire.size(); i++) {
            Question question = questionnaire.get(i);
            Answer answer = question.getAnswer();
            switch(answer.getType()) {
                case "Text":
                    Types[i] = RecyclerViewAdapter.TextField;
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

        //Clean
        Button buttonClean; // Clean
        buttonClean = (Button) findViewById(R.id.buttonClean);// Clean
        buttonClean.setOnClickListener(new View.OnClickListener() {// Clean
            @Override // Clean
            public void onClick(View v) {// Clean
                MainActivity.feedback = new Feedback("1 test", "Student", "feedback");
                Intent i = new Intent( QuestionnaireActivity.this , QuestionnaireActivity.this.getClass() );
                finish();
                QuestionnaireActivity.this.startActivity(i);
            }// Clean
        });// Clean
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
        EditText rx = (EditText)QuestionnaireRecyclerView.getChildAt(0).findViewById(R.id.editText);
        rx.setText("in strart");

        // буду пилить отображение ответов
        LinkedList<Question> questionnnaire = MainActivity.questionnaire.getQuestions();
        LinkedList<Response> feedback = MainActivity.feedback.getResponses();
        for (int i = 0; i < QuestionnaireRecyclerView.getChildCount(); i++) {
            Question question = questionnnaire.get(i);
            Answer answer = question.getAnswer();
            // uri respose == iri question
            Response resp = new Response(questionnnaire.get(i).getUri(), questionnnaire.get(i).getUri());
            // add to list response
            //MainActivity.feedback.addResponse(resp);
            LinkedList<ResponseItem> AnswerofUser;
            switch(answer.getType()) {
                case "Text":
                    EditText TextEdit = (EditText)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.editText);
                    for(int j = 0; j < feedback.size(); j++){
                        if(questionnnaire.get(i).getUri() == feedback.get(j).getUri()) {
                            //EditText TextEdit = (EditText)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.editText);
                            TextEdit.setText(feedback.get(j).getResponseItems().get(0).getLinkedItems().get(0).getItemText().toString());
                            TextEdit.setText("in if".toString());
                        }
                    }
                    TextEdit.setText("in Text".toString());
                    break;
                case "MultipleChoise":

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
        }*/
    }

    @Override
    public void onStop() {
        //super.onStop();
    //save result
        MainActivity.feedback = new Feedback("1 test", "Student", "feedback");
        LinkedList<Question> questionnnaire = MainActivity.questionnaire.getQuestions();

        for (int i = 0; i < QuestionnaireRecyclerView.getChildCount(); i++) {
            //getChildAt(i); return View
            Question question = questionnnaire.get(i);
            Answer answer = question.getAnswer();
            // uri respose == iri question
            Response resp = new Response(questionnnaire.get(i).getUri(), questionnnaire.get(i).getUri());
            // add to list response
            //MainActivity.feedback.addResponse(resp);

            LinkedList<ResponseItem> AnswerofUser;
            switch(answer.getType()) {
                case "Text":
                    // ответ, с ИД вответа и какими-то параметрами, разберусь потом
                    ResponseItem TextAnswer = new ResponseItem(answer.getUri(), "textItem", "fileUri");
                    // получаю 0 элемент из списка Answer, TextCard только один ответ
                    AnswerItem AnswerItemForTextField = new AnswerItem(answer.getItems().get(0));
                    // получаю значение из EditText, в других это заранее известно (только надо выбрать отмеченный)
                    AnswerItem AnswerText = new AnswerItem(AnswerItemForTextField.getUri(), AnswerItemForTextField.getItemScore(), ((EditText)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.editText)).getText().toString());
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
                    ResponseItem MultipleChoiseAnswer = new ResponseItem(answer.getUri(), "textItem", "fileUri");
                    for(int j = 0; j < answer.getItems().size(); j++) {
                        // получаю j элемент из списка Answer, MultipleChoiseCard много ответов
                        AnswerItem AnswerItemForMultipleChoise = new AnswerItem(answer.getItems().get(j));
                        // если из списка LinearLayout есть выбранный CheckBox
                        if(( (CheckBox) ((LinearLayout)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.LinearMultiple)).getChildAt(j)).isChecked()) {
                            // получаю значение из CheckBox, точнее из списка ответов к данному вопросу
                            AnswerItem AnswerMultipleChoise = new AnswerItem(AnswerItemForMultipleChoise.getUri(), AnswerItemForMultipleChoise.getItemScore(), AnswerItemForMultipleChoise.getItemText()/**/);
                            // Собираю полученное значение в ответ
                            MultipleChoiseAnswer.addLinkedAnswerItem(AnswerMultipleChoise);
                            // Собираю полученное значение в "вопрос"
                            resp.addResponseItem(MultipleChoiseAnswer);
                        }
                    }
                    if(resp.getResponseItems().size() > 0) {
                        // Собираю и отправляю в главную программу, вопрос с ответом (на который ответил пользователь)
                        MainActivity.feedback.addResponse(resp);
                    }
                    break;
                case "SingleChoise":
                    // ответ, с ИД вответа и какими-то параметрами, разберусь потом
                    ResponseItem SingleChoiseAnswer = new ResponseItem(answer.getUri(), "textItem", "fileUri");
                    for(int j = 0; j < answer.getItems().size(); j++) {
                        // получаю j элемент из списка Answer, SingleChoiseCard много ответов
                        AnswerItem AnswerItemForSingleChoise = new AnswerItem(answer.getItems().get(j));
                        // если из списка LinearLayout есть выбранный CheckBox
                        if(( (RadioButton) ((RadioGroup)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.SingleChoiceAnswers)).getChildAt(j)).isChecked()) {
                            // получаю значение из CheckBox, точнее из списка ответов к данному вопросу
                            AnswerItem AnswerSingleChoise = new AnswerItem(AnswerItemForSingleChoise.getUri(), AnswerItemForSingleChoise.getItemScore(), AnswerItemForSingleChoise.getItemText()/**/);
                            // Собираю полученное значение в ответ
                            SingleChoiseAnswer.addLinkedAnswerItem(AnswerSingleChoise);
                            // Собираю полученное значение в "вопрос"
                            resp.addResponseItem(SingleChoiseAnswer);
                        }
                    }
                    if(resp.getResponseItems().size() > 0) {
                        // Собираю и отправляю в главную программу, вопрос с ответом (на который ответил пользователь)
                        MainActivity.feedback.addResponse(resp);
                    }
                    break;
                case "BipolarQuestion":
                    // ответ, с ИД вответа и какими-то параметрами, разберусь потом
                    ResponseItem BipolarQuestionAnswer = new ResponseItem(answer.getUri(), "textItem", "fileUri");
                    // получаю 0 элемент из списка Answer, BipolarQuestionCard только один ответ
                    AnswerItem AnswerItemForBipolarQuestionField = new AnswerItem(answer.getItems().get(0));
                    // получаю значение из BipolarQuestion, в других это заранее известно (только надо выбрать отмеченный)
                    AnswerItem AnswerBipolarQuestion = new AnswerItem(AnswerItemForBipolarQuestionField.getUri(), AnswerItemForBipolarQuestionField.getItemScore(), ((TextView)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.BipolarQuestionValue)).getText().toString());
                    // Собираю полученное значение в ответ
                    BipolarQuestionAnswer.addLinkedAnswerItem(AnswerBipolarQuestion);
                    // Собираю полученное значение в "вопрос"
                    resp.addResponseItem(BipolarQuestionAnswer);
                    // Собираю и отправляю в главную программу, вопрос с ответом (на который ответил пользователь)
                    MainActivity.feedback.addResponse(resp);
                    break;
                case "Dichotomous":/*
                    // ответ, с ИД вответа и какими-то параметрами, разберусь потом
                    ResponseItem DichotomousAnswer1 = new ResponseItem(answer.getUri(), "textItem", "fileUri");
                    // получаю 0 элемент из списка Answer, Dichotomous только два ответа
                    AnswerItem AnswerItemForDichotomous1 = new AnswerItem(answer.getItems().get(0));
                    if(((RadioButton)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.DichotomousAnswer1)).isChecked()){
                        // получаю значение из CheckBox, точнее из списка ответов к данному вопросу
                        AnswerItem AnswerDichotomous1 = new AnswerItem(AnswerItemForDichotomous1.getUri(), AnswerItemForDichotomous1.getItemScore(), AnswerItemForDichotomous1.getItemText());
                        // Собираю полученное значение в ответ
                        DichotomousAnswer1.addLinkedAnswerItem(AnswerDichotomous1);
                        // Собираю полученное значение в "вопрос"
                        resp.addResponseItem(DichotomousAnswer1);
                    }
                    // ответ, с ИД вответа и какими-то параметрами, разберусь потом
                    ResponseItem DichotomousAnswer2 = new ResponseItem(answer.getUri(), "textItem", "fileUri");
                    // получаю 0 элемент из списка Answer, Dichotomous только два ответа
                    AnswerItem AnswerItemForDichotomous2 = new AnswerItem(answer.getItems().get(1));
                    if(((RadioButton)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.DichotomousAnswer2)).isChecked()){
                        // получаю значение из CheckBox, точнее из списка ответов к данному вопросу
                        AnswerItem AnswerDichotomous2 = new AnswerItem(AnswerItemForDichotomous2.getUri(), AnswerItemForDichotomous2.getItemScore(), AnswerItemForDichotomous2.getItemText());
                        // Собираю полученное значение в ответ
                        DichotomousAnswer2.addLinkedAnswerItem(AnswerDichotomous2);
                        // Собираю полученное значение в "вопрос"
                        resp.addResponseItem(DichotomousAnswer2);
                    }
                    if(resp.responseItems.size() > 0) {
                        // Собираю и отправляю в главную программу, вопрос с ответом (на который ответил пользователь)
                        MainActivity.feedback.addResponse(resp);
                    }*/
                    break;
                case "GuttmanScale":
                    // ответ, с ИД вответа и какими-то параметрами, разберусь потом
                    ResponseItem GuttmanScaleAnswer = new ResponseItem(answer.getUri(), "textItem", "fileUri");
                    for(int j = 0; j < answer.getItems().size(); j++) {
                        // получаю j элемент из списка Answer, GuttmanScaleCard много ответов
                        AnswerItem AnswerItemForGuttmanScale = new AnswerItem(answer.getItems().get(j));
                        // если из списка LinearLayout есть выбранный CheckBox
                        if(( (RadioButton) ((RadioGroup)QuestionnaireRecyclerView.getChildAt(i).findViewById(R.id.GuttmanScaleAnswers)).getChildAt(j)).isChecked()) {
                            // получаю значение из CheckBox, точнее из списка ответов к данному вопросу
                            AnswerItem AnswerGuttmanScale = new AnswerItem(AnswerItemForGuttmanScale.getUri(), AnswerItemForGuttmanScale.getItemScore(), AnswerItemForGuttmanScale.getItemText()/**/);
                            // Собираю полученное значение в ответ
                            GuttmanScaleAnswer.addLinkedAnswerItem(AnswerGuttmanScale);
                            // Собираю полученное значение в "вопрос"
                            resp.addResponseItem(GuttmanScaleAnswer);
                        }
                    }
                    if(resp.getResponseItems().size() > 0) {
                        // Собираю и отправляю в главную программу, вопрос с ответом (на который ответил пользователь)
                        MainActivity.feedback.addResponse(resp);
                    }
                    break;
                case "LikertScale":

                    break;
                case "ContinuousScale":

                    break;
                default:
            }
        }
        super.onStop();
    }
}

