package com.example.otyon.gamesummary.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.util.HtmlParseUtil;

import java.util.ArrayList;
import java.util.Map;

public class BbsListAsyncTask extends AsyncTask<Void, Boolean, Boolean> {

    private Intent intent;
    private Activity activity;
    private String url;
    private ArrayList<Map<String, String>> bbsDataLists = new ArrayList<Map<String, String>>();

    public BbsListAsyncTask(Activity activity, Intent intent, String url) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        ListView listView = (ListView)activity.findViewById(R.id.bbsListView);
//        SimpleAdapter adapter = new SimpleAdapter(activity,
//                bbsDataLists,
//                R.layout.bbs_listview_parts,
//                new String[]{"",""},
//                new int[]{1, 2});
//        listView.setAdapter(adapter);
    }

    @Override
    protected Boolean doInBackground(Void... value) {
        Log.d("BbsListAsyncTask", "doInBackgroundが実行されました");

        try {
            HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
            htmlParseUtil.connectUrll(url);
            bbsDataLists = htmlParseUtil.getBbsParseData();
        } catch (Exception e) {
            Log.d("error", e.toString());
            return false;
        }

        Log.d("BbsListAsyncTask", bbsDataLists.toString());
        intent.putExtra("newDataLists", bbsDataLists);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("BbsListAsyncTask", "onPostExecuteが実行されました");
        Log.d("BbsListAsyncTask", result.toString());

        if (result) {
            ListView listView = (ListView)activity.findViewById(R.id.bbsListView);
            SimpleAdapter adapter = new SimpleAdapter(activity,
                    bbsDataLists,
                    R.layout.bbs_listview_parts,
                    new String[]{"",""},
                    new int[]{1, 2}){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = convertView;
                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.bbs_listview_parts, parent, false);
                    } else {
                        view = convertView;
                    }
                    Map<String, String> dataLists = bbsDataLists.get(position);
                    //ImageView bbsRserImage = (ImageView)activity.findViewById(R.id.bbsUserImage);

                    TextView bbsUserName = (TextView)view.findViewById(R.id.bbsUserName);
                    bbsUserName.setText(dataLists.get("bbs_user_name"));
                    TextView commentTitle = (TextView)view.findViewById(R.id.comment_title);
                    commentTitle.setText(dataLists.get("comment_title"));
                    TextView bbsPostTime = (TextView)view.findViewById(R.id.bbsPostTime);
                    bbsPostTime.setText(dataLists.get("bbs_post_time"));
                    TextView commentNum = (TextView)view.findViewById(R.id.comment_num);
                    commentNum.setText(dataLists.get("comment_num"));
                    return view;
                }
            };
            listView.setAdapter(adapter);
            Log.d("BbsListAsyncTask", "更新完了しました");
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
    }
}
