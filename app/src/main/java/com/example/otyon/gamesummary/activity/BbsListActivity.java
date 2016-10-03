package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.BbsListAsyncTask;

public class BbsListActivity extends AbstructActivity {

    protected Intent intent;
    protected Activity activity = this;
    protected String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_list);

        TextView tileText = (TextView) findViewById(R.id.header_title);
        tileText.setText("BBS一覧");
        intent = getIntent();
        baseUrl = this.intent.getStringExtra("baseUrl");

        Log.d("BbsListActivity", "onCreateが実行されました");
        Log.d("BbsListActivity", "baseUrl : " + baseUrl);

        new BbsListAsyncTask(
                activity,
                intent,
                baseUrl).execute();

        ListView listView = (ListView) findViewById(R.id.bbsListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            }
        });
    }

    @Override
    public void onBulletionBoardButtonClick(View v) {}
}
