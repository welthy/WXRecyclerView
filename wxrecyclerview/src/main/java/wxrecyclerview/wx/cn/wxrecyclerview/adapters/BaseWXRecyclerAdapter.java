package wxrecyclerview.wx.cn.wxrecyclerview.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import wxrecyclerview.wx.cn.wxrecyclerview.bean.WXRecyclerItem;
import wxrecyclerview.wx.cn.wxrecyclerview.viewholder.WXViewHolder;

public abstract class BaseWXRecyclerAdapter extends RecyclerView.Adapter<WXViewHolder>{

    private Context mBaseContext;
    private List<WXRecyclerItem> mBaseDatas;

    //item是否允许删除开关
    private boolean mItemDeleteAllowed = false;
    //item点击事件
    private OnWXItemClickListener itemClickListener;

    public interface OnWXItemClickListener extends AdapterView.OnItemClickListener {
        void onWXItemClick(View view);
    }

    public void setOnItemClickListener(OnWXItemClickListener listener){
        this.itemClickListener = listener;
    }

    public BaseWXRecyclerAdapter(Context context, List<WXRecyclerItem> datas){
        this.mBaseContext = context;
        this.mBaseDatas = datas;
    }

    @NonNull
    @Override
    public WXViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WXViewHolder(getInflateLayout(mBaseContext,viewGroup));
    }

    public abstract View getInflateLayout(Context context,ViewGroup viewGroup);

    @Override
    public void onBindViewHolder(@NonNull WXViewHolder wxViewHolder, int i) {
        if (wxViewHolder.root != null){
            wxViewHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onWXItemClick(v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBaseDatas.size();
    }
}
