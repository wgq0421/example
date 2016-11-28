package com.creatunion.example.recyclerView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.creatunion.example.R;
import com.creatunion.example.base.BaseActivity;
import com.creatunion.example.utils.ToastUtil;

import butterknife.BindView;

public class RecyclerActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置固定大小(如果可以确定每个item的高度是固定的，设置这个选项可以提高性能)
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);//创建线性布局
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL); //垂直方向
//        GridLayoutManager girdLayoutManager=new GridLayoutManager(this,4);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new AdvanceDecoration(this, OrientationHelper.VERTICAL));
        //创建适配器，并且设置
        adapter = new RecyclerAdapter(this);
        adapter.setOnRecyclerClickListener(new OnRecyclerClickListener() {
            @Override
            public void onClickListener(String text, int position) {
                ToastUtil.centerToast(RecyclerActivity.this, "点击了第" + (position + 1) + "," + text);
            }

            @Override
            public void onLongClickListener(String text, int position) {
                ToastUtil.centerToast(RecyclerActivity.this, "长按了第" + (position + 1) + "," + text);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initContentView(ContentViewAttribute attribute) {
        attribute.layoutResId = R.layout.activity_recycler;
    }
}
