package wxrecyclerview.wx.cn.wxrecyclerview.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import wxrecyclerview.wx.cn.wxrecyclerview.R;

public class WXItemView extends LinearLayout {

    private boolean mHasLeftMenu = false;
    private boolean mHasRightMenu = false;
    private boolean mInit = false;
    private LinearLayout leftMenu,rightMenu;

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

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!mInit){
            leftMenu = findViewById(R.id.left_menu);
            rightMenu = findViewById(R.id.right_menu);
            int dx = leftMenu.getWidth();
            this.scrollTo(dx,0);
            mInit = true;
        }
    }

    public int getLeftMenuWidth(){
        return findViewById(R.id.left_menu).getWidth();
    }

    public int getRightMenuWidth(){
        return findViewById(R.id.right_menu).getWidth();
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
