package com.example.otyon.gamesummary.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageGetTask extends AsyncTask<String,Void,Bitmap> {
    private View layout;
    private String tag;

    public ImageGetTask(View _layout) {
        layout = _layout;
        tag = layout.getTag().toString();
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap image;
        try {
            URL imageUrl = new URL(params[0]);
            InputStream imageIs;
            imageIs = imageUrl.openStream();
            image = BitmapFactory.decodeStream(imageIs);
            return image;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if (this.tag.equals(this.layout.getTag())) {
            ImageView imageView = new ImageView(layout.getContext());
            // 取得した画像をImageViewに設定します。
            imageView.setImageBitmap(result);
            ((LinearLayout)layout).removeAllViews();
            ((LinearLayout)layout).addView(imageView);
        }
   }
}
