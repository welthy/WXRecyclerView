package wxrecyclerview.wx.cn.wxrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;

import wxrecyclerview.wx.cn.wxrecyclerview.adapters.WXRecyclerAdapter;
import wxrecyclerview.wx.cn.wxrecyclerview.utils.LogUtil;
import wxrecyclerview.wx.cn.wxrecyclerview.viewholder.WXViewHolder;

public class WXItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final String TAG = WXItemTouchHelperCallback.class.getSimpleName();
    private WXRecyclerAdapter wxRecyclerAdapter;

    public WXItemTouchHelperCallback(WXRecyclerAdapter wxRecyclerAdapter) {
        this.wxRecyclerAdapter = wxRecyclerAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof WXViewHolder){
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags,swipeFlags);
        }
        return 0;

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder moveHolder, @NonNull RecyclerView.ViewHolder target) {
        LogUtil.d(TAG,TAG + " onMove() state = "+((WXRecyclerView)recyclerView).state);
        boolean flag = false;
        if (((WXRecyclerView)recyclerView).state == WXRecyclerViewState.SWIPE){
            flag = false;
        }else if ((target instanceof WXViewHolder)){
            Collections.swap(wxRecyclerAdapter.getDatas(),moveHolder.getAdapterPosition(),target.getAdapterPosition());
            wxRecyclerAdapter.notifyItemMoved(moveHolder.getAdapterPosition(),target.getAdapterPosition());
            flag = true;
        }
        return flag;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }
}
