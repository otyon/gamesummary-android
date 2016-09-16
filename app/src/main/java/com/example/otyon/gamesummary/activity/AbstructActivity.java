package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.otyon.gamesummary.R;

public abstract class AbstructActivity extends Activity {

    public void onBackButtonClick(View v) {
        this.onBackPressed();
    }

    public void onReloadButtonClick(View v) {
        Toast.makeText(this, "リロードボタンが押されました", Toast.LENGTH_LONG).show();
    }

    public void onNewButtonClick(View v) {
        Toast.makeText(this, "新着ボタンが押されました", Toast.LENGTH_LONG).show();
    }

    public void onInfomationButtonClick(View v) {
        Toast.makeText(this, "公式ボタンが押されました", Toast.LENGTH_LONG).show();
    }

    public void onDictionaryButtonClick(View v) {
        Toast.makeText(this, "図鑑ボタンが押されました", Toast.LENGTH_LONG).show();
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
