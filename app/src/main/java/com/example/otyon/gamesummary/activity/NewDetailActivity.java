package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.NewAsyncTask;
import com.example.otyon.gamesummary.task.NewDetailAsyncTask;

public class NewDetailActivity extends AbstructActivity {

    private int selectPosition = 0;
    private int beforePosition = 0;
    private int nextPosition = 1;
    private ArrayList<Map<String, String>> newDataList;
    private Intent intent;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        this.intent = intent;
        newDataList = (ArrayList<Map<String, String>>)intent.getSerializableExtra("newDataLists");

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("新着詳細");

        Button beforeButton = (Button)findViewById(R.id.before_button);
        Button nextButton = (Button)findViewById(R.id.next_button);

        int selectPosition  = intent.getIntExtra("selectPosition", 0);
        this.selectPosition = selectPosition;
        beforePosition = selectPosition - 1;
        nextPosition   = selectPosition + 1;
        if (beforePosition < 0) {
            beforeButton.setEnabled(false);
        }
        if (nextPosition > newDataList.size()) {
            nextButton.setEnabled(false);
        }

        TextView newsTitle = (TextView)findViewById(R.id.news_title);
        newsTitle.setText(newDataList.get(selectPosition).get("heding"));

        ArrayList<Map<String, String>> nextDataLists = (ArrayList<Map<String, String>>)intent.getSerializableExtra("nextDataLists");
        Log.d("debug", nextDataLists.toString());

        LinearLayout commentLayout = (LinearLayout)findViewById(R.id.commnet_layout);

        for(Map<String, String> nextData : nextDataLists) {
            Log.d("debug", nextData.get("remarkHeader"));
            Log.d("debug", nextData.get("remarkContents"));

            TextView remarkHeader = new TextView(this);
            remarkHeader.setText(nextData.get("remarkHeader"));
            remarkHeader.setTextSize(8);
            remarkHeader.setTextColor(getResources().getColor(R.color.black));
            remarkHeader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                       LinearLayout.LayoutParams.WRAP_CONTENT));
            MarginLayoutParams mp = (MarginLayoutParams)remarkHeader.getLayoutParams();
            mp.setMargins(0,5,0,0);
            remarkHeader.setLayoutParams(mp);
            commentLayout.addView(remarkHeader);

            TextView remarkContents = new TextView(this);
            remarkContents.setText(nextData.get("remarkContents"));
            remarkContents.setTextSize(10);
            remarkContents.setTextColor(getResources().getColor(R.color.black));
            remarkContents.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            mp = (MarginLayoutParams)remarkContents.getLayoutParams();
            mp.setMargins(0,5,0,0);
            remarkContents.setLayoutParams(mp);
            commentLayout.addView(remarkContents);
        }
    }

    @Override
    public void onReloadButtonClick(View v) {
        new NewDetailAsyncTask(
                activity,
                intent,
                newDataList.get(selectPosition).get("url"),
                newDataList.get(selectPosition).get("site"),
                true).execute();
    }

    public void onBeforeButonClick(View v) {

        Button beforeButton = (Button)findViewById(R.id.before_button);
        Button nextButton   = (Button)findViewById(R.id.next_button);
        selectPosition = beforePosition;
        beforePosition = selectPosition - 1;
        nextPosition   = selectPosition + 1;
        if (beforePosition < 0) {
            beforeButton.setEnabled(false);
        } else {
            beforeButton.setEnabled(true);
        }
        if (nextPosition > newDataList.size()) {
            nextButton.setEnabled(false);
        } else {
            nextButton.setEnabled(true);
        }

        TextView newsTitle = (TextView)findViewById(R.id.news_title);
        newsTitle.setText(newDataList.get(selectPosition).get("heding"));

        new NewDetailAsyncTask(
                activity,
                intent,
                newDataList.get(selectPosition).get("url"),
                newDataList.get(selectPosition).get("site"),
                true).execute();
    }

    public void onNextButonClick(View v) {
        Button beforeButton = (Button)findViewById(R.id.before_button);
        Button nextButton   = (Button)findViewById(R.id.next_button);

        selectPosition = nextPosition;
        beforePosition = selectPosition - 1;
        nextPosition   = selectPosition + 1;
        if (beforePosition < 0) {
            beforeButton.setEnabled(false);
        } else {
            beforeButton.setEnabled(true);
        }
        if (nextPosition > newDataList.size()) {
            nextButton.setEnabled(false);
        } else {
            nextButton.setEnabled(true);
        }

        TextView newsTitle = (TextView)findViewById(R.id.news_title);
        newsTitle.setText(newDataList.get(selectPosition).get("heding"));

        new NewDetailAsyncTask(
                activity,
                intent,
                newDataList.get(selectPosition).get("url"),
                newDataList.get(selectPosition).get("site"),
                true).execute();
    }
}
