package com.petrsu.cardiacare.smartcarequestionnaire;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    public Context context = this;//getBaseContext();
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
            // uri respose == iri question
            Response resp = new Response(q.get(i).getUri(), q.get(i).getUri());
            // add to list response
            MainActivity.feedback.addResponse(resp);
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
                    /*
                    LinkedList <AnswerItem> ai = a.getItems();
                    if (ai.size() > 0) {
                        //RadioGroup SingleChoiceGroup = (RadioGroup) findViewById(R.id.SingleChoiceAnswers);

                        LinearLayout SingleLayout = (LinearLayout) findViewById(R.id.RelativeSingle);
                        RadioGroup SingleChoiceGroup = new RadioGroup(this);
                        AnswerItem item;
                        for (int j = 0; j < ai.size(); j++) {
                            RadioButton SingleChoiceAnswer = new RadioButton(this);
                            item = ai.get(j);
                            SingleChoiceAnswer.setText(item.getItemText());
                            SingleChoiceGroup.addView(SingleChoiceAnswer);
                        }
                        SingleLayout.addView(SingleChoiceGroup);
                    }
                    */
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
    }
}

