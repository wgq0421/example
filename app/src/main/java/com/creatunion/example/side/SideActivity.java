package com.creatunion.example.side;

import android.os.Bundle;

import com.creatunion.example.R;
import com.creatunion.example.base.BaseActivity;

public class SideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView(ContentViewAttribute attribute) {
        attribute.layoutResId = R.layout.activity_side;
    }

}
