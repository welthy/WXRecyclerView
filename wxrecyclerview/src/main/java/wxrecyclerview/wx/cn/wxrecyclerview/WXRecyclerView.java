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

    //private int lastTouchPosition;
    private int downX,downY;
    private int deltaX,deltaY;
    private int lastX;
    private int touchSlop;
    private View mMoveView,mLastView;
    private boolean mHorizontalMoving;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        //查找当前触摸的item位置
        /*int pos = findPositionWhenTouch(x,y);
        if (pos != lastTouchPosition){
            lastTouchPosition = pos;
        }*/
        int action = e.getAction();
        //LogUtil.i(TAG,"touch pos is: "+pos + "   action = "+action);

        switch (action){
            case MotionEvent.ACTION_DOWN:
                LogUtil.i(TAG,"ACTION_DOWN");
                downX = x;
                downY = y;
                //查找当前触摸的Item
                mMoveView = findChildViewUnder(x,y);
                if (mLastView != null && mLastView != mMoveView){
                    //关闭其他非触摸的Item
                    //closeItem(mLastView);
                }
                //按下后，避免当前Item瞬间移动到手指按下的位置
                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = x - downX;
                deltaY = y - downY;
                ViewParent viewParent = getParent();
                if (viewParent == null){
                    break;
                }
                if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > touchSlop){
                    //当前满足移动条件，则WXRecyclerView拦截触摸事件
                    LogUtil.i(TAG,"move horizontal");
                    mHorizontalMoving = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.i(TAG,"ACTION_UP");
            case MotionEvent.ACTION_CANCEL:
                LogUtil.i(TAG,"ACTION_CANCEL");
                mLastView = mMoveView;
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (mHorizontalMoving && Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > touchSlop){
                    //计算水平移动偏移量
                    int dx = lastX - x;
                    mMoveView.scrollBy(dx,0);
                }
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mHorizontalMoving = false;
               // closeItem();
                break;
        }
        return super.onTouchEvent(e);
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

    //复原Item置初始位置
    private void closeItem(View view){
        if (mLastView != null && mLastView.getX() != 0){
            mLastView.scrollTo(view.getScrollX(),0);
        }
    }

}
