/**
 * 自定义RecyclerView
 */

package wxrecyclerview.wx.cn.wxrecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.Scroller;

import wxrecyclerview.wx.cn.wxrecyclerview.adapters.WXRecyclerAdapter;
import wxrecyclerview.wx.cn.wxrecyclerview.base.BaseWXRecyclerAdapter;
import wxrecyclerview.wx.cn.wxrecyclerview.utils.LogUtil;
import wxrecyclerview.wx.cn.wxrecyclerview.utils.WXConstants;
import wxrecyclerview.wx.cn.wxrecyclerview.widgets.WXItemView;

public class WXRecyclerView extends RecyclerView {

    private final String TAG = WXRecyclerView.class.getSimpleName();
    private final int INVALID_POSITION = -1;

    private final int LONG_PRESS =  10;

    private Scroller closeScroller;
    private ItemTouchHelper mItemTouchHelper;
    private int LONG_PRESS_TIME;
    private Handler mMainHandler;

    public WXRecyclerViewState state;

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
        LONG_PRESS_TIME = 1000;
        state = WXRecyclerViewState.IDLE;

        mMainHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case LONG_PRESS:
                        Log.d("welthy",TAG + " mMainHandler LONG_PRESS");
                        state = WXRecyclerViewState.LONG_PRESS;
                        updateMoveState();
                        break;
                }
            }
        };
    }

    //private int lastTouchPosition;
    private int downX,downY;
    private int deltaX,deltaY;
    private int lastX;
    private int touchSlop;
    private WXItemView mMoveView,mLastView;
    private Rect rect;

    private int rightLimit,leftLimit;
    private int touchPosition;
    private boolean canMove;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();

        int action = e.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                //获取是否可以滑动
                touchPosition = findPositionWhenTouch(x,y);
                canMove = getMoveEnable();
                LogUtil.i(TAG,"onInterceptTouchEvent() ACTION_DOWN canMove = "+canMove);
                if (canMove){
                    //查找当前触摸的Item
                    mMoveView = (WXItemView) findChildViewUnder(x,y);
                    if (mMoveView != null){
                        rightLimit = getMenuWidth(mMoveView.findViewById(R.id.right_menu));
                        leftLimit = getMenuWidth(mMoveView.findViewById(R.id.left_menu));
                    }
                }
                if (mLastView != null && mLastView != mMoveView){
                    //关闭其他非触摸的Item
                    LogUtil.d(TAG," onInterceptTouchEvent() closeItem()");
                    closeItem();
                }
                //按下后，避免当前Item瞬间移动到手指按下的位置
                lastX = x;
                if (mMainHandler.hasMessages(LONG_PRESS)){
                    mMainHandler.removeMessages(LONG_PRESS);
                }
                mMainHandler.sendEmptyMessageDelayed(LONG_PRESS,LONG_PRESS_TIME);
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = x - downX;
                deltaY = y - downY;
                ViewParent viewParent = getParent();
                if (viewParent == null){
                    break;
                }
                if ((Math.abs(deltaX) > Math.abs(deltaY)) && ((Math.abs(deltaX)) > touchSlop)
                        || ((Math.abs(deltaY) > Math.abs(deltaX)) && ((Math.abs(deltaY)) > touchSlop) )){
                    if (mMainHandler.hasMessages(LONG_PRESS)){
                        mMainHandler.removeMessages(LONG_PRESS);
                    }
                }
                if ((Math.abs(deltaX) > Math.abs(deltaY))
                        && ((Math.abs(deltaX)) > touchSlop)
                        && canMove){
                    //当前满足移动条件，则WXRecyclerView拦截触摸事件
                    LogUtil.d(TAG,"onInterceptTouchEvent() move horizontal");
                    state = WXRecyclerViewState.SWIPE;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(TAG,"onInterceptTouchEvent() ACTION_UP");
            case MotionEvent.ACTION_CANCEL:
                LogUtil.d(TAG,"onInterceptTouchEvent() ACTION_CANCEL");
               // closeItem();
                if (mMainHandler.hasMessages(LONG_PRESS)){
                    mMainHandler.removeMessages(LONG_PRESS);
                    state = WXRecyclerViewState.IDLE;
                }
                break;
        }
        if (state == WXRecyclerViewState.LONG_PRESS){
            return true;
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
                if (canMove && (mMoveView != null)
                        &&(Math.abs(deltaX) > Math.abs(deltaY))
                        && ((Math.abs(deltaX)) > touchSlop)){
                    //计算水平移动偏移量
                    int dx = lastX - x;
                    mMoveView.scrollBy(dx,0);
                }
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                state = WXRecyclerViewState.IDLE;
            case MotionEvent.ACTION_CANCEL:
                if (canMove){
                    canMove = false;
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
                    LogUtil.d(TAG," onTouchEvent() closeItem()");
                    closeItem();
                }
                if (mMainHandler.hasMessages(LONG_PRESS)){
                    mMainHandler.removeMessages(LONG_PRESS);
                }
                state = WXRecyclerViewState.IDLE;
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
        return view == null ? 0 : view.getWidth();
    }

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
            LogUtil.d(TAG, " closeItem() scrollX = "+scrollX
                    +"  leftLimit = "+leftLimit
                    +"  rightLimit = "+rightLimit
                    +"  dis = "+(leftLimit - scrollX));
            if (scrollX == 0 || scrollX == (leftLimit + rightLimit)){
                //mLastView.scrollTo(leftLimit,0);
                closeScroller.startScroll(scrollX,0,leftLimit - scrollX, 0,500);
            }

        }
    }

    private void updateMoveState(){
        canMove = getMoveEnable();
    }

    private boolean getMoveEnable(){
        boolean moveAble = true;
        LayoutManager lm = getLayoutManager();
        if (!WXConstants.TOGGLE_SWIPE){
            LogUtil.w(TAG,"getMoveEnable() TOGGLE_SWIPE is " + WXConstants.TOGGLE_SWIPE);
            moveAble = false;
        } else if (state != WXRecyclerViewState.SWIPE){
            moveAble = false;
        } else if (!(lm instanceof LinearLayoutManager)){
            LogUtil.w(TAG,"getMoveEnable()  It's LayoutManager is LinearLayoutManager = "+(lm instanceof LinearLayoutManager));
            moveAble = false;
        } else if (((BaseWXRecyclerAdapter)getAdapter()).getItemViewType(touchPosition) != WXConstants.TYPE_NORMAL) {
            LogUtil.w(TAG,"getMoveEnable() The item viewType is NOT TYPE_NORMAL. touchPosition = "+touchPosition);
            moveAble = false;
        } else if (!(hasLeftMenu() || hasRightMenu())){
            LogUtil.w(TAG,"getMoveEnable() hasLeftMenu = "+hasLeftMenu() + "  hasRightMenu = "+hasRightMenu());
            moveAble = false;
        }
        return moveAble;
    }

    public void setSwipeEnable(boolean canSwipe){
        WXConstants.TOGGLE_SWIPE = canSwipe;
    }

    public void setDragEnable(boolean canDrag){
        WXConstants.TOGGLE_DRAG = canDrag;
        if (canDrag){
            mItemTouchHelper = new ItemTouchHelper(new WXItemTouchHelperCallback((WXRecyclerAdapter) getAdapter()));
            mItemTouchHelper.attachToRecyclerView(this);
        }
    }

    private boolean hasLeftMenu(){
        if (mMoveView != null){
            return mMoveView.findViewById(R.id.left_menu) != null;
        }
        return true;
    }

    private boolean hasRightMenu(){
        if (mMoveView != null){
            return mMoveView.findViewById(R.id.right_menu) != null;
        }
        return true;
    }

}
