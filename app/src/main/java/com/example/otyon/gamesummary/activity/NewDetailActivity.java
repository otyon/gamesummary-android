package com.example.otyon.gamesummary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

import com.example.otyon.gamesummary.R;

public class NewDetailActivity extends AbstructActivity {

    private int beforePosition = 0;
    private int nextPosition = 1;
    private ArrayList<Map<String, String>> newsDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        newsDataList= (ArrayList<Map<String, String>>)intent.getSerializableExtra("newDataLists");

        TextView tileText = (TextView)findViewById(R.id.header_title);
        tileText.setText("新着詳細");

        Button beforeButton = (Button)findViewById(R.id.before_button);
        Button nextButton = (Button)findViewById(R.id.next_button);

        int selectPosition  = intent.getIntExtra("selectPosition", 0);
        beforePosition    = selectPosition - 1;
        nextPosition      = selectPosition + 1;
        if (beforePosition <= 0) {
            beforeButton.setEnabled(false);
        }
        if (nextPosition >= newsDataList.size()) {
            nextButton.setEnabled(false);
        }

        String remarkTitle  = intent.getStringExtra("remarkTitle");
        TextView newsTitle = (TextView)findViewById(R.id.news_title);
        newsTitle.setText(remarkTitle);

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
}
