package com.example.otyon.gamesummary.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.util.Log;
import android.content.Intent;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.util.HtmlParseUtil;
import com.example.otyon.gamesummary.task.NewAsyncTask;

import java.util.ArrayList;
import java.util.Map;
import java.lang.Exception;

public class NewActivity extends AbstructActivity {

    protected ArrayList<Map<String, String>> newDataLists;
    protected String baseUrl = "http://unisonleague.warotamaker.com";
    protected Intent intent;
    protected Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("新着一覧");

        intent = getIntent();
        newDataLists = (ArrayList<Map<String, String>>)intent.getSerializableExtra("newDataLists");

        new NewAsyncTask(
                activity,
                intent,
                baseUrl).execute();

        ListView listView = (ListView) findViewById(R.id.newListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                intent.putExtra("selectPosition", pos);
                intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.NewDetailActivity");
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void onReloadButtonClick(View v) {
        new NewAsyncTask(
                activity,
                intent,
                baseUrl).execute();
    }

    @Override
    public void onNewButtonClick(View v) {
        this.onReloadButtonClick(v);
    }
}
