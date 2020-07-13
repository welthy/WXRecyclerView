package wxrecyclerview.wx.cn.wxrecyclerview.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wxrecyclerview.wx.cn.wxrecyclerview.R;
import wxrecyclerview.wx.cn.wxrecyclerview.widgets.WXItemView;

public class WXViewHolder extends RecyclerView.ViewHolder {

    public WXItemView root;
    public TextView tv;
    public Button jump;

    public LinearLayout linearLeft;
    public RelativeLayout middleContent;
    public LinearLayout linearRight;

    public WXViewHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.root);
        tv = itemView.findViewById(R.id.name);
        jump = itemView.findViewById(R.id.jump);

        linearLeft = itemView.findViewById(R.id.left_menu);
        middleContent = itemView.findViewById(R.id.middle_content);
        linearRight = itemView.findViewById(R.id.right_menu);
    }
}
