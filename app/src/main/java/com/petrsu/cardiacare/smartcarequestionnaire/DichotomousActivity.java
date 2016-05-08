package com.petrsu.cardiacare.smartcarequestionnaire;

import android.os.Bundle;
import android.support.v7.widget.CardView;

import java.util.LinkedList;


/**
 * Created by Kiribaz on 08.05.16.
 */
public class DichotomousActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dichotomous_card);

        CardView DichotomousCardView = (CardView)findViewById(R.id.DichotomousCardView);
    }
}