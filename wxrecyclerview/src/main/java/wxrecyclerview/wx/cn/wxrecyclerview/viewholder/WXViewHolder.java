package wxrecyclerview.wx.cn.wxrecyclerview.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import wxrecyclerview.wx.cn.wxrecyclerview.R;

public class WXViewHolder extends RecyclerView.ViewHolder {

    public ViewGroup root;
    public TextView tv;
    public Button jump;

    public WXViewHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.root);
        tv = itemView.findViewById(R.id.name);
        jump = itemView.findViewById(R.id.jump);
    }
}
