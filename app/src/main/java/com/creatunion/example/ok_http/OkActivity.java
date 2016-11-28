package com.creatunion.example.ok_http;

import android.os.Bundle;
import android.widget.TextView;

import com.creatunion.example.R;
import com.creatunion.example.base.BaseActivity;

import butterknife.BindView;

public class OkActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initContentView(ContentViewAttribute attribute) {
        attribute.layoutResId = R.layout.activity_ok;
    }
}
