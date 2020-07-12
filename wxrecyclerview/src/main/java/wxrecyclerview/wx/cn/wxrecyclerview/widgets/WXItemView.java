package wxrecyclerview.wx.cn.wxrecyclerview.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class WXItemView extends FrameLayout {

    private boolean mHasLeftMenu = false;
    private boolean mHasRightMenu = false;

    public WXItemView(Context context) {
        this(context,null);
    }

    public WXItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WXItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

    }

    private void addLeftMenu(View view){
        LayoutParams params = new LayoutParams(view.getWidth(),view.getHeight());

        addView(view,params);
        mHasLeftMenu = true;
    }

    private void addRightMenu(View view){
        mHasRightMenu = true;
    }

    public boolean ismHasLeftMenu(){
        return mHasLeftMenu;
    }

    public boolean ismHasRightMenu(){
        return mHasRightMenu;
    }

}
