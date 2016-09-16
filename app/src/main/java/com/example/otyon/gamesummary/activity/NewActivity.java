package com.example.otyon.gamesummary.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.util.Log;
import android.content.Intent;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.util.HtmlParseUtil;
import com.example.otyon.gamesummary.task.NewDetailAsyncTask;
import com.example.otyon.gamesummary.task.NewAsyncTask;

import java.util.ArrayList;
import java.util.Map;
import java.lang.Exception;

public class NewActivity extends AbstructActivity {

    private ArrayList<Map<String, String>> newDataLists;
    protected String baseUrl = "http://unisonleague.warotamaker.com";
    private Intent intent;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("新着一覧");

        intent = getIntent();
        newDataLists = (ArrayList<Map<String, String>>)intent.getSerializableExtra("newDataLists");

        // 後ほどファイルから習得する方法に変更
        if (newDataLists == null) {
            newDataLists = new ArrayList<Map<String, String>>();
            try {
                HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
                htmlParseUtil.connectUrll(this.baseUrl);
                newDataLists = htmlParseUtil.getNewsParseData();
                intent.putExtra("newDataLists", newDataLists);
            } catch (Exception e) {
                Log.d("error", e.toString());
            }
        }

        ListView listView = (ListView) findViewById(R.id.newListView);
        SimpleAdapter adapter = new SimpleAdapter(this,
                newDataLists,
                R.layout.list_view_parts,
                new String[]{"heding","channel"},
                new int[]{R.id.heding, R.id.channel});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                intent.putExtra("selectPosition", pos);
                intent.putExtra("remarkTitle", newDataLists.get(pos).get("heding"));

                new NewDetailAsyncTask(
                        activity,
                        intent,
                        baseUrl + newDataLists.get(pos).get("url"),
                        newDataLists.get(pos).get("site"),
                        false).execute();
            }
        });
    }

    @Override
    public void onReloadButtonClick(View v) {
        new NewAsyncTask(
                activity,
                intent,
                baseUrl,
                true).execute();
    }
}
