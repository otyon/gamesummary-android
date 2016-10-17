package com.example.otyon.gamesummary.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.util.HtmlParseUtil;

import java.util.ArrayList;
import java.util.Map;

public class DictionaryAsyncTask extends AsyncTask<Void, Boolean, Boolean> {

    private Intent intent;
    private Activity activity;
    private String url;
    private ArrayList<Map<String, String>> dictionaryDataLists = new ArrayList<Map<String, String>>();

    public DictionaryAsyncTask(Activity activity, Intent intent, String url) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        GridView gridView = (GridView)activity.findViewById(R.id.dictionaryGridView);
        SimpleAdapter adapter = new SimpleAdapter(activity,
                dictionaryDataLists,
                R.layout.dictionary_setting_parts,
                new String[]{"",""}, // TODO 後ほど修正が必要になる
                new int[]{1, 2});
        gridView.setAdapter(adapter);
    }

    @Override
    protected Boolean doInBackground(Void... value) {
        Log.d("DictionaryAsyncTask", "doInBackgroundが実行されました");

        try {
            HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
            htmlParseUtil.connectUrll(url);
            dictionaryDataLists = htmlParseUtil.getDictionaryParseData();
        } catch (Exception e) {
            Log.d("error", e.toString());
            return false;
        }

        Log.d("DictionaryAsyncTask", dictionaryDataLists.toString());
        intent.putExtra("informationDataLists", dictionaryDataLists);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if (result) {
            GridView gridView = (GridView)activity.findViewById(R.id.dictionaryGridView);
            SimpleAdapter adapter = new SimpleAdapter(activity,
                    dictionaryDataLists,
                    R.layout.dictionary_gridview_parts,
                    new String[]{"",""},
                    new int[]{1, 2}){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = convertView;
                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.dictionary_gridview_parts, parent, false);
                    } else {
                        view = convertView;
                    }
                    Map<String, String> dictionaryData = dictionaryDataLists.get(position);
                    Log.d("debug", dictionaryData.toString());

                    LinearLayout layout = (LinearLayout)view.findViewById(R.id.dictionary_column_layout);
                    layout.removeAllViews();

                    ProgressBar progressBar = new ProgressBar(activity.getApplicationContext(),null,android.R.attr.progressBarStyle);
                    layout.addView(progressBar);
                    layout.setTag(dictionaryData.get("image_url"));
                    new ImageGetTask(layout).execute(dictionaryData.get("image_url"));

                    TextView char_no = new TextView(activity.getApplicationContext());
                    char_no.setText(dictionaryData.get("char_no"));
                    char_no.setGravity(Gravity.CENTER);

                    TextView char_name = new TextView(activity.getApplicationContext());
                    char_name.setText(dictionaryData.get("char_name"));
                    char_no.setGravity(Gravity.CENTER);

                    layout.addView(char_no);
                    layout.addView(char_name);

                    return view;
                }
            };
            gridView.setAdapter(adapter);
            Log.d("DictionaryAsyncTask", "更新完了しました");
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
    }
}
