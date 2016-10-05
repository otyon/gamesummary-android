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
        Toast.makeText(this, "onNewButtonClick", Toast.LENGTH_LONG).show();
        intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.NewActivity");
        this.startActivity(intent);
    }

    public void onInfomationButtonClick(View v) {
        Toast.makeText(this, "onInfomationButtonClick", Toast.LENGTH_LONG).show();
        intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.InformationActivity");
        this.startActivity(intent);
     }

    public void onDictionaryButtonClick(View v) {
        Toast.makeText(this, "onDictionaryButtonClick", Toast.LENGTH_LONG).show();
        intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.DictionaryActivity");
        this.startActivity(intent);
    }

    public void onBulletionBoardButtonClick(View v) {
        intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.BbsTopActivity");
        this.startActivity(intent);
    }

    public void onFavoriteButtonClick(View v) {
        intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.FavoriteActivity");
        this.startActivity(intent);
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
