package wxrecyclerview.wx.cn.wxrecyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import wxrecyclerview.wx.cn.wxrecyclerview.bean.WXRecyclerItem;
import wxrecyclerview.wx.cn.wxrecyclerview.utils.LogUtil;

public class WXRecyclerAdapter extends RecyclerView.Adapter<WXViewHolder> {

    private final String TAG = "wxAdapter";
    private Context context;
    private List<WXRecyclerItem> datas;

    public WXRecyclerAdapter(Context context,List<WXRecyclerItem> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public WXViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.wx_recycler_item_layout,viewGroup,false);
        WXViewHolder holder = new WXViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WXViewHolder wxViewHolder, @SuppressLint("RecyclerView") final int i) {
        if (wxViewHolder.tv != null){
            wxViewHolder.tv.setText(datas.get(i).getName().isEmpty()?"":datas.get(i).getName());
        }
        if (wxViewHolder.jump != null){
            wxViewHolder.jump.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.i(TAG,i+" clicked");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
