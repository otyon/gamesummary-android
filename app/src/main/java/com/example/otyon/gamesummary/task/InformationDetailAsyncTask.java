package com.example.otyon.gamesummary.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.util.HtmlParseUtil;

import java.util.ArrayList;
import java.util.Map;

public class InformationDetailAsyncTask extends AsyncTask<Void, Boolean, Boolean> {

    private Intent intent;
    private Activity activity;
    private String url;

    private String informationDetailOuterHtml;

    public InformationDetailAsyncTask(Activity activity, Intent intent, String url) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... value) {
        Log.d("debug", "doInBackground が実行されました");
        try {
            HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
            htmlParseUtil.connectUrll(url);
            informationDetailOuterHtml = htmlParseUtil.getInformationDetailOuterHtml();
        } catch (Exception e) {
            Log.d("error", e.toString());
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("debug", "onPostExecuteが実行されました");
        Log.d("debug", result.toString());

        if (result) {
            WebView webView = (WebView)activity.findViewById(R.id.information_webview);
            webView.loadData(informationDetailOuterHtml, "text/html", "utf-8");
            webView.loadDataWithBaseURL(null, informationDetailOuterHtml, "text/html", "utf-8", null);
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
    }
}
