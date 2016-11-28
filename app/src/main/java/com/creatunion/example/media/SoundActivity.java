package com.creatunion.example.media;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.creatunion.example.R;
import com.creatunion.example.base.BaseActivity;
import com.creatunion.example.utils.ToastUtil;

import butterknife.BindView;

public class SoundActivity extends BaseActivity {


    @BindView(R.id.btn_record)
    Button btn_record;
    @BindView(R.id.btn_voice_bank)
    Button btn_voice_bank;
    private final static int REQUEST_RECORD = 10003;
    private final static int REQUEST_VIDEO_BANK = 10004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView(ContentViewAttribute attribute) {
        attribute.layoutResId = R.layout.activity_sound;
    }

    @Override
    protected void setListener() {
        super.setListener();
        btn_record.setOnClickListener(this);
        btn_voice_bank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_record://录音
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, REQUEST_RECORD);
                break;
            case R.id.btn_voice_bank://访问声音库
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1, REQUEST_VIDEO_BANK);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_RECORD:
                    try {
                        Uri uri = data.getData();
                        Cursor cursor = getContentResolver()
                                .query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                        String filePath = cursor.getString(index);
                        ToastUtil.centerToast(this, filePath);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case REQUEST_VIDEO_BANK:
                    Uri selectedAudio = data.getData();
                    String[] filePathColumn = {MediaStore.Audio.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedAudio,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    if (filePath != null && filePath.length() > 0)
                        ToastUtil.centerToast(this, filePath);
                    break;
            }
        }
    }

}
