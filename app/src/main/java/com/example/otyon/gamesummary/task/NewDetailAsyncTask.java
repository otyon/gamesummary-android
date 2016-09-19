package com.example.otyon.gamesummary.task;

import android.os.AsyncTask;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private ProgressBar progressBar;
    private  ArrayList<Map<String, String>> nextDataLists;

    public NewDetailAsyncTask(Activity activity, Intent intent, String url, String site) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
        this.site = site;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        LayoutInflater inflater   = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout headerLayout = (LinearLayout)inflater.inflate(R.layout.common_header, null);
//        View loadLayout = inflater.inflate(R.layout.load_view_parts, null);
//        progressBar = (ProgressBar)loadLayout.findViewById(R.id.load_progressbar);
//        progressBar.setMax(100);
//         headerLayout.addView(loadLayout);
    }

    @Override
    protected Boolean doInBackground(Void... value) {
        Log.d("debug", "doInBackground が実行されました");

        Log.d("debug", url);
        Log.d("debug", site);

        nextDataLists = new ArrayList<Map<String, String>>();
        try {
            HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
            htmlParseUtil.connectUrll(url);

            switch(site) {
                case "ユニゾンリーグ攻略サイト":
                    nextDataLists = htmlParseUtil.getUnKouryakuParseData();
                    break;
                case "ユニゾンリーグ最速攻略まとめ":
                    nextDataLists = htmlParseUtil.getUnSaisokuKouryakuParseData();
                    break;
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
            LinearLayout commentLayout = (LinearLayout)activity.findViewById(R.id.commnet_layout);
            commentLayout.removeAllViews();
            for(Map<String, String> nextData : nextDataLists) {
                Log.d("debug", nextData.get("remarkHeader"));
                Log.d("debug", nextData.get("remarkContents"));

                TextView remarkHeader = new TextView(activity);
                remarkHeader.setText(nextData.get("remarkHeader"));
                remarkHeader.setTextSize(8);
                remarkHeader.setTextColor(activity.getResources().getColor(R.color.black));
                remarkHeader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                ViewGroup.MarginLayoutParams mp = (ViewGroup.MarginLayoutParams)remarkHeader.getLayoutParams();
                mp.setMargins(0,5,0,0);
                remarkHeader.setLayoutParams(mp);
                commentLayout.addView(remarkHeader);

                TextView remarkContents = new TextView(activity);
                remarkContents.setText(nextData.get("remarkContents"));
                remarkContents.setTextSize(10);
                remarkContents.setTextColor(activity.getResources().getColor(R.color.black));
                remarkContents.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                mp = (ViewGroup.MarginLayoutParams)remarkContents.getLayoutParams();
                mp.setMargins(0,5,0,0);
                remarkContents.setLayoutParams(mp);
                commentLayout.addView(remarkContents);
            }
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
    }
}
