package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.InformationDetailAsyncTask;

import java.util.ArrayList;
import java.util.Map;

public class InformationDetailActivity extends AbstructActivity {

    private int selectPosition = 0;
    private int beforePosition = 0;
    private int nextPosition = 1;
    private ArrayList<Map<String, String>> informationDataList;
    private Intent intent;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);

        Intent intent = getIntent();
        this.intent = intent;
        informationDataList = (ArrayList<Map<String, String>>)intent.getSerializableExtra("informationDataLists");

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("公式お知らせ詳細");

        WebView webView = (WebView)findViewById(R.id.information_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setLoadWithOverviewMode( true );
        webView.getSettings().setUseWideViewPort( true );

        Button beforeButton = (Button)findViewById(R.id.information_before_button);
        Button nextButton = (Button)findViewById(R.id.information_next_button);

        int selectPosition  = intent.getIntExtra("selectPosition", 0);
        this.selectPosition = selectPosition;
        beforePosition = selectPosition - 1;
        nextPosition   = selectPosition + 1;
        if (beforePosition < 0) {
            beforeButton.setEnabled(false);
        }
        if (nextPosition > informationDataList.size()) {
            nextButton.setEnabled(false);
        }

        new InformationDetailAsyncTask(
                activity,
                intent,
                informationDataList.get(selectPosition).get("url")).execute();
    }

    @Override
    public void onReloadButtonClick(View v) {
        new InformationDetailAsyncTask(
                activity,
                intent,
                informationDataList.get(selectPosition).get("url")).execute();
    }

    public void onBeforeButonClick(View v) {

        Button beforeButton = (Button)findViewById(R.id.information_before_button);
        Button nextButton   = (Button)findViewById(R.id.information_next_button);
        selectPosition = beforePosition;
        beforePosition = selectPosition - 1;
        nextPosition   = selectPosition + 1;
        if (beforePosition < 0) {
            beforeButton.setEnabled(false);
        } else {
            beforeButton.setEnabled(true);
        }
        if (nextPosition > informationDataList.size()) {
            nextButton.setEnabled(false);
        } else {
            nextButton.setEnabled(true);
        }

        new InformationDetailAsyncTask(
                activity,
                intent,
                informationDataList.get(selectPosition).get("url")).execute();
    }

    public void onNextButonClick(View v) {
        Button beforeButton = (Button)findViewById(R.id.information_before_button);
        Button nextButton   = (Button)findViewById(R.id.information_next_button);

        selectPosition = nextPosition;
        beforePosition = selectPosition - 1;
        nextPosition   = selectPosition + 1;
        if (beforePosition < 0) {
            beforeButton.setEnabled(false);
        } else {
            beforeButton.setEnabled(true);
        }
        if (nextPosition > informationDataList.size()) {
            nextButton.setEnabled(false);
        } else {
            nextButton.setEnabled(true);
        }

        new InformationDetailAsyncTask(
                activity,
                intent,
                informationDataList.get(selectPosition).get("url")).execute();
    }
}
