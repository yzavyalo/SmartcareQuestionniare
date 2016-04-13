package com.petrsu.cardiacare.smartcarequestionnaire;

import android.app.AlertDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;


import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

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

    // Native code part begin
    static {
        System.loadLibrary("smartcare");
    }

    String TAG = "SS-main";

    /*
    * Common functions for interaction with SmartSpace
     */
    public native long connectSmartSpace(String name, String ip, int port);

    public native void disconnectSmartSpace(long nodeDescriptor);

    public native Questionnaire getQuestionnaire(long nodeDescriptor);

    static protected long nodeDescriptor;
    // Native code part end
    Questionnaire questionnaire;
    Toolbar mToolbar;

    String filename = "questionnaire.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*****************************
         * SS init
         *****************************/


        nodeDescriptor = connectSmartSpace("X", "78.46.130.194", 10010);
        if (nodeDescriptor == -1) {
            return;
        }
        setRegisteredActivity();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Button loadFromSS = (Button)findViewById(R.id.buttonSSLoad);
        loadFromSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionnaire = getQuestionnaire(nodeDescriptor);
                printQuestionnaire(questionnaire);

            }
        });

        Button saveToJson = (Button)findViewById(R.id.buttonSJson);
        saveToJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson json = new Gson();
                String jsonStr = json.toJson(questionnaire);
                System.out.println(jsonStr);
                writeData(jsonStr);
            }
        });

        Button loadFromJson = (Button)findViewById(R.id.buttonLJson);
        loadFromJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jasonFromFile = readSavedData();
                    Gson json = new Gson();
                    Questionnaire qst = json.fromJson(jasonFromFile,Questionnaire.class);
                    printQuestionnaire(qst);
            }
        });
    }

    public void writeData ( String data ){
        try {
            FileOutputStream fOut = openFileOutput(filename, MODE_PRIVATE);
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

    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, getString(R.string.action_settings), Toast.LENGTH_LONG).show();
                break;

            case R.id.action_exit:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Вы действительно хотите выйти?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setNegativeButton("Нет", null).show();
    };

    public void onDestroy() {
        moveTaskToBack(true);
        super.onDestroy();

        disconnectSmartSpace(nodeDescriptor);
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

