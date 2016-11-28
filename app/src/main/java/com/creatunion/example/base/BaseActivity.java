package com.creatunion.example.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.creatunion.example.R;
import com.creatunion.example.event.BaseEventManager;
import com.creatunion.example.event.Event;
import com.creatunion.example.utils.ToastUtil;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseEventManager.OnEventListener {

    private ContentViewAttribute attribute = new ContentViewAttribute();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        initContentView(attribute);
        if (attribute.layoutResId != 0)
            setCustomContentView(attribute.layoutResId);
        ButterKnife.bind(this);
        initViews();
        setListener();

    }

    private RelativeLayout titleBar;
    private ImageView iv_back;
    private TextView tv_title;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.tv_title:
                    ToastUtil.centerToast(getApplicationContext(), "点击了标题栏");
                    break;
            }
        }
    };

    protected abstract void initContentView(ContentViewAttribute attribute);

    protected class ContentViewAttribute {

        public boolean hasBar = false;
        public int layoutResId;
        public boolean hasBack = false;
        public boolean hasTitle = false;
        public String mTitle;
    }

    //自定义layout
    protected void setCustomContentView(int layoutResId) {
        super.setContentView(layoutResId);

        titleBar = (RelativeLayout) findViewById(R.id.re_titleBar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);

        if (attribute.hasBar) {
            titleBar.setVisibility(View.VISIBLE);
            if (attribute.hasBack) {
                iv_back.setVisibility(View.VISIBLE);
                iv_back.setOnClickListener(clickListener);
            }
            if (attribute.hasTitle) {
                tv_title.setVisibility(View.VISIBLE);
                if (attribute.mTitle != null)
                    tv_title.setText(attribute.mTitle);
                tv_title.setOnClickListener(clickListener);
            }
        }
    }

    //初始化控件
    protected void initViews() {

    }

    //给控件设置监听
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    //找到控件
    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    protected void launchActivity(Class<?> c) {
        Intent i = new Intent(this, c);
        startActivity(i);
    }

    @Override
    public void onEventRunEnd(Event event) {

    }
}
