package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.DictionaryAsyncTask;

import java.util.ArrayList;
import java.util.Map;

public class DictionaryActivity extends AbstructActivity {

    protected ArrayList<Map<String, String>> dictionaryDataLists;
    protected String baseUrl = "https://gamy.jp/unisonleague/dictionary/equipments?order=latest";
    protected Intent intent;
    protected Activity activity = this;
    protected FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("図鑑");
        this.intent = getIntent();

        frameLayout = (FrameLayout)findViewById(R.id.content_dictionary_framelayout);
        ToggleButton togleButton = (ToggleButton)findViewById(R.id.content_dictionary_toggleButton);
        togleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frameLayout.setVisibility(View.VISIBLE);
                } else {
                    frameLayout.setVisibility(View.GONE);
                }
            }
        });

        new DictionaryAsyncTask(
                activity,
                this.intent,
                baseUrl).execute();

        GridView gridView = (GridView) findViewById(R.id.dictionaryGridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            }
        });
    }
}
