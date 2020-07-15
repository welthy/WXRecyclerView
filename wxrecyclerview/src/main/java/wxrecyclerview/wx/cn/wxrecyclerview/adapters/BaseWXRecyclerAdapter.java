package wxrecyclerview.wx.cn.wxrecyclerview.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import wxrecyclerview.wx.cn.wxrecyclerview.bean.WXRecyclerItem;
import wxrecyclerview.wx.cn.wxrecyclerview.utils.Utils;
import wxrecyclerview.wx.cn.wxrecyclerview.viewholder.WXFootHolder;
import wxrecyclerview.wx.cn.wxrecyclerview.viewholder.WXHeadHolder;
import wxrecyclerview.wx.cn.wxrecyclerview.viewholder.WXTitleHolder;
import wxrecyclerview.wx.cn.wxrecyclerview.viewholder.WXViewHolder;
import wxrecyclerview.wx.cn.wxrecyclerview.utils.WXConstants;

public abstract class BaseWXRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == WXConstants.TYPE_HEAD){
            return new WXHeadHolder(getHeadInflateLayout(mBaseContext,viewGroup));
        }else if (i == WXConstants.TYPE_FOOT){
            return new WXFootHolder(getFootInflateLayout(mBaseContext,viewGroup));
        }else if (i == WXConstants.TYPE_TITLE){
            return new WXTitleHolder(getTitleInflateLayout(mBaseContext,viewGroup));
        }else {
            return new WXViewHolder(getNormalInflateLayout(mBaseContext,viewGroup));
        }
    }

    public abstract View getNormalInflateLayout(Context context,ViewGroup viewGroup);
    public abstract View getHeadInflateLayout(Context context,ViewGroup viewGroup);
    public abstract View getFootInflateLayout(Context context,ViewGroup viewGroup);
    public abstract View getTitleInflateLayout(Context context,ViewGroup viewGroup);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof WXViewHolder){
            WXViewHolder viewHolder = (WXViewHolder)holder;
            viewHolder.middleContent.getLayoutParams().width = Utils.getScreenWidth(mBaseContext);
            if (viewHolder.root != null){
                viewHolder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onWXItemClick(v);
                    }
                });
            }
        }else if (holder instanceof WXHeadHolder){
            WXHeadHolder headHolder = (WXHeadHolder)holder;

        }else if (holder instanceof WXFootHolder){
            WXFootHolder footHolder = (WXFootHolder)holder;

        }else if (holder instanceof WXTitleHolder){
            WXTitleHolder titleHolder = (WXTitleHolder)holder;
            titleHolder.titleName.setText(mBaseDatas.get(i).getName());
        }

    }

    @Override
    public int getItemCount() {
        return mBaseDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        WXRecyclerItem item = mBaseDatas.get(position);
        if (item != null){
            int type = item.getType();
            if (type == WXConstants.TYPE_HEAD){
                return WXConstants.TYPE_HEAD;
            }else if (type == WXConstants.TYPE_FOOT){
                return WXConstants.TYPE_FOOT;
            }else if (type == WXConstants.TYPE_TITLE){
                return WXConstants.TYPE_TITLE;
            }else {
                return WXConstants.TYPE_NORMAL;
            }
        }
        return WXConstants.TYPE_INVALID;
    }
}
