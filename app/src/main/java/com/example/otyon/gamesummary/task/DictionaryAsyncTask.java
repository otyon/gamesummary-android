package com.example.otyon.gamesummary.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    private ArrayList<Map<String, String>> DictonaryDataLists = new ArrayList<Map<String, String>>();

    public DictionaryAsyncTask(Activity activity, Intent intent, String url) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ListView listView = (ListView)activity.findViewById(R.id.dictionaryListView);
        SimpleAdapter adapter = new SimpleAdapter(activity,
                                                    DictonaryDataLists,
                                                    R.layout.content_dictionary_parts,
                                                    new String[]{"char_no","char_name"},
                                                    new int[]{R.id.information_category, R.id.information_time});
        listView.setAdapter(adapter);
    }

    @Override
    protected Boolean doInBackground(Void... value) {
        Log.d("DictionaryAsyncTask", "doInBackgroundが実行されました");

        try {
            HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
            htmlParseUtil.connectUrll(url);
            DictonaryDataLists = htmlParseUtil.getDictionaryParseData();
        } catch (Exception e) {
            Log.d("error", e.toString());
            return false;
        }

        Log.d("DictionaryAsyncTask", DictonaryDataLists.toString());
        intent.putExtra("informationDataLists", DictonaryDataLists);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if (result) {
            ListView listView = (ListView)activity.findViewById(R.id.informationListView);
            SimpleAdapter adapter = new SimpleAdapter(activity,
                    DictonaryDataLists,
                    R.layout.information_listview_parts,
                    new String[]{"information_category","information_time"},
                    new int[]{R.id.information_category, R.id.information_time}){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = convertView;
                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.information_listview_parts, parent, false);
                    } else {
                        view = convertView;
                    }
                    Map<String, String> informationData = DictonaryDataLists.get(position);
                    Log.d("debug", informationData.toString());

                    TextView informationCategory = (TextView)view.findViewById(R.id.information_category);
                    informationCategory.setText(informationData.get("category"));

                    TextView informationTime = (TextView)view.findViewById(R.id.information_time);
                    informationTime.setText(informationData.get("time"));

                    LinearLayout layout = (LinearLayout)view.findViewById(R.id.information_contents_layout);
                    layout.removeAllViews();
                    Log.d("debug InformationTask", layout.toString());
                    switch(informationData.get("category")) {
                        case "お知らせ":
                            Log.d("debug InformationTask", "おしらせが実行されました");
                            informationCategory.setBackgroundColor(activity.getResources().getColor(R.color.red));
                            TextView informationContents0 = new TextView(activity.getApplicationContext());
                            informationContents0.setText(informationData.get("title_word"));
                            informationContents0.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            //ViewGroup.MarginLayoutParams mp0 = (ViewGroup.MarginLayoutParams)informationContents0.getLayoutParams();
                            //mp0.setMargins(0,10,10,0);
                            informationContents0.setGravity(Gravity.CENTER);
                            informationContents0.setTextSize(15);
                            informationContents0.setTypeface(null, Typeface.BOLD);
                            layout.addView(informationContents0);
                            Log.d("debug InformationTask", "おしらせが終了しました");
                            break;
                        case "重 要":
                            Log.d("debug InformationTask", "重要が実行されました");
                            informationCategory.setBackgroundColor(activity.getResources().getColor(R.color.red));
                            if (!informationData.get("image_url").equals("")) {
                                ProgressBar progressBar = new ProgressBar(activity.getApplicationContext(),null,android.R.attr.progressBarStyle);
                                layout.addView(progressBar);
                                layout.setTag(informationData.get("image_url"));
                                new ImageGetTask(layout).execute(informationData.get("image_url"));
                            } else {
                                TextView informationContents1 = new TextView(activity.getApplicationContext());
                                informationContents1.setText(informationData.get("title_word"));
                                informationContents1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                                //ViewGroup.MarginLayoutParams mp1 = (ViewGroup.MarginLayoutParams)informationContents1.getLayoutParams();
                                //mp1.setMargins(0,10,10,0);
                                informationContents1.setGravity(Gravity.CENTER);
                                informationContents1.setTextSize(15);
                                informationContents1.setTypeface(null, Typeface.BOLD);
                                layout.addView(informationContents1);
                            }
                            Log.d("debug InformationTask", "重要が終了しました");
                            break;
                        case "イベント":
                            Log.d("debug InformationTask", "イベントが実行されました");
                            informationCategory.setBackgroundColor(activity.getResources().getColor(R.color.orange));
                            ProgressBar progressBar = new ProgressBar(activity.getApplicationContext(),null,android.R.attr.progressBarStyle);
                            progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            //ViewGroup.MarginLayoutParams mp2 = (ViewGroup.MarginLayoutParams)progressBar.getLayoutParams();
                            //mp2.setMargins(0,10,10,0);
                            layout.addView(progressBar);
                            layout.setTag(informationData.get("image_url"));
                            new ImageGetTask(layout).execute(informationData.get("image_url"));
                            Log.d("debug InformationTask", "イベントが終了しました");
                            break;
                        case "不具合":
                            Log.d("debug InformationTask", "不具合が実行しました");
                            informationCategory.setBackgroundColor(activity.getResources().getColor(R.color.gray));
                            TextView informationContents2 = new TextView(activity.getApplicationContext());
                            informationContents2.setText(informationData.get("title_word"));
                            informationContents2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            //ViewGroup.MarginLayoutParams mp3 = (ViewGroup.MarginLayoutParams)informationContents2.getLayoutParams();
                            //mp3.setMargins(0,10,10,0);
                            informationContents2.setGravity(Gravity.CENTER);
                            informationContents2.setTextSize(15);
                            informationContents2.setTypeface(null, Typeface.BOLD);
                            layout.addView(informationContents2);
                            Log.d("debug InformationTask", "不具合が終了しました");
                            break;
                    }

                    return view;
                }
            };
            listView.setAdapter(adapter);
            Log.d("DictionaryAsyncTask", "更新完了しました");
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
    }
}
