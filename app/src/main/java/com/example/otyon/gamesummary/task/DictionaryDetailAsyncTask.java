package com.example.otyon.gamesummary.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.util.HtmlParseUtil;

import java.util.ArrayList;
import java.util.Map;

public class DictionaryDetailAsyncTask extends AsyncTask<Void, Boolean, Boolean> {

    private Intent intent;
    private Activity activity;
    private String url;
    private ProgressBar progressBar;
    private  ArrayList<Map<String, String>> dictionaryDetailDataLists;

    public DictionaryDetailAsyncTask(Activity activity, Intent intent, String url) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
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
        Log.d("DictionaryDetailaTask", "doInBackground が実行されました");
        dictionaryDetailDataLists = new ArrayList<Map<String, String>>();
        try {
            HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
            htmlParseUtil.connectUrll(url);
            dictionaryDetailDataLists = htmlParseUtil.getDictionaryDetailParseData();

        } catch (Exception e) {
            Log.d("error", e.toString());
            return false;
        }

        Log.d("DictionaryDetailaTask", dictionaryDetailDataLists.toString());
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("DictionaryDetailaTask", "onPostExecuteが実行されました");
        if (result) {
            for(Map<String, String> nextData : dictionaryDetailDataLists) {
                ((TextView)activity.findViewById(R.id.item_title)).setText(nextData.get("item_title"));
                LinearLayout layout = (LinearLayout)activity.findViewById(R.id.item_image_layout);
                layout.setTag(nextData.get("item_image_url"));
                new ImageGetTask(layout).execute(nextData.get("item_image_url"));
                ((TextView)activity.findViewById(R.id.item_name)).setText(nextData.get("item_name"));
                ((TextView)activity.findViewById(R.id.item_rare)).setText(nextData.get("item_rare"));
                ((TextView)activity.findViewById(R.id.item_type)).setText(nextData.get("item_type"));
                ((TextView)activity.findViewById(R.id.item_kind)).setText(nextData.get("item_kind"));
                ((TextView)activity.findViewById(R.id.item_attribute)).setText(nextData.get("item_attribute"));
                ((TextView)activity.findViewById(R.id.item_cost)).setText(nextData.get("item_cost"));
                ((TextView)activity.findViewById(R.id.item_evolution_num)).setText(nextData.get("item_evolution_num"));
                ((TextView)activity.findViewById(R.id.item_max_level)).setText(nextData.get("item_max_level"));

                ((TextView)activity.findViewById(R.id.item_attack)).setText(nextData.get("item_attack"));
                ((TextView)activity.findViewById(R.id.item_max_attack)).setText(nextData.get("item_max_attack"));
                ((TextView)activity.findViewById(R.id.item_m_attack)).setText(nextData.get("item_m_attack"));
                ((TextView)activity.findViewById(R.id.item_m_max_attack)).setText(nextData.get("item_m_max_attack"));
                ((TextView)activity.findViewById(R.id.item_defence)).setText(nextData.get("item_defence"));
                ((TextView)activity.findViewById(R.id.item_max_defence)).setText(nextData.get("item_max_defence"));
                ((TextView)activity.findViewById(R.id.item_m_defence)).setText(nextData.get("item_m_defence"));
                ((TextView)activity.findViewById(R.id.item_m_max_defence)).setText(nextData.get("item_m_max_defence"));

                ((TextView)activity.findViewById(R.id.item_skill_name)).setText(nextData.get("item_skill_name"));
                ((TextView)activity.findViewById(R.id.item_skill_effect)).setText(nextData.get("item_skill_effect"));

                ((TextView)activity.findViewById(R.id.item_comment)).setText(nextData.get("item_comment"));
            }
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
        Log.d("DictionaryDetailaTask", "onPostExecuteが終了しました");
    }
}
