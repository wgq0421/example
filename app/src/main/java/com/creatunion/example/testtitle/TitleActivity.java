package com.creatunion.example.testtitle;

import android.os.Bundle;

import com.creatunion.example.R;
import com.creatunion.example.base.BaseActivity;

public class TitleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView(ContentViewAttribute attribute) {
        attribute.layoutResId = R.layout.activity_title;
        attribute.hasBar = true;
        attribute.hasBack = true;
        attribute.hasTitle = true;
        attribute.mTitle = "标题栏";
    }
}
