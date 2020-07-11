package wxrecyclerview.wx.cn.wxrecyclerview.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import wxrecyclerview.wx.cn.wxrecyclerview.R;
import wxrecyclerview.wx.cn.wxrecyclerview.viewholder.WXViewHolder;
import wxrecyclerview.wx.cn.wxrecyclerview.bean.WXRecyclerItem;
import wxrecyclerview.wx.cn.wxrecyclerview.utils.LogUtil;

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
    public WXViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return super.onCreateViewHolder(viewGroup,i);
    }

    @Override
    public View getInflateLayout(Context context,ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.wx_recycler_item_layout,viewGroup,false);
    }

    @Override
    public void onBindViewHolder(@NonNull WXViewHolder wxViewHolder, @SuppressLint("RecyclerView") final int i) {
        super.onBindViewHolder(wxViewHolder,i);
        if (wxViewHolder.tv != null){
            wxViewHolder.tv.setText(datas.get(i).getName().isEmpty()?"":datas.get(i).getName());
        }
        if (wxViewHolder.jump != null){
            wxViewHolder.jump.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
