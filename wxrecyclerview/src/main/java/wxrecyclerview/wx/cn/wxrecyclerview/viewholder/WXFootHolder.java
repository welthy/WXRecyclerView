package wxrecyclerview.wx.cn.wxrecyclerview.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import wxrecyclerview.wx.cn.wxrecyclerview.R;

public class WXFootHolder extends RecyclerView.ViewHolder {

    private TextView footTitle;

    public WXFootHolder(@NonNull View itemView) {
        super(itemView);
        footTitle = itemView.findViewById(R.id.foot_title);
    }
}
