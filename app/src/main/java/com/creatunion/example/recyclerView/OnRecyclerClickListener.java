package com.creatunion.example.recyclerView;

/**
 * Created by wuguoqiang on 16/9/21.
 */
public interface OnRecyclerClickListener {

    void onClickListener(String text, int position);

    void onLongClickListener(String text, int position);
}
