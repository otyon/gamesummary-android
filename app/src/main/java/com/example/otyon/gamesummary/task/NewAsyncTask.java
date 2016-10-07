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

public class NewAsyncTask extends AsyncTask<Void, Boolean, Boolean> {

    private Intent intent;
    private Activity activity;
    private String url;
    private ProgressBar progressBar;
    private ArrayList<Map<String, String>> newDataLists = new ArrayList<Map<String, String>>();

    public NewAsyncTask(Activity activity, Intent intent, String url) {
        this.intent   = intent;
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ListView listView = (ListView)activity.findViewById(R.id.newListView);
        SimpleAdapter adapter = new SimpleAdapter(activity,
                newDataLists,
                R.layout.new_listview_parts,
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
            ListView listView = (ListView)activity.findViewById(R.id.newListView);
            SimpleAdapter adapter = new SimpleAdapter(activity,
                    newDataLists,
                    R.layout.new_listview_parts,
                    new String[]{"",""},
                    new int[]{1, 2}) {

                @Override
                public View getView(final int position, View convertView, final ViewGroup parent) {
                    View view = convertView;
                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.new_listview_parts, parent, false);
                    } else {
                        view = convertView;
                    }
                    Map<String, String> tmp = newDataLists.get(position);
                    Log.d("debug NewAsyncTask", tmp.toString());

                    TextView hedingTextView = (TextView)view.findViewById(R.id.heding);
                    Log.d("debug NewAsyncTask", hedingTextView.toString());
                    hedingTextView.setText(tmp.get("heding"));
                    TextView channelTextView = (TextView)view.findViewById(R.id.channel);
                    channelTextView.setText(tmp.get("channel"));
                    ImageView favoriteImageButton = (ImageView)view.findViewById(R.id.favoriteRegister);
                    favoriteImageButton.setTag(position);

                    return view;
                }
            };
            listView.setAdapter(adapter);
            Log.d("debug NewAsyncTask", "更新完了しました");
        } else {
            Toast.makeText(activity,"データの取得に失敗しました", Toast.LENGTH_LONG).show();
        }
    }
}
