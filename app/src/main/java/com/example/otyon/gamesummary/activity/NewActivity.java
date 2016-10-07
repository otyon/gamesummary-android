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
import android.widget.Toast;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.util.FileWriteObjectUtil;
import com.example.otyon.gamesummary.util.HtmlParseUtil;
import com.example.otyon.gamesummary.task.NewAsyncTask;

import java.util.ArrayList;
import java.util.Map;
import java.lang.Exception;

public class NewActivity extends AbstructActivity {

    // 後ほど修正
    private String filename = "favoriteList.dat";

    protected ArrayList<Map<String, String>> newDataLists;
    protected ArrayList<Map<String, String>> favoriteDataLists;
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
        new NewAsyncTask(
                activity,
                intent,
                baseUrl).execute();

        Object result = FileWriteObjectUtil.load(activity, filename);
        if (result != null) {
            favoriteDataLists = (ArrayList<Map<String, String>>)result;
        } else {
            favoriteDataLists = new ArrayList<Map<String, String>>();
        }

        ListView listView = (ListView) findViewById(R.id.newListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                intent.putExtra("selectPosition", pos);
                intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.NewDetailActivity");
                activity.startActivity(intent);
            }
        });
    }

    // 後ほど修正
    public void onFavoriteRegisterClick(View v) {
        int position = (Integer)v.getTag();
        Log.d("onFavoriteRegisterClick", "position=" + position);
        newDataLists = (ArrayList<Map<String, String>>)intent.getSerializableExtra("newDataLists");
        Map<String, String> tmp = newDataLists.get(position);
        favoriteDataLists.add(tmp);

        FileWriteObjectUtil.save(activity, favoriteDataLists, filename);
        Toast.makeText(this, "お気に入りに登録しました", Toast.LENGTH_LONG).show();
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
