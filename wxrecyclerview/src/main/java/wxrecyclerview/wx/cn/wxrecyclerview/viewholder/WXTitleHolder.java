package wxrecyclerview.wx.cn.wxrecyclerview.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import wxrecyclerview.wx.cn.wxrecyclerview.R;

public class WXTitleHolder extends RecyclerView.ViewHolder {

    public TextView titleName;

    public WXTitleHolder(@NonNull View itemView) {
        super(itemView);
        titleName = itemView.findViewById(R.id.title_name);
    }
}
