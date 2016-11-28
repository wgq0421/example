package com.creatunion.example;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.creatunion.example.base.BaseActivity;
import com.creatunion.example.media.PictureActivity;
import com.creatunion.example.media.SoundActivity;
import com.creatunion.example.media.VideoActivity;
import com.creatunion.example.side.SideActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.recycler)
    Button recycler;
    private Button picture;//照片
    private Button sound;//音频
    private Button video;//视频

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView(ContentViewAttribute attribute) {
        attribute.layoutResId = R.layout.activity_main;
    }


    @Override
    protected void initViews() {
        super.initViews();
        picture = getView(R.id.picture);
        sound = getView(R.id.sound);
        video = getView(R.id.video);

    }

    @Override
    protected void setListener() {
        super.setListener();
        picture.setOnClickListener(this);
        sound.setOnClickListener(this);
        video.setOnClickListener(this);
        recycler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.picture:
                launchActivity(PictureActivity.class);
                break;
            case R.id.sound:
                launchActivity(SoundActivity.class);
                break;
            case R.id.video:
                launchActivity(VideoActivity.class);
                break;
            case R.id.recycler:
                launchActivity(SideActivity.class);
                break;
        }
    }
}
