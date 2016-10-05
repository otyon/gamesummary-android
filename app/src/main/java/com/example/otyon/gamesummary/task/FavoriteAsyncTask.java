package com.example.otyon.gamesummary.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.util.FileWriteObjectUtil;

import java.util.ArrayList;
import java.util.Map;

public class FavoriteAsyncTask extends AsyncTask<Void, Boolean, Boolean> {

    private String filename = "favoriteList.dat";

    private Intent intent;
    private Activity activity;
    private ArrayList<Map<String, String>> newDataLists = new ArrayList<Map<String, String>>();

    public FavoriteAsyncTask(Activity activity, Intent intent) {
        this.intent   = intent;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... value) {
        Log.d("FavoriteAsyncTask", "doInBackgroundが実行されました");

        try {
            Object result = FileWriteObjectUtil.load(activity, filename);
            if (result != null) {
                newDataLists = (ArrayList<Map<String, String>>)result;
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
            return false;
        }

        Log.d("FavoriteAsyncTask", newDataLists.toString());
        intent.putExtra("newDataLists", newDataLists);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("FavoriteAsyncTask", "onPostExecuteが実行されました");
        Log.d("FavoriteAsyncTask", result.toString());

        if (result) {
            ListView listView = (ListView)activity.findViewById(R.id.favoriteListView);
            SimpleAdapter adapter = new SimpleAdapter(activity,
                    newDataLists,
                    R.layout.favorite_listview_parts,
                    new String[]{"heding","channel"},
                    new int[]{R.id.heding, R.id.channel});
            listView.setAdapter(adapter);
            Log.d("FavoriteAsyncTask", "更新完了しました");
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
    }
}
