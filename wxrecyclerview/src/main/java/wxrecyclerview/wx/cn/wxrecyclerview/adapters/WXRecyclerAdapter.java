package wxrecyclerview.wx.cn.wxrecyclerview.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import wxrecyclerview.wx.cn.wxrecyclerview.R;
import wxrecyclerview.wx.cn.wxrecyclerview.viewholder.WXViewHolder;
import wxrecyclerview.wx.cn.wxrecyclerview.bean.WXRecyclerItem;

public class WXRecyclerAdapter extends BaseWXRecyclerAdapter {

    private final String TAG = "wxAdapter";
    private Context context;
    private List<WXRecyclerItem> datas;

    public WXRecyclerAdapter(Context context,List<WXRecyclerItem> datas) {
        super(context,datas);
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return super.onCreateViewHolder(viewGroup,i);
    }

    @Override
    public View getNormalInflateLayout(Context context,ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.wx_base_recycler_item_layout,viewGroup,false);
    }

    @Override
    public View getHeadInflateLayout(Context context, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.wx_base_recycler_head_layout,viewGroup,false);
    }

    @Override
    public View getFootInflateLayout(Context context, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.wx_base_recycler_foot_layout,viewGroup,false);
    }

    @Override
    public View getTitleInflateLayout(Context context, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.wx_base_recycler_title_layout,viewGroup,false);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        super.onBindViewHolder(holder,i);
        if (holder instanceof WXViewHolder){
            WXViewHolder viewHolder = (WXViewHolder)holder;
            if (viewHolder.tv != null){
                viewHolder.tv.setText(datas.get(i).getName().isEmpty()?"":datas.get(i).getName());
            }
            if (viewHolder.jump != null){
                viewHolder.jump.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, i +"　点击", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


}
