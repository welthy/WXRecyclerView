package wxrecyclerview.wx.cn.wxrecyclerview2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class WXRecyclerView extends RecyclerView {

    public WXRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public WXRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WXRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){

    }

    public void layout(){

    }
}
