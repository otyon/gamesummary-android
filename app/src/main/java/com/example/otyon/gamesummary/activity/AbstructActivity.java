package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.InformationAsyncTask;
import com.example.otyon.gamesummary.task.NewAsyncTask;

import java.util.ArrayList;
import java.util.Map;

public abstract class AbstructActivity extends Activity {

    protected Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.intent = intent;
}

    public void onBackButtonClick(View v) {
        this.onBackPressed();
    }

    public void onReloadButtonClick(View v) {
        Toast.makeText(this, "リロードボタンが押されました", Toast.LENGTH_LONG).show();
    }

    public void onNewButtonClick(View v) {
        intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.NewActivity");
        this.startActivity(intent);
    }

    public void onInfomationButtonClick(View v) {
        intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.InformationActivity");
        this.startActivity(intent);
     }

    public void onDictionaryButtonClick(View v) {
        intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.DictionaryActivity");
        this.startActivity(intent);
    }

    public void onBulletionBoardButtonClick(View v) {
        Toast.makeText(this, "掲示板ボタンが押されました", Toast.LENGTH_LONG).show();
    }


    public void onFavoriteButtonClick(View v) {
        Toast.makeText(this, "お気に入りボタンが押されました", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
