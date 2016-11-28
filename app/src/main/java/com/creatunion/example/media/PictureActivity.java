package com.creatunion.example.media;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.creatunion.example.R;
import com.creatunion.example.base.BaseActivity;
import com.creatunion.example.utils.ImageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureActivity extends BaseActivity {

    private Button capture;
    private Button album;
    private ImageView iv_picture;
    private String imageFilePath;
    private final static int REQUEST_CAPTURE = 10001;
    private final static int REQUEST_ALBUM = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView(ContentViewAttribute attribute) {
        attribute.layoutResId = R.layout.activity_picture;
    }

    @Override
    protected void initViews() {
        super.initViews();
        capture = getView(R.id.capture);
        album = getView(R.id.album);
        iv_picture = getView(R.id.iv_picture);
    }

    @Override
    protected void setListener() {
        super.setListener();
        capture.setOnClickListener(this);
        album.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.capture://调用相机拍照
//                String status = Environment.getExternalStorageState();
//                if (status.equals(Environment.MEDIA_MOUNTED)) {
//                    imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/filename.jpg";
//                    File dir = new File(imageFilePath);
//                    if (!dir.exists()) dir.mkdirs();
//                    Uri imageFileUri = Uri.fromFile(dir);//获取文件的Uri
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
//                    it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                startActivityForResult(it, REQUEST_CAPTURE);
//                } else {
//                    Toast.makeText(PictureActivity.this, "没有存储卡", Toast.LENGTH_LONG).show();
//                }
                break;
            case R.id.album://调用系统相册
                Intent intent1 = new Intent(Intent.ACTION_PICK);//ACTION_GET_CONTENT,ACTION_PICK
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, REQUEST_ALBUM);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAPTURE:

                    //返回未经裁剪的图片(原图)
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
//                        iv_picture.setImageBitmap(bitmap);
//                    } catch (Exception e) {
//                    }
                    //两种方式 获取拍好的图片（得到的是经过裁剪的图片）
                    if (data != null) {
                        if (data.getData() != null || data.getExtras() != null) { //防止没有返回结果
                            Bitmap photo = null;
                            Uri uri = null;
                            uri = data.getData();
                            if (uri != null) {
                                photo = BitmapFactory.decodeFile(uri.getPath()); //拿到图片
                                iv_picture.setImageBitmap(photo);
                            }
                            if (photo == null) {
                                Bundle bundle = data.getExtras();
                                if (bundle != null) {
                                    photo = (Bitmap) bundle.get("data");
                                    uri = Uri.parse(MediaStore.Images.Media.insertImage(
                                            getContentResolver(), photo, null, null));
                                    iv_picture.setImageBitmap(photo);
                                } else {
                                    Toast.makeText(getApplicationContext(), "找不到图片", Toast.LENGTH_SHORT).show();
                                }
                            }
                            Cursor cursor = getContentResolver()
                                    .query(uri, null, null, null, null);
                            cursor.moveToFirst();
                            int index = cursor.getColumnIndex("_data");
                            String filePath = cursor.getString(index);
                            Toast.makeText(PictureActivity.this, filePath, Toast.LENGTH_LONG).show();
                        }
                    }
                    break;

                case REQUEST_ALBUM:
                    Bitmap bitmap = null;
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        bitmap = ImageUtil.handleImageOnKitKat(this, data);        //ImgUtil是自己实现的一个工具类
                    } else {
                        //4.4以下系统使用这个方法处理图片
                        bitmap = ImageUtil.handleImageBeforeKitKat(this, data);
                    }
                    iv_picture.setImageBitmap(bitmap);
                    break;
            }
        }
    }


    //把bitamp存到SD卡
    public void saveMyBitmap(Bitmap mBitmap) {
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/filename2.jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);//第二个参数是100时，表示不压缩
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
