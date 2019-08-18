/**
 * 自定义RecyclerView
 */

package wxrecyclerview.wx.cn.wxrecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;

import wxrecyclerview.wx.cn.wxrecyclerview.utils.LogUtil;

public class WXRecyclerView extends RecyclerView {

    private final String TAG = WXRecyclerView.class.getSimpleName();
    private final int INVALID_POSITION = -1;

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
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private int lastTouchPosition;
    private int downX,downY;
    private int deltaX,deltaY;
    private int touchSlop;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        int pos = findPositionWhenTouch(x,y);
        if (pos != lastTouchPosition){
            lastTouchPosition = pos;
        }
        int action = e.getAction();
        LogUtil.i(TAG,"touch pos is: "+pos + "   action = "+action);

        switch (action){
            case MotionEvent.ACTION_DOWN:
                LogUtil.i(TAG,"ACTION_DOWN");
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = x - downX;
                deltaY = y - downY;
                ViewParent viewParent = getParent();
                if (viewParent == null){
                    break;
                }
                LogUtil.i(TAG,"ACTION_MOVE move deltaX = "+deltaX + "  deltaY = "+deltaY);
                if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > touchSlop){
                    LogUtil.i(TAG,"move horizontal");
                    viewParent.requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.i(TAG,"ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.i(TAG,"ACTION_CANCEL");
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    private Rect rect;
    private int findPositionWhenTouch(int x,int y){
        int firstVisibilePos = ((LinearLayoutManager)getLayoutManager()).findFirstVisibleItemPosition();
        Rect touchRect = rect;
        if (touchRect == null){
            rect = new Rect();
            touchRect = rect;
        }

        int childCnt = getChildCount();
        for (int i=childCnt - 1; i>0; i--){
            View item = getChildAt(i);
            if (item.getVisibility() == View.VISIBLE){
                item.getHitRect(touchRect);
                if (touchRect.contains(x,y)){
                    return firstVisibilePos + i;
                }
            }
        }

        return INVALID_POSITION;
    }

    public void layout(){

    }
}
