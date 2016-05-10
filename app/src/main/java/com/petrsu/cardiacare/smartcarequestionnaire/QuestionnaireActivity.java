package com.petrsu.cardiacare.smartcarequestionnaire;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.util.LinkedList;

public class QuestionnaireActivity extends AppCompatActivity {

    RecyclerView QuestionnaireRecyclerView;
    RecyclerView.Adapter QuestionnaireAdapter;
    RecyclerView.LayoutManager QuestionnaireLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        QuestionnaireRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        QuestionnaireRecyclerView.setHasFixedSize(true);

        QuestionnaireLayoutManager= new LinearLayoutManager(this);
        QuestionnaireRecyclerView.setLayoutManager(QuestionnaireLayoutManager);

        QuestionnaireAdapter = new RecyclerViewAdapter(MainActivity.questionnaire.getQuestions());
        QuestionnaireRecyclerView.setAdapter(QuestionnaireAdapter);
    }
}

