package com.example.otyon.gamesummary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.otyon.gamesummary.R;
import com.example.otyon.gamesummary.task.InformationAsyncTask;

import java.util.ArrayList;
import java.util.Map;

public class InformationActivity extends AbstructActivity {

    protected ArrayList<Map<String, String>> informationDataLists;
    protected String baseUrl = "http://app.ja.unisonleague.com/app_jp/information.php?action_information_past=true&lang=jp";
    protected Intent intent;
    protected Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("公式おしらせ一覧");
        this.intent = getIntent();

        new InformationAsyncTask(
                activity,
                this.intent,
                baseUrl).execute();

        // 後ほどファイルから習得する方法に・・・
//        if (informationDataLists == null) {
//            informationDataLists = new ArrayList<Map<String, String>>();
//            try {
//                HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
//                htmlParseUtil.connectUrll(this.baseUrl);
//                informationDataLists = htmlParseUtil.getInformationParseData();
//                intent.putExtra("informationDataLists", informationDataLists);
//            } catch (Exception e) {
//                Log.d("error", e.toString());
//            }
//        }

//        ListView listView = (ListView) findViewById(R.id.informationListView);
//        SimpleAdapter adapter = new SimpleAdapter(this,
//                informationDataLists,
//                R.layout.information_listview_parts,
//                new String[]{"information_category","information_time"},
//                new int[]{R.id.information_category, R.id.information_time}){
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View row = convertView;
//                Map<String, String> informationData = informationDataLists.get(position);
//
//                TextView informationCategory = (TextView)row.findViewById(R.id.information_category);
//                informationCategory.setText(informationData.get("category"));
//
//                TextView informationTime = (TextView)row.findViewById(R.id.information_time);
//                informationCategory.setText(informationData.get("time"));
//
//                LinearLayout layout = (LinearLayout)row.findViewById(R.id.information_contents_layout);
//                switch(informationData.get("category")) {
//                    case "重要":
//                        informationCategory.setBackgroundColor(getResources().getColor(R.color.red));
//                        TextView informationContents1 = new TextView(getApplicationContext());
//                        informationContents1.setText(informationData.get("title_word"));
//                        informationContents1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                                                                           LinearLayout.LayoutParams.WRAP_CONTENT));
//                        ViewGroup.MarginLayoutParams mp1 = (ViewGroup.MarginLayoutParams)informationContents1.getLayoutParams();
//                        mp1.setMargins(0,10,10,0);
//                        informationContents1.setGravity(Gravity.CENTER);
//                        informationContents1.setTextSize(15);
//                        informationContents1.setTypeface(null, Typeface.BOLD);
//                        layout.addView(informationContents1);
//                        break;
//                    case "イベント":
//                        informationCategory.setBackgroundColor(getResources().getColor(R.color.orange));
//                        ProgressBar progressBar = new ProgressBar(getApplicationContext(),null,android.R.attr.progressBarStyle);
//                        progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                                                                LinearLayout.LayoutParams.WRAP_CONTENT));
//                        ViewGroup.MarginLayoutParams mp2 = (ViewGroup.MarginLayoutParams)progressBar.getLayoutParams();
//                        mp2.setMargins(0,10,10,0);
//                        layout.addView(progressBar);
//                        new ImageGetTask(layout).execute(informationData.get("image_url"));
//                        break;
//                    case "不具合":
//                        informationCategory.setBackgroundColor(getResources().getColor(R.color.gray));
//                        TextView informationContents2 = new TextView(getApplicationContext());
//                        informationContents2.setText(informationData.get("title_word"));
//                        informationContents2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                                                                            LinearLayout.LayoutParams.WRAP_CONTENT));
//                        ViewGroup.MarginLayoutParams mp3 = (ViewGroup.MarginLayoutParams)informationContents2.getLayoutParams();
//                        mp3.setMargins(0,10,10,0);
//                        informationContents2.setGravity(Gravity.CENTER);
//                        informationContents2.setTextSize(15);
//                        informationContents2.setTypeface(null, Typeface.BOLD);
//                        layout.addView(informationContents2);
//                        break;
//                }
//
//                return row;
//            }
//        };
//        listView.setAdapter(adapter);

        ListView listView = (ListView) findViewById(R.id.informationListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            }
        });
    }

    public void onInformationDetailButonClick(View v){
    }
}
