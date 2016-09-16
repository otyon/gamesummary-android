package com.example.otyon.gamesummary.task;

import android.os.AsyncTask;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.otyon.gamesummary.util.HtmlParseUtil;
import com.example.otyon.gamesummary.R;
import android.util.Log;

import java.util.ArrayList;
import java.lang.Exception;
import java.util.Map;

public class NewDetailAsyncTask extends AsyncTask<Void, Boolean, Boolean> {

    private Intent intent;
    private Activity activity;
    private String url;
    private String site;
    private boolean isReload;
    private ProgressBar progressBar;

    public NewDetailAsyncTask(Activity activity, Intent intent, String url, String site, boolean isReload) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
        this.site = site;
        this.isReload = isReload;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LayoutInflater inflater   = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout headerLayout = (LinearLayout)inflater.inflate(R.layout.common_header, null);
        View loadLayout = inflater.inflate(R.layout.load_view_parts, null);
        progressBar = (ProgressBar)loadLayout.findViewById(R.id.load_progressbar);
        progressBar.setMax(100);
         headerLayout.addView(loadLayout);
    }

    @Override
    protected Boolean doInBackground(Void... value) {
        Log.d("debug", "doInBackground が実行されました");

        Log.d("debug", url);
        Log.d("debug", site);

        ArrayList<Map<String, String>> nextDataLists = new ArrayList<Map<String, String>>();
        try {
            HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
            htmlParseUtil.connectUrll(url);

            switch(site) {
                case "ユニゾンリーグ攻略サイト":
                    // TODO
                    return false;
                case "ユニゾンリーグ最速攻略まとめ":
                    // TODO
                    return false;
                case "ユニゾンリーグ攻略まとめNews":
                    nextDataLists = htmlParseUtil.getUnKouryakuMatomeSokuhouParseData();
                    break;
                case "ユニゾンリーグ速報":
                    nextDataLists =htmlParseUtil.getUnSokuhouParseData();
                    break;
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
            return false;
        }

        Log.d("debug", nextDataLists.toString());

        intent.putExtra("nextDataLists", nextDataLists);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("debug", "onPostExecuteが実行されました");
        Log.d("debug", result.toString());

        if (result) {
            if (!isReload) {
                intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.NewDetailActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
    }
}
