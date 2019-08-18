package wxrecyclerview.wx.cn.wxrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WXViewHolder extends RecyclerView.ViewHolder {

    TextView tv;
    Button jump;

    public WXViewHolder(@NonNull View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.name);
        jump = itemView.findViewById(R.id.jump);
    }
}
