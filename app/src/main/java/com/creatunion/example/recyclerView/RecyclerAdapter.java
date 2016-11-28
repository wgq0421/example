package com.creatunion.example.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creatunion.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuguoqiang on 16/9/20.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<String> datas;
    private OnRecyclerClickListener onRecyclerClickListener;

    public void setOnRecyclerClickListener(OnRecyclerClickListener onRecyclerClickListener) {
        this.onRecyclerClickListener = onRecyclerClickListener;
    }

    public RecyclerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.datas = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            int index = i + 1;
            datas.add("item" + index);
        }
    }

    /**
     * item显示类型
     */
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    /**
     * 数据的绑定显示
     */
    @Override
    public void onBindViewHolder(final RecyclerAdapter.MyViewHolder holder, final int position) {
        holder.text.setText(datas.get(position));
        if (onRecyclerClickListener != null) {
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerClickListener.onClickListener(holder.text.getText().toString(), position);
                }
            });
            holder.text.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onRecyclerClickListener.onLongClickListener(holder.text.getText().toString(), position);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }


    //添加一条数据
    public void addItem(String data, int position) {
        datas.add(position, data);
        notifyItemInserted(position);
    }

    //删除一条数据
    public void removeItem(String data) {
        int position = datas.indexOf(data);
        datas.remove(position);
        notifyItemRemoved(position);
    }

    //修改一条数据
    public void replaceItem(String data, int position) {
        datas.remove(position);
        datas.add(position, data);
        notifyDataSetChanged();
    }

    public void addAll(List<String> list) {
        datas.addAll(list);
        notifyDataSetChanged();
    }

}
