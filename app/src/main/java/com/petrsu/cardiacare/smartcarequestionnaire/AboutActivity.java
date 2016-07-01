package com.petrsu.cardiacare.smartcarequestionnaire;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("About");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Инициализируем компонент
        TextView AboutText = (TextView)findViewById(R.id.AboutText);
        try {
            InputStream inputStream = openFileInput("questionnaire.json");
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                inputStream.close();
                AboutText.setText(builder.toString());
            }
        } catch (Throwable t) {
            AboutText.setText("Error !");
        }

        // задаём текст
        //AboutText.setText("Hello Kitty!");
        // или с использованием текстовых ресурсов
        //textView.setText(R.string.hello);
    }

}
