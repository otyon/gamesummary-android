package com.example.otyon.gamesummary.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.util.HtmlParseUtil;

import java.util.ArrayList;
import java.util.Map;

public class NewAsyncTask extends AsyncTask<Void, Boolean, Boolean> {

    private Intent intent;
    private Activity activity;
    private String url;
    private String site;
    private boolean isReload;
    private ProgressBar progressBar;
    private ArrayList<Map<String, String>> newDataLists = new ArrayList<Map<String, String>>();

    public NewAsyncTask(Activity activity, Intent intent, String url, boolean isReload) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
        this.isReload = isReload;
    }

    public NewAsyncTask(Activity activity, Intent intent, String url, String site, boolean isReload) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
        this.site = site;
        this.isReload = isReload;
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
        ListView listView = (ListView)activity.findViewById(R.id.newListView);
        SimpleAdapter adapter = new SimpleAdapter(activity,
                newDataLists,
                R.layout.list_view_parts,
                new String[]{"heding","channel"},
                new int[]{R.id.heding, R.id.channel});
        listView.setAdapter(adapter);
    }

    @Override
    protected Boolean doInBackground(Void... value) {
        Log.d("debug NewAsyncTask", "doInBackgroundが実行されました");

        try {
            HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
            htmlParseUtil.connectUrll(url);
            newDataLists = htmlParseUtil.getNewsParseData();
        } catch (Exception e) {
            Log.d("error", e.toString());
            return false;
        }

        Log.d("debug NewAsyncTask", newDataLists.toString());
        intent.putExtra("newDataLists", newDataLists);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("debug NewAsyncTask", "onPostExecuteが実行されました");
        Log.d("debug NewAsyncTask", result.toString());

        if (result) {
            if (!isReload) {
//                intent.setClassName("com.example.otyon.gamesummary", "com.example.otyon.gamesummary.activity.NewDetailActivity");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                activity.startActivity(intent);
            } else {
                ListView listView = (ListView)activity.findViewById(R.id.newListView);
                SimpleAdapter adapter = new SimpleAdapter(activity,
                        newDataLists,
                        R.layout.list_view_parts,
                        new String[]{"heding","channel"},
                        new int[]{R.id.heding, R.id.channel});
                listView.setAdapter(adapter);

                Log.d("debug NewAsyncTask", "更新完了しました");
            }
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
    }
}
