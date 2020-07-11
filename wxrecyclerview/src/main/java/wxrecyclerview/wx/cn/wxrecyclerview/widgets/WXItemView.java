package wxrecyclerview.wx.cn.wxrecyclerview.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import wxrecyclerview.wx.cn.wxrecyclerview.utils.LogUtil;

public class WXItemView extends RelativeLayout {

    private static final String TAG = WXItemView.class.getSimpleName();
    private Context mContext;
    public WXItemView(Context context) {
        this(context,null);
    }

    public WXItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WXItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d(TAG, "onTouchEvent()");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
