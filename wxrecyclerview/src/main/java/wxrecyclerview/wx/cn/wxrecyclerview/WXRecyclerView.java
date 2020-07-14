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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.Scroller;

import wxrecyclerview.wx.cn.wxrecyclerview.utils.LogUtil;
import wxrecyclerview.wx.cn.wxrecyclerview.widgets.WXItemView;

public class WXRecyclerView extends RecyclerView {

    private final String TAG = WXRecyclerView.class.getSimpleName();
    private final int INVALID_POSITION = -1;
    //左滑开关
    private boolean mLeftScrollAllowed = true;
    //右滑开关
    private boolean mRightScrollAllowed = true;

    private Scroller closeScroller;

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
        closeScroller = new Scroller(context);
    }

    //private int lastTouchPosition;
    private int downX,downY;
    private int deltaX,deltaY;
    private int lastX;
    private int touchSlop;
    private WXItemView mMoveView,mLastView;
    private boolean mHorizontalMoving;

    private int rightLimit,leftLimit;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();

        int action = e.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                LogUtil.i(TAG,"ACTION_DOWN");
                downX = x;
                downY = y;
                //查找当前触摸的Item
                mMoveView = (WXItemView) findChildViewUnder(x,y);
                if (mLastView != null && mLastView != mMoveView){
                    //关闭其他非触摸的Item
                    Log.d("wx",TAG + " onInterceptTouchEvent() closeItem()");
                    closeItem();
                }
                //按下后，避免当前Item瞬间移动到手指按下的位置
                lastX = x;
                rightLimit = getMenuWidth(mMoveView.findViewById(R.id.right_menu));
                leftLimit = getMenuWidth(mMoveView.findViewById(R.id.left_menu));
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = x - downX;
                deltaY = y - downY;
                ViewParent viewParent = getParent();
                if (viewParent == null){
                    break;
                }
                if ((mLeftScrollAllowed || mRightScrollAllowed)
                        && (Math.abs(deltaX) > Math.abs(deltaY))
                        && (Math.abs(deltaX)) > touchSlop){
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
               // closeItem();
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
                if (mHorizontalMoving
                        && (mLeftScrollAllowed || mRightScrollAllowed)
                        && (Math.abs(deltaX) > Math.abs(deltaY))
                        && (Math.abs(deltaX) > touchSlop)){
                    //计算水平移动偏移量
                    int dx = lastX - x;
                    mMoveView.scrollBy(dx,0);
                }
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                LogUtil.d("wx","onTouchEvent() mHorizontalMoving = " + mHorizontalMoving
                        + " getScrollX = " + mMoveView.getScrollX());
                if (mHorizontalMoving){
                    mHorizontalMoving = false;
                    //滑动结束后，将当前滑动的View赋值给mLastView
                    mLastView = mMoveView;
                    //左滑 > 400/2
                    if (mMoveView.getScrollX() > (leftLimit + rightLimit/2)){
                        mMoveView.scrollTo((leftLimit + rightLimit), 0);
                    } else if (mMoveView.getScrollX() < rightLimit/2){
                        mMoveView.scrollTo(0, 0);
                    } else{
                        mMoveView.scrollTo(leftLimit,0);
                    }
                }else {
                    Log.d("wx",TAG + " onTouchEvent() closeItem()");
                    closeItem();
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (closeScroller.computeScrollOffset()){
            if (mLastView != null){
                mLastView.scrollTo(closeScroller.getCurrX(),closeScroller.getCurrY());
                invalidate();
            }
        }
    }

    private int getMenuWidth(View view){
        return view.getWidth();
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
    private void closeItem(){
        if (mLastView != null){
            int scrollX = mLastView.getScrollX();
            Log.d("wx", TAG + " closeItem() scrollX = "+scrollX
                    +"  leftLimit = "+leftLimit
                    +"  rightLimit = "+rightLimit
                    +"  dis = "+(leftLimit - scrollX));
            if (scrollX == 0 || scrollX == (leftLimit + rightLimit)){
                //mLastView.scrollTo(leftLimit,0);
                closeScroller.startScroll(scrollX,0,leftLimit - scrollX, 0,500);
            }

        }
    }

}
