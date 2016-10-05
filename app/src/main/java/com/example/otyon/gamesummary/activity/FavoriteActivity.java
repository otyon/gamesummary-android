package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.FavoriteAsyncTask;

public class FavoriteActivity extends AbstructActivity {

    protected Intent intent;
    protected Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("お気に入り一覧");

        intent = getIntent();
        new FavoriteAsyncTask(
                activity,
                intent).execute();

        ListView listView = (ListView) findViewById(R.id.favoriteListView);
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
        new FavoriteAsyncTask(
                activity,
                intent).execute();
    }
}
