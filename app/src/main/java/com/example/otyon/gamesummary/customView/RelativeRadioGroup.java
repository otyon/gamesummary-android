/*
 * Copyright (C) 2011 The yanzm Custom View Project
 *      Yuki Anzai, uPhyca Inc.
 *      http://www.uphyca.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.otyon.gamesummary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.util.Random;

public class RelativeRadioGroup extends RelativeLayout {

    private int mCheckedId = -1;
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private boolean mProtectFromCheckedChange = false;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;

    private static final String TAG = "RelativeRadioGroup";
    private static final boolean DEBUG = false;

    public RelativeRadioGroup(Context context) {
        super(context);
        init();
    }

    public RelativeRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mChildOnCheckedChangeListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(mCheckedId != -1) {
            mProtectFromCheckedChange = true;
            setCheckedStateForView(mCheckedId, true);
            mProtectFromCheckedChange = false;
            setCheckedId(mCheckedId);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(DEBUG)
            Log.d(TAG, "child : " + child.getClass().getName() + ", index : " + index);

        if(child instanceof RadioButton) {
            // idが未設定の場合は乱数でどうにかする。
            if (child.getId() == -1) {
                child.setId(new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE));
            }

            final RadioButton button = (RadioButton) child;

            if (button.isChecked()) {
                mProtectFromCheckedChange = true;
                if (mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false);
                }
                mProtectFromCheckedChange = false;
                setCheckedId(button.getId());
            }
        }
        super.addView(child, index, params);
    }

    public void check(int id) {
        if(DEBUG)
            Log.d(TAG, "check : id = " + id);

        if(id != -1 && (id == mCheckedId)) {
            return;
        }

        if(mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }

        if(id != -1) {
            setCheckedStateForView(id, true);
        }
        setCheckedId(id);
    }

    private void setCheckedId(int id) {
        if(DEBUG)
            Log.d(TAG, "setCheckedId : id = " + id);

        mCheckedId = id;
        if(mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
        }
    }

    private void setCheckedStateForView(int viewId, boolean checked){
        if(DEBUG)
            Log.d(TAG, "setCheckedStateForView : id = " + viewId + ", checked = " + checked);

        View checkedView = findViewById(viewId);
        if(checkedView != null && checkedView instanceof RadioButton) {
            ((RadioButton) checkedView).setChecked(checked);
        }
    }

    public int getCheckedRadioButtonId() {
        return mCheckedId;
    }

    public void clearCheck() {
        check(-1);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    public interface OnCheckedChangeListener {
        public void onCheckedChanged(RelativeRadioGroup group, int checkedId);
    }

    private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(DEBUG) {
                Log.d(TAG, "mCheckedId = " + mCheckedId);
                Log.d(TAG, "mProtectFromCheckedChange = " + mProtectFromCheckedChange);
            }

            if(mProtectFromCheckedChange) {
                return;
            }

            mProtectFromCheckedChange = true;
            if(mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }
            mProtectFromCheckedChange = false;

            int id = buttonView.getId();
            Log.d(TAG, "id = " + id);
            setCheckedId(id);
        }
    }

    private class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        public void onChildViewAdded(View parent, View child) {
            if(DEBUG)
                Log.d(TAG, "onChildViewAdded : parent = " + parent + ", child = " + child);

            if(parent == RelativeRadioGroup.this && child instanceof RadioButton) {
                int id = child.getId();
                if(id == View.NO_ID) {
                    id = child.hashCode();
                    child.setId(id);
                }
                ((RadioButton)child).setOnCheckedChangeListener(mChildOnCheckedChangeListener);
            }
            if(mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        public void onChildViewRemoved(View parent, View child) {
            if(DEBUG)
                Log.d(TAG, "onChildViewRemoved : parent = " + parent + ", child = " + child);

            if(parent == RelativeRadioGroup.this && child instanceof RadioButton) {
                int id = child.getId();
                if(id == View.NO_ID) {
                    id = child.hashCode();
                    child.setId(id);
                }
                ((RadioButton)child).setOnCheckedChangeListener(null);
            }
            if(mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {

        super.onWindowFocusChanged(hasWindowFocus);

        // 子供の数を取得
        int l = this.getChildCount();
        if(DEBUG)
            Log.d("debug child", String.valueOf(l));
        // 無いなら何もしない
        if (l == 0) {
            return;
        }

        // 1行最大長
        int max = this.getWidth();
        if(DEBUG)
            Log.d("debug max", String.valueOf(max));

        // 一番最初は基点となるので何もしない
        View pline = this.getChildAt(0);
        // 現在の１行の長さ
        int currentTotal = pline.getWidth();

        if(DEBUG)
            Log.d("debug pline width", String.valueOf(pline.getWidth()));
        if(DEBUG)
            Log.d("debug currentTotal", String.valueOf(currentTotal));

        for (int i = 1; i < l; i++) {
            int w = this.getChildAt(i).getWidth();
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) this.getChildAt(i).getLayoutParams();

            // 横幅を超えないなら前のボタンの右に出す
            if (max > currentTotal + w) {
                // 現在長に処理対象コントロール長を加算
                currentTotal += w;
                layoutParams.addRule(RelativeLayout.ALIGN_TOP, this.getChildAt(i - 1).getId());
                layoutParams.addRule(RelativeLayout.RIGHT_OF, this.getChildAt(i - 1).getId());
            } else {
                // 横最大長を超過した場合は折り返す
                layoutParams.addRule(RelativeLayout.BELOW, pline.getId());

                // 基点を変更
                pline = this.getChildAt(i);

                // 長さをリセット
                currentTotal = pline.getWidth();
            }
        }
    }
}