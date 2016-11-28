package com.creatunion.example.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.creatunion.example.R;

/**
 * Created by wuguoqiang on 16/9/18.
 */
public class ToastUtil {


    public static void centerToast(Context context, String content) {
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.layout_toast, null);
        TextView text = (TextView) layout.findViewById(R.id.tv_content);
        text.setText(content);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
