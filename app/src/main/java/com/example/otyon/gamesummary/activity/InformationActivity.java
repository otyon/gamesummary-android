package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.InformationAsyncTask;

import java.util.ArrayList;
import java.util.Map;

public class InformationActivity extends AbstructActivity {

    protected ArrayList<Map<String, String>> informationDataLists;
    protected String baseUrl = "http://app.ja.unisonleague.com/app_jp/information.php?action_information_past=true&lang=jp";
    protected Intent intent;
    protected Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("公式おしらせ一覧");
        this.intent = getIntent();

        new InformationAsyncTask(
                activity,
                this.intent,
                baseUrl).execute();

        ListView listView = (ListView) findViewById(R.id.informationListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            }
        });
    }

    public void onInformationDetailButonClick(View v){
    }
}
