package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.DictionaryDetailAsyncTask;

import java.util.ArrayList;
import java.util.Map;

public class DictionaryDetailActivity extends AbstructActivity {

    protected ArrayList<Map<String, String>> dictionaryDataLists;
    protected Intent intent;
    protected Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_detail);

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("図鑑詳細");
        this.intent = getIntent();
        int selectPosition = intent.getIntExtra("selectPosition", 0);
        dictionaryDataLists = (ArrayList<Map<String, String>>)intent.getSerializableExtra("dictionaryDataLists");

        Log.d("DictionaryDetail", dictionaryDataLists.get(selectPosition).get("url"));

        new DictionaryDetailAsyncTask(
                activity,
                this.intent,
                dictionaryDataLists.get(selectPosition).get("url")).execute();
    }
}
