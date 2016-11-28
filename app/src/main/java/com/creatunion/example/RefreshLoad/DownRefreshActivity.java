package com.creatunion.example.RefreshLoad;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.creatunion.example.R;
import com.creatunion.example.base.BaseActivity;
import com.creatunion.example.recyclerView.AdvanceDecoration;
import com.creatunion.example.recyclerView.OnRecyclerClickListener;
import com.creatunion.example.recyclerView.RecyclerAdapter;
import com.creatunion.example.utils.ToastUtil;

import butterknife.BindView;

public class DownRefreshActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.layout_load_more)
    LinearLayout layoutLoadMore;
    @BindView(R.id.layout_load_empty)
    LinearLayout layoutLoadEmpty;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapter adapter;
    private int lastVisibleItem;//recycler中最后一个view的位置

    @Override
    protected void initContentView(ContentViewAttribute attribute) {
        attribute.layoutResId = R.layout.activity_refresh;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置固定大小(如果可以确定每个item的高度是固定的，设置这个选项可以提高性能)
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
                ToastUtil.centerToast(DownRefreshActivity.this, "点击了第" + (position + 1) + "," + text);
            }

            @Override
            public void onLongClickListener(String text, int position) {
                ToastUtil.centerToast(DownRefreshActivity.this, "长按了第" + (position + 1) + "," + text);
            }
        });
        recyclerView.setAdapter(adapter);

        //下拉刷新
        setSwiperefreshlayout();
        //上拉加载
        upLoadMore();
    }


    //上拉加载
    public void upLoadMore() {

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    layoutLoadMore.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layoutLoadMore.setVisibility(View.GONE);
                            layoutLoadEmpty.setVisibility(View.VISIBLE);
                        }
                    }, 2000);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            List<String> newDatas = new ArrayList<String>();
//                            for (int i = 0; i < 5; i++) {
//                                int index = i + 1;
//                                newDatas.add("more item" + index);
//                            }
//                            adapter.addAll(newDatas);
//                            layoutLoadMore.setVisibility(View.GONE);
//                        }
//                    }, 2000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    //处理swipserefershlayout（下拉刷新）
    public void setSwiperefreshlayout() {
        //刷新进度条的背景色
        swiperefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //刷新进度条的颜色
        swiperefreshlayout.setColorSchemeResources(
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        //设置进度条的偏移量（防止显示不出来）
        swiperefreshlayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //添加下拉刷新监听器
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟网络请求（耗时操作）
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.replaceItem("replace", 1);
                        swiperefreshlayout.setRefreshing(false);//隐藏刷新进度条
                        Toast.makeText(DownRefreshActivity.this, "更新了数据...", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
    }
}
