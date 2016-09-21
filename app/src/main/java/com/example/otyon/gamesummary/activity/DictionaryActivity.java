package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.InformationAsyncTask;

import java.util.ArrayList;
import java.util.Map;

public class DictionaryActivity extends AbstructActivity {

    protected ArrayList<Map<String, String>> informationDataLists;
    protected String baseUrl = "http://app.ja.unisonleague.com/app_jp/information.php?action_information_past=true&lang=jp";
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
//
//        new InformationAsyncTask(
//                activity,
//                this.intent,
//                baseUrl).execute();
//
//        ListView listView = (ListView) findViewById(R.id.informationListView);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//            }
//        });
    }
//
//    public void onInformationDetailButonClick(View v){
//    }
}
